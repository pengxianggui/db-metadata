package com.github.md.web.user;

import com.github.md.web.config.MetaProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author pengxg 认证&鉴权装配
 * @date 2021/10/18 9:00 上午
 */
@Slf4j
@Configuration
public class AuthMvcConfigurer implements WebMvcConfigurer {

    private MetaProperties metaProperties;

    public AuthMvcConfigurer(MetaProperties metaProperties) {
        this.metaProperties = metaProperties;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        if (metaProperties.getServer().getLogin().isEnable()) {
            registry.addInterceptor(AuthenticationManager.me().getUserIntercept())
                    .addPathPatterns(metaProperties.getServer().getLogin().getIncludes())
                    .excludePathPatterns(metaProperties.getServer().getLogin().getExcludes());
        }
        if (metaProperties.getServer().getAuth().isEnable()) {
            registry.addInterceptor(AuthenticationManager.me().getAuthIntercept())
                    .addPathPatterns(metaProperties.getServer().getAuth().getIncludes())
                    .excludePathPatterns(metaProperties.getServer().getAuth().getExcludes());
        }
    }
}
