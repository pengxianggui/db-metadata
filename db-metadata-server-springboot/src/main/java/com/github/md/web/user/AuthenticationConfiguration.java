package com.github.md.web.user;

import cn.com.asoco.annotation.Authorize;
import com.github.md.web.user.auth.MRAuthInterceptDoer;
import com.github.md.web.user.auth.MRManager;
import com.github.md.web.user.auth.MResource;
import com.github.md.web.user.auth.defaults.ApiResourceFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.HandlerMethod;

/**
 * 默认的认证/鉴权配置类
 *
 * @author pengxg
 * @date 2021/10/18 1:07 下午
 */
@Slf4j
@Configuration
public class AuthenticationConfiguration {

    /**
     * 内置的鉴权执行者，基于资源表(meta_api_resource)、权限表(meta_auth)的访问控制
     *
     * @return
     */
    @Bean
    public MRAuthInterceptDoer defaultInterceptDoer() {
        return (request, response, handler) -> {
            if (!(handler instanceof HandlerMethod)) {
                return true;
            }

            MResource resource = (basedOnAnnotate((HandlerMethod) handler))
                    ? ApiResourceFactory.createAnnotateApiResource(request, (HandlerMethod) handler)
                    : ApiResourceFactory.createMetaApiResource(request, (HandlerMethod) handler);

            User user = AuthenticationManager.me().getUser(request);
            return MRManager.me().permit(user, resource);
        };
    }

    @ConditionalOnMissingBean
    @Bean
    public UserInterceptDoer defaultUserInterceptDoer() {
        return new DefaultUserInterceptDoer();
    }

    /**
     * 接口使用了{@link Authorize}修饰
     *
     * @param handler
     * @return
     */
    private boolean basedOnAnnotate(HandlerMethod handler) {
        Authorize authorize = handler.getMethodAnnotation(Authorize.class);
        return authorize != null;
    }
}
