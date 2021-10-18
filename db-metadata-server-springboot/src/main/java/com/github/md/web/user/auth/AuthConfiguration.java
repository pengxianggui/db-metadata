package com.github.md.web.user.auth;

import com.github.md.web.user.User;
import com.github.md.web.user.UserThreadLocal;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.HandlerMethod;

/**
 * 权限配置
 *
 * @author pengxg
 * @date 2021/10/18 1:07 下午
 */
@Configuration
public class AuthConfiguration {

    @Bean
    public MRAuthInterceptDoer defaultMRAuthInterceptDoer() {
        return (request, response, handler) -> {
            if (!(handler instanceof HandlerMethod)) {
                return true;
            }

            MetaAuthResource resource = new MetaAuthResource(request, (HandlerMethod) handler);
            User user = UserThreadLocal.getUser();
            return MRManager.me().permit(user, resource);
        };
    }
}
