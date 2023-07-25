package com.github.md.web;

import com.github.md.web.user.AuthenticationManager;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * dbmeta自动配置类。
 *
 * @author pengxg
 * @date 2022/4/25 9:38 上午
 */
@ComponentScan
@Configuration
public class DbMetaAutoConfiguration {

    /**
     * 通过覆盖此bean实现自定义配置
     *
     * @return
     */
    @ConditionalOnMissingBean
    @Bean
    public DbMetaConfigurer dbMetaConfigurer() {
        return new DefaultDbMetaConfigurer();
    }

    /**
     * 权限管理类。
     *
     * @param dbMetaConfigurer
     * @return
     * @deprecated 无需配置为bean, 系统由{@link AuthenticationManager#me()}调用
     */
    @Deprecated
    @Bean
    public AuthenticationManager authenticationManager(DbMetaConfigurer dbMetaConfigurer) {
        return new AuthenticationManager(dbMetaConfigurer);
    }
}
