package com.github.md.web.user;

import com.github.md.analysis.kit.Kv;
import com.github.md.web.config.MetaProperties;
import com.github.md.web.user.auth.MRRole;
import com.github.md.web.user.auth.Permission;
import com.github.md.web.user.role.UserWithRolesWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.Assert;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * <p> @Date : 2019/10/18 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
@Slf4j
public class UserIntercept implements HandlerInterceptor {
    private MetaProperties metaProperties;

    public UserIntercept(MetaProperties metaProperties) {
        this.metaProperties = metaProperties;
    }

    public static User staticUser = new UserWithRolesWrapper() {

        @Override
        public MRRole[] roles() {
            return new MRRole[0];
        }

        @Override
        public boolean hasRole(String nameOrCode) {
            return false;
        }

        @Override
        public Permission[] permissions() {
            return new Permission[0];
        }

        @Override
        public boolean hasPermission(String... permissions) {
            return true;
        }

        @Override
        public boolean hasPermission(Permission... permissions) {
            return true;
        }

        @Override
        public String userId() {
            return "db-meta-web-devUser";
        }

        @Override
        public String userName() {
            return null;
        }

        @Override
        public Kv attrs() {
            return null;
        }

        @Override
        public Kv attrs(Map attrs) {
            return null;
        }
    };

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
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
        User user = null;
        try {
            LoginService<User> loginService = UserManager.me().loginService();
            user = loginService.getUser(request);
            Assert.notNull(user, "user为null");
        } catch (Exception e) {
            if (this.metaProperties.getServer().isDevMode()) {
                user = staticUser;
            } else {
                log.error(e.getMessage());
            }
        }
        if (!Objects.isNull(user)) {
            UserThreadLocal.setUser(user);
        }
        return user;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        UserThreadLocal.removeUser();
    }

    //    @Override
//    public void intercept(Invocation inv) {

    /**
     * 获取 LoginService ,进行登录校验
     * 1. 获取LoginService实例
     * 2. 从Request中取得用户;
     * 2.1 取id|token|或其他
     * 2.2 从某个容器中获取用户信息(redis|db|缓存|其他)
     * 3. 判断用户是否已登录,是否过期
     * 3.1 已登录,未过期 -> 放行
     * 3.2 未登录->拒绝
     * 3.3 已登录,已过期 -> 拒绝
     * > 已登录和过期 ,用两种状态表示, 用是否过期来表示用户是否登录虽然也可以,但是语义不明确
     * 4. 放行后更新用户过期时间.
     */
//        User user = null;
//        try {
//            // 放行
//            if (PatternPathMatcher.matchAny(inv.getActionKey(),
//                    skipPathPatterns.toArray(new String[skipPathPatterns.size()]))) {
//                inv.invoke();
//                return;
//            }
//
//            LoginService<User> loginService = UserManager.me().loginService();
//            user = loginService.getUser(inv.getController().getRequest());
//            //开发模式时 指定开发用户
//            if (JFinal.me().getConstants().getDevMode()) {
//                if (user == null) {
//                    user = staticUser;
//                }
//            }
//            if (user != null) {
//                UserThreadLocal.setUser(user);
//                inv.invoke();
//                inv.getController().setCookie(loginService.cookieKey(), user.userId(), (int) TimeUnit.HOURS.toSeconds(6));
//            } else {
//                throw new UserException("未从请求内发现有效用户标志,请检查参数:%s", loginService.tokenKey()).loginError();
//            }
//        } finally {
//            UserThreadLocal.removeUser(user);
//        }
//    }
}
