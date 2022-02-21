package com.github.md.web.user;

import cn.com.asoco.annotation.Authorize;
import com.github.md.web.user.auth.MRAuthInterceptDoer;
import com.github.md.web.user.auth.MRManager;
import com.github.md.web.user.auth.defaults.ApiResource;
import com.github.md.web.user.auth.meta.MetaAccess;
import com.github.md.web.user.auth.meta.MetaAuthResource;
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

    /**
     * 内置的鉴权执行者，基于{@link MetaAccess}注解的访问控制
     *
     * @return
     */
    @Bean
    public MRAuthInterceptDoer metaMRAuthInterceptDoer() {
        return (request, response, handler) -> {
            if (!(handler instanceof HandlerMethod)) {
                return true;
            }

            MetaAuthResource resource = MetaAuthResource.by(request, (HandlerMethod) handler);
            User user = UserThreadLocal.getUser();
            return MRManager.me().permit(user, resource);
        };
    }

    /**
     * 内置的基于{@link Authorize}的访问控制
     *
     * @return
     */
    @Bean
    public MRAuthInterceptDoer defaultMRAuthInterceptDoer() {
        return (request, response, handler) -> {
            if (!(handler instanceof HandlerMethod)) {
                return true;
            }

            ApiResource resource = ApiResource.by(request, (HandlerMethod) handler);
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
