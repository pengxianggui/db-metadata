package com.hthjsj.web.config;

import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.hthjsj.SpringAnalysisManager;
import com.hthjsj.analysis.AnalysisProperties;
import com.hthjsj.web.config.json.JsonParameterToMapHandler;
import com.hthjsj.web.kit.Dicts;
import com.jfinal.json.FastJsonRecordSerializer;
import com.jfinal.plugin.activerecord.Record;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;

import javax.annotation.PostConstruct;
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
public class MetaInitializer implements WebMvcConfigurer {

    @Bean
    @ConfigurationProperties("md")
    public MetaProperties metaProperties(AnalysisProperties analysisProperties) {
        return new MetaProperties(analysisProperties);
    }

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
//    public JsonParameterToMapHandler jsonParameterToMapHandler(RequestMappingHandlerAdapter requestMappingHandlerAdapter) {
//        return new JsonParameterToMapHandler();
//    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new JsonParameterToMapHandler());
    }

    @Bean
    public HttpMessageConverter configureMessageConverters() {
        FastJsonHttpMessageConverter converter = new FastJsonHttpMessageConverter();
        FastJsonConfig config = new FastJsonConfig();
        config.setSerializerFeatures(
                // 保留map空的字段
                SerializerFeature.WriteMapNullValue,
                // 将String类型的null转成""
                SerializerFeature.WriteNullStringAsEmpty,
                // 将Number类型的null转成0
                SerializerFeature.WriteNullNumberAsZero,
                // 将List类型的null转成[]
                SerializerFeature.WriteNullListAsEmpty,
                // 将Boolean类型的null转成false
                SerializerFeature.WriteNullBooleanAsFalse,
                // 避免循环引用
                SerializerFeature.DisableCircularReferenceDetect);

        converter.setFastJsonConfig(config);
        converter.setDefaultCharset(Charset.forName("UTF-8"));
        List<MediaType> mediaTypeList = new ArrayList<>();
        // 解决中文乱码问题，相当于在Controller上的@RequestMapping中加了个属性produces = "application/json"
        mediaTypeList.add(MediaType.APPLICATION_JSON);
        converter.setSupportedMediaTypes(mediaTypeList);
        // 支持序列化 ActiveRecord 的 Record 类型
        SerializeConfig.getGlobalInstance().put(Record.class, new FastJsonRecordSerializer());
        return converter;
    }

    @PostConstruct
    public void init() {
        //dictionary register
        Dicts.me().init();

        //component register
        //        Components.me().init();
        //        Components.me().addAutoInitComponents(ComponentType.SEARCHVIEW)
        //                  .addAutoInitComponents(ComponentType.TABLEVIEW)
        //                  .addAutoInitComponents(ComponentType.TABLETREEVIEW)
        //                  .addAutoInitComponents(ComponentType.FORMVIEW);
    }
}
