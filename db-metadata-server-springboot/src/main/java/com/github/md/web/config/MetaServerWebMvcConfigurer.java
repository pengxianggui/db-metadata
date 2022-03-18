package com.github.md.web.config;

import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.github.md.analysis.db.DateTime;
import com.github.md.web.config.json.FastJsonRecordSerializer;
import com.github.md.web.config.json.JsonParameterToMapHandler;
import com.github.md.web.config.register.DynamicRegisterControllerHandlerMapping;
import com.jfinal.plugin.activerecord.Record;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcRegistrations;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * <p> @Date : 2021/9/10 </p>
 * <p> @Project : db-metadata-server-springboot</p>
 *
 * <p> @author konbluesky </p>
 */
@Configuration
public class MetaServerWebMvcConfigurer implements WebMvcConfigurer, WebMvcRegistrations {

    /**
     * 可定制MetaServer系统URl的前缀
     *
     * @return
     */
    @Override
    public RequestMappingHandlerMapping getRequestMappingHandlerMapping() {
        return new DynamicRegisterControllerHandlerMapping();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        InterceptorRegistration jsonParameterToMap = registry.addInterceptor(new JsonParameterToMapHandler());
    }

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        /**
         * SpringBoot 默认使用Jackson作为json序列化工具,为了避免干扰需要将Jackson关掉
         * 关掉有2种方式:
         * 1. 从pom.xml中排除jackson引用
         * 2. 代码中动态remove
         * 这里使用第二种
         */
        Iterator<HttpMessageConverter<?>> iterator = converters.iterator();
        while (iterator.hasNext()) {
            HttpMessageConverter<?> converter = iterator.next();
            if (converter instanceof MappingJackson2HttpMessageConverter) {
                iterator.remove();
            }
        }

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
        converter.setDefaultCharset(StandardCharsets.UTF_8);
        List<MediaType> mediaTypeList = new ArrayList<>();
        // 解决中文乱码问题，相当于在Controller上的@RequestMapping中加了个属性produces = "application/json"
        mediaTypeList.add(MediaType.APPLICATION_JSON);
        converter.setSupportedMediaTypes(mediaTypeList);
        // 支持序列化 ActiveRecord 的 Record 类型
        SerializeConfig.getGlobalInstance().put(Record.class, new FastJsonRecordSerializer());

        // TODO 2.3 序列化对全局生效, 如何避免对业务系统的强制影响, 仅仅只作用于Record中的日期类型
        SerializeConfig.getGlobalInstance().put(Timestamp.class, (jsonSerializer, o, o1, type, i) -> jsonSerializer.write(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format((Timestamp) o)));
        SerializeConfig.getGlobalInstance().put(DateTime.class, (jsonSerializer, o, o1, type, i) -> jsonSerializer.write(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format((DateTime) o)));
        SerializeConfig.getGlobalInstance().put(LocalDateTime.class, (jsonSerializer, o, o1, type, i) -> jsonSerializer.write(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format((LocalDateTime) o)));
        converters.add(converter);
    }

    @Override
    public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        // TODO 如何动态决定使用哪个HttpMessageConverter？
        //  从而实现dbmeta内置请求使用内置 HttpMessageConverter，内置Converter不会影响外部系统
    }
}
