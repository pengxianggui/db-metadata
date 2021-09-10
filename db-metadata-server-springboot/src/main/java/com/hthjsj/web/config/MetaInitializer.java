package com.hthjsj.web.config;

import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.hthjsj.SpringAnalysisManager;
import com.hthjsj.analysis.AnalysisProperties;
import com.hthjsj.web.config.json.FastJsonRecordSerializer;
import com.hthjsj.web.config.json.JsonParameterToMapHandler;
import com.jfinal.plugin.activerecord.Record;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

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

}
