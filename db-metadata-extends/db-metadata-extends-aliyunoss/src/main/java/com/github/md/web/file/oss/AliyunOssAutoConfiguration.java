package com.github.md.web.file.oss;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 阿里云oss文件服务自动配置类
 */
@Configuration
public class AliyunOssAutoConfiguration {

    @Bean
    @ConfigurationProperties("md.server.upload.aliyunoss")
    public AliyunOssProperties aliyunOssProperties() {
        return new AliyunOssProperties();
    }

    @Bean
    public AliyunOssFileService aliyunOssFileService(AliyunOssProperties aliyunOssProperties) {
        return new AliyunOssFileService(new AliyunOssUtil(aliyunOssProperties));
    }
}
