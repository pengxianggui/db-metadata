package com.hthjsj;

import com.alibaba.druid.filter.logging.Log4jFilter;
import com.alibaba.druid.util.JdbcConstants;
import com.alibaba.druid.wall.WallFilter;
import com.jfinal.config.Plugins;
import com.jfinal.kit.Prop;
import com.jfinal.kit.PropKit;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.IPlugin;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.activerecord.dialect.MysqlDialect;
import com.jfinal.plugin.druid.DruidPlugin;

import java.util.ArrayList;
import java.util.List;

/**
 * <p> Class title: </p>
 * <p> @Describe: </p>
 * <p> @Date : 2019-08-20 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
public class AnalysisConfig {

    public static final String PREFIX_BIZ_DB = "biz.db";

    public static final String SUFFIX_BIZ_URL = ".jdbc.url";

    public static final String SUFFIX_BIZ_USERNAME = ".jdbc.username";

    public static final String SUFFIX_BIZ_PASSWORD = ".jdbc.password";

    private static final AnalysisConfig me = new AnalysisConfig();

    List<DBSource> dbSources = new ArrayList<>();

    private Plugins plugins = new Plugins();

    private AnalysisConfig() {

        //init main database resource
        DBSource mainSource = new DBSource(App.DB_MAIN, getProp().get(App.DB_MAIN_URL),
                                           getProp().get(App.DB_MAIN_USERNAME), getProp().get(App.DB_MAIN_PASSWORD),
                                           plugins);
        dbSources.add(mainSource);

        //init business database resource
        String dbs = getProp().get(PREFIX_BIZ_DB);
        if (StrKit.notBlank(dbs)) {
            for (String dbName : dbs.split(",")) {
                String url = getProp().get(dbName + SUFFIX_BIZ_URL);
                String username = getProp().get(dbName + SUFFIX_BIZ_USERNAME);
                String password = getProp().get(dbName + SUFFIX_BIZ_PASSWORD);
                if (StrKit.isBlank(url) || StrKit.isBlank(username) || StrKit.isBlank(password)) {
                    throw new RuntimeException(
                            String.format("业务数据库配置信息有误\n\t[dbname:\"%s\",url:\"%s\",user:\"%s\",pwd:\"%s\"]", dbName,
                                          url, username, password));
                }
                dbSources.add(new DBSource(dbName, url, username, password, plugins));
            }
        }
    }

    public static AnalysisConfig me() {
        return me;
    }

    public List<IPlugin> getPlugins() {
        return plugins.getPluginList();
    }

    public Prop getProp() {
        return PropKit.use(App.CONFIG_NAME);
    }

    public void start() {
        for (IPlugin plugin : plugins.getPluginList()) {
            plugin.start();
        }
    }

    interface DbSouceConfig {

        String configName();

        void init(Plugins me);
    }

    class DBSource implements DbSouceConfig {

        String configName, url, username, password;

        public DBSource(String configName, String jdbcurl, String username, String password, Plugins plugins) {
            this.configName = configName;
            this.url = jdbcurl;
            this.username = username;
            this.password = password;
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
            log4jFilter.setStatementLogEnabled(true);
            log4jFilter.setStatementLogErrorEnabled(true);
            log4jFilter.setStatementExecutableSqlLogEnable(true);
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
    }
}
