package com.github.md.web.user;

import com.github.md.web.config.MetaProperties;
import com.github.md.web.user.auth.MRAuthInterceptDoer;
import com.github.md.web.user.auth.MRManager;
import com.github.md.web.user.auth.MetaAuthResource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.HandlerMethod;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

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

            MetaAuthResource resource = new MetaAuthResource(request, (HandlerMethod) handler);
            User user = UserThreadLocal.getUser();
            return MRManager.me().permit(user, resource);
        };
    }

    @ConditionalOnMissingBean
    @Bean
    public UserInterceptDoer defaultUserInterceptDoer(MetaProperties metaProperties) {
        return new UserInterceptDoer() {

            @Override
            public boolean preCertify(HttpServletRequest request, HttpServletResponse response, Object handler) {
                User user = getUser(request);

                if (Objects.isNull(user)) {
                    throw new UserException("未从请求内发现有效用户标志,请检查参数:%s", UserManager.me().loginService().tokenKey()).loginError();
                }

                Cookie cookie = new Cookie(UserManager.me().loginService().cookieKey(), user.userId());
                cookie.setMaxAge((int) TimeUnit.HOURS.toSeconds(6));
                response.addCookie(cookie);
                return true;
            }

            private User getUser(HttpServletRequest request) {
                User user;
                try {
                    LoginService<User> loginService = UserManager.me().loginService();
                    user = loginService.getUser(request);
                    if (Objects.isNull(user)) {
                        user = getDefaultVisitor(request);
                    }
                } catch (Exception e) {
                    log.error(e.getMessage());
                    throw e;
                }

                if (!Objects.isNull(user)) {
                    UserThreadLocal.setUser(user);
                }
                return user;
            }

            @Override
            public User getDefaultVisitor(HttpServletRequest request) {
                return UserManager.staticUser;
            }
        };
    }
}
