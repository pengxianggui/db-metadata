package com.github.md.web;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author pengxg
 * @date 2022/4/25 9:38 上午
 */
@Configuration
public class DbMetaConfiguration {

    /**
     * 通过覆盖此bean实现自定义配置
     *
     * @return
     */
    @ConditionalOnMissingBean
    @Bean
    public DbMetaConfigurer dbMetaConfigurer() {
        return new DbMetaConfigurer() {
        };
    }
}
