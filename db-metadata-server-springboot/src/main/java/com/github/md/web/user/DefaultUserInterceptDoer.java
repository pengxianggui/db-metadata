package com.github.md.web.user;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.concurrent.TimeUnit;

/**
 * @author pengxg
 * @date 2021/10/19 8:31 下午
 */
@Slf4j
public class DefaultUserInterceptDoer implements UserInterceptDoer {

    public DefaultUserInterceptDoer() {
    }

    @Override
    public boolean preCertify(HttpServletRequest request, HttpServletResponse response, Object handler) {
        User user = getUser(request);

        if (user == null) {
            user = ifNullUser(); // 补偿，有时希望无用户时默认提供给一个匿名用户
        }

        if (user != null) {
            UserThreadLocal.setUser(user);
            Cookie cookie = new Cookie(UserManager.me().loginService().cookieKey(), user.userId());
            cookie.setMaxAge((int) TimeUnit.HOURS.toSeconds(6));
            response.addCookie(cookie);
        }
        return true;
    }

    /**
     * 当为用户对象为空时。默认不做处理，你可以继承此方法，从而实现当用户为空时，提供一个默认的静态用户。比如: "游客"
     *
     * @return
     */
    public User ifNullUser() {
        throw new UnLoginException("未认证");
    }

    public User getUser(HttpServletRequest request) {
        User user;
        try {
            LoginService<User> loginService = UserManager.me().loginService();
            user = loginService.getUser(request);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw e;
        }

        return user;
    }

    @Override
    public User getDefaultVisitor(HttpServletRequest request) {
        return UserManager.staticUser;
    }
}
