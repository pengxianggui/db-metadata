package com.hthjsj;

import com.alibaba.druid.filter.logging.Log4jFilter;
import com.alibaba.druid.util.JdbcConstants;
import com.alibaba.druid.wall.WallFilter;
import com.jfinal.config.Plugins;
import com.jfinal.plugin.IPlugin;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.activerecord.dialect.MysqlDialect;
import com.jfinal.plugin.druid.DruidPlugin;

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

    Plugins plugins = new Plugins();

    public List<IPlugin> getPlugins() {
        return plugins.getPluginList();
    }

    public AnalysisConfig() {
        DBSource mainsource = new DBSource(App.DB_MAIN, "jdbc:mysql://192.168.1.93:3310/metadata?useSSL=false&characterEncoding=utf-8", "root", "123456", plugins);
        //        DBSource dbsource = new DBSource("metadata", "jdbc:mysql://localhost:3309/eova?useSSL=false", "root", "gongwei911", plugins);
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
