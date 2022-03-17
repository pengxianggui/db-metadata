package com.github.md.analysis.db.registry;

import com.github.md.analysis.AnalysisProperties;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * <p> @Date : 2021/9/2 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
@AllArgsConstructor
@Configuration
public class JFinalActiveRecordInitializer {
    private AnalysisProperties analysisProperties;

    @Bean
    public JFinalActiveRecordPluginManager jFinalActiveRecordPluginManager(DataSourceRegistrar dataSourceRegistrar) {
        JFinalActiveRecordPluginManager manager = new JFinalActiveRecordPluginManager();
        dataSourceRegistrar.allSource().forEach((key, value) -> {
            ActiveRecordPlugin plugin = new ActiveRecordPlugin(value.schemaName(), value.dataSource());
            if (analysisProperties.isShowSql()) {
                plugin.setShowSql(true);
            }
            manager.add(key, plugin);
        });
        // " manager.start() " very important
        manager.start();
        return manager;
    }
}
