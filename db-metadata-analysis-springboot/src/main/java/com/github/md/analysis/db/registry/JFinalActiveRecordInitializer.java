package com.github.md.analysis.db.registry;

import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * <p> @Date : 2021/9/2 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
@Configuration
public class JFinalActiveRecordInitializer {

    @Bean
    public JFinalActiveRecordPluginManager jFinalActiveRecordPluginManager(DataSourceRegistrar dataSourceRegistrar) {
        JFinalActiveRecordPluginManager manager = new JFinalActiveRecordPluginManager();
        dataSourceRegistrar.allSource().forEach((key, value) -> {
            manager.add(key, new ActiveRecordPlugin(value.schemaName(), value.dataSource()));
        });
        // " manager.start() " very important
        manager.start();
        return manager;
    }
}
