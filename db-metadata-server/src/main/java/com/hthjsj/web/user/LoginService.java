package com.hthjsj.web.user;

import javax.servlet.http.HttpServletRequest;

/**
 * <p> @Date : 2019/12/13 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
public interface LoginService<U extends User> {

    String tokenKey();

    String loginKey();

    String pwdKey();

    String cookieKey();

    U getUser(HttpServletRequest request);

    U login(String username, String password);

    /**
     * 如外部已完成用户的login动作,可以将User用户手动登入
     * 方法主要逻辑在于显示得将某个用户注册到验证容器中
     *
     * @param user
     *
     * @return
     */
    default U login(U user) {
        return user;
    }

    default boolean logout(U user) {
        return false;
    }

    default boolean logged(U user) {
        return false;
    }

    default boolean isExpired(U user) {
        return false;
    }
}
