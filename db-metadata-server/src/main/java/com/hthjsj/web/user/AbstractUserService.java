package com.hthjsj.web.user;

import com.jfinal.kit.StrKit;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Optional;

/**
 * <p> @Date : 2019/12/13 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
public abstract class AbstractUserService<U extends User> implements UserService<U>, LoginService<U> {

    @Override
    public U getUser(HttpServletRequest request) {
        //cookie load
        String uid = "";
        if (request.getCookies() != null) {
            Optional<Cookie> cookie = Arrays.stream(request.getCookies()).filter(c -> c.getName().equalsIgnoreCase(cookieKey())).findFirst();
            if (cookie.isPresent()) {
                uid = cookie.get().getValue();
            }
        }
        //request load
        uid = StrKit.defaultIfBlank(uid, request.getParameter(tokenKey()));
        if (StrKit.notBlank(uid)) {
            U user = (U) UserManager.me().getLoginUsers().getIfPresent(uid);
            return user;
        }
        return null;
    }
}
