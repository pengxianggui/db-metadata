package com.hthjsj;

import com.alibaba.druid.filter.logging.Log4jFilter;
import com.alibaba.druid.util.JdbcConstants;
import com.alibaba.druid.wall.WallFilter;
import com.hthjsj.analysis.MetaAnalysisException;
import com.jfinal.config.Plugins;
import com.jfinal.kit.Prop;
import com.jfinal.kit.PropKit;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.IPlugin;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.DbPro;
import com.jfinal.plugin.activerecord.dialect.MysqlDialect;
import com.jfinal.plugin.druid.DruidPlugin;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <p> Class title: </p>
 * <p> @Describe: </p>
 * <p> @Date : 2019-08-20 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
@Slf4j
public class AnalysisConfig {

    public static final String PREFIX_BIZ_DB = "biz.db";

    public static final String PREFIX_MAN_DB = "main.db";

    public static final String SUFFIX_BIZ_URL = ".jdbc.url";

    public static final String SUFFIX_BIZ_USERNAME = ".jdbc.username";

    public static final String SUFFIX_BIZ_PASSWORD = ".jdbc.password";

    public static final String SUFFIX_BIZ_PERMISSION = ".jdbc.permission";

    public static final String CONFIG_NAME = "config.properties";

    public static final String DB_MAIN_URL = "main.jdbc.url";

    public static final String DB_MAIN_USERNAME = "main.jdbc.username";

    public static final String DB_MAIN_PASSWORD = "main.jdbc.password";

    private static final AnalysisConfig me = new AnalysisConfig();

    private final List<DBSource> dbSources = new ArrayList<>();

    private final Plugins plugins = new Plugins();

    public static AnalysisConfig me() {
        return me;
    }

    public void initDefaultDbSource() {
        //init main database resource
        DBSource mainSource = new DBSource(dbMainStr(),
                                           getProp().get(DB_MAIN_URL),
                                           getProp().get(DB_MAIN_USERNAME),
                                           getProp().get(DB_MAIN_PASSWORD),
                                           DbSourceConfig.Permission.RW,
                                           plugins);
        dbSources.add(mainSource);

        //init business database resource
        String dbs = getProp().get(PREFIX_BIZ_DB);
        if (StrKit.notBlank(dbs)) {
            for (String dbName : dbs.split(",")) {
                String url = getProp().get(dbName + SUFFIX_BIZ_URL);
                String username = getProp().get(dbName + SUFFIX_BIZ_USERNAME);
                String password = getProp().get(dbName + SUFFIX_BIZ_PASSWORD);
                String permissionString = getProp().get(dbName + SUFFIX_BIZ_PERMISSION);
                DbSourceConfig.Permission permission = DbSourceConfig.Permission.get(permissionString);
                if (permission == DbSourceConfig.Permission.UNKNOW) {
                    throw new MetaAnalysisException("%s 数据库的权限设置有误,当前配置项 [%s] ;支持的配置项:[r,w,rw]", dbName, permissionString);
                }
                if (StrKit.isBlank(url) || StrKit.isBlank(username) || StrKit.isBlank(password)) {
                    throw new MetaAnalysisException("业务数据库配置信息有误\n\t[dbname:\"%s\",url:\"%s\",user:\"%s\",pwd:\"%s\"]", dbName, url, username, password);
                }
                dbSources.add(new DBSource(dbName, url, username, password, permission, plugins));
            }
        }
    }

    public String dbMainStr() {
        return getProp().get(PREFIX_MAN_DB);
    }

    @Deprecated
    private String filterMainDBStr(String jdbcUrl) {
        Pattern pattern = Pattern.compile("jdbc:mysql://.*/(.*)\\?.*");
        Matcher matcher = pattern.matcher(jdbcUrl);
        String dbName = "";
        if (matcher.find()) {
            dbName = matcher.group(1);
        }
        return StrKit.defaultIfBlank(dbName, "");
    }

    public DbPro dbMain() {
        return Db.use(dbMainStr());
    }

    public List<IPlugin> getPlugins() {
        return plugins.getPluginList();
    }

    public List<DBSource> getDbSources() {
        return dbSources;
    }

    public Prop getProp() {
        return PropKit.useFirstFound("config-dev.properties", CONFIG_NAME);
    }

    public void start() {
        for (IPlugin plugin : plugins.getPluginList()) {
            plugin.start();
        }
    }

    interface DbSourceConfig {

        String configName();

        void init(Plugins me);

        /**
         * TODO  not finished
         *
         * @return
         */
        Permission getPermission();

        enum Permission {
            UNKNOW,
            R,//读
            W,//写
            RW;//读写

            static Permission get(String s) {
                if (StrKit.isBlank(s)) {
                    return R;
                }
                if (s.toLowerCase().equals("r"))
                    return R;
                else if (s.toLowerCase().equals("w"))
                    return W;
                else if (s.toLowerCase().equals("rw"))
                    return RW;
                return UNKNOW;
            }
        }
    }

    public class DBSource implements DbSourceConfig {

        String configName, url, username, password;

        Permission permission;

        public DBSource(String configName, String jdbcUrl, String username, String password, Permission permission, Plugins plugins) {
            this.configName = configName;
            this.url = jdbcUrl;
            this.username = username;
            this.password = password;
            this.permission = permission;
            init(plugins);
        }

        @Override
        public String configName() {
            return configName;
        }

        @Override
        public void init(Plugins me) {
            DruidPlugin dp_info = new DruidPlugin(url, username, password);

            Log4jFilter log4jFilter = new Log4jFilter();
            log4jFilter.setStatementLogEnabled(false);
            log4jFilter.setStatementLogErrorEnabled(false);
            log4jFilter.setStatementExecutableSqlLogEnable(false);
            dp_info.addFilter(log4jFilter);

            WallFilter wallFilter = new WallFilter();
            wallFilter.setDbType(JdbcConstants.MYSQL);
            dp_info.addFilter(wallFilter);

            ActiveRecordPlugin arp_info = new ActiveRecordPlugin(configName, dp_info);
            //            arp_info.addSqlTemplate("schema_init.sql.txt");
            //            arp_info.addSqlTemplate("meta_operator.sql.txt");
            arp_info.setDialect(new MysqlDialect());
            arp_info.setShowSql(false);


            me.add(dp_info).add(arp_info);
        }

        @Override
        public Permission getPermission() {
            return permission;
        }
    }
}
