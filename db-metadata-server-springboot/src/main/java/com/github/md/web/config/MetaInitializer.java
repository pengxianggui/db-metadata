package com.github.md.web.config;

import com.github.md.analysis.SpringAnalysisManager;
import com.github.md.analysis.AnalysisProperties;
import com.github.md.web.config.register.PathCustomizer;
import com.github.md.web.config.register.PrefixPathCustomizer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Specially initialize some classes required by MetaServer
 * <p> @Date : 2021/9/8 </p>
 * <p> @Project : db-metadata-server-springboot</p>
 *
 * <p> @author konbluesky </p>
 */
@Configuration
@Slf4j
public class MetaInitializer {

    @Bean
    @ConfigurationProperties("md")
    public MetaProperties metaProperties(AnalysisProperties analysisProperties) {
        return new MetaProperties(analysisProperties);
    }

    /** Some logic that needs to be triggered automatically after the program is started */
    @Bean
    public MetaBootstrap metaBootstrap(MetaProperties metaProperties) {
        return new MetaBootstrap(metaProperties);
    }

    @Bean
    public MetaServerManager metaServerManager(SpringAnalysisManager analysisManager, MetaProperties metaProperties) {
        return new MetaServerManager(analysisManager, metaProperties);
    }

    @Bean
    public QuickJudge quickJudge(MetaServerManager metaServerManager) {
        return new QuickJudgeImpl(metaServerManager);
    }

//    @Bean
    public PathCustomizer pathCustomizer(){
        //        return new PrefixPathCustomizer("v1","com.github.md.web.controller","com.github.md.web.feature");
        return new PrefixPathCustomizer("v1", "com.github.md.web");
    }
}
