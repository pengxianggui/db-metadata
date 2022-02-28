package com.github.md.web.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 默认的认证/鉴权配置类
 *
 * @author pengxg
 * @date 2021/10/18 1:07 下午
 */
@Slf4j
@Configuration
public class AuthenticationConfiguration {

    @ConditionalOnMissingBean
    @Bean
    public AuthenticationConfigurer authenticationConfigurer() {
        return new AuthenticationConfigurer() {
        };
    }
}
