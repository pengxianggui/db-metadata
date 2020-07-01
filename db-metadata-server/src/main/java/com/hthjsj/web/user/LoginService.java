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

    boolean logout(U user);

    boolean logged(U user);

    boolean isExpired(U user);
}
