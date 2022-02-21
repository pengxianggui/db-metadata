package com.github.md.web.user;

import com.github.md.web.user.auth.MRAuthInterceptDoer;
import com.github.md.web.user.auth.MRManager;
import com.github.md.web.user.auth.MetaAuthResource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.HandlerMethod;

/**
 * 权限配置
 *
 * @author pengxg
 * @date 2021/10/18 1:07 下午
 */
@Slf4j
@Configuration
public class DefaultDoerConfiguration {

    @Bean
    public MRAuthInterceptDoer defaultMRAuthInterceptDoer() {
        return (request, response, handler) -> {
            if (!(handler instanceof HandlerMethod)) {
                return true;
            }

            MetaAuthResource resource = MetaAuthResource.buildByRequest(request, (HandlerMethod) handler);
            User user = UserThreadLocal.getUser();
            return MRManager.me().permit(user, resource);
        };
    }

    @ConditionalOnMissingBean
    @Bean
    public UserInterceptDoer defaultUserInterceptDoer() {
        return new DefaultUserInterceptDoer();
    }
}
