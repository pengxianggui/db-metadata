package com.github.md.web.user;

import com.github.md.web.user.role.UserWithRolesWrapper;
import com.jfinal.kit.StrKit;
import lombok.AllArgsConstructor;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * 从请求头中获取key，根据key值从内置的内存缓存中获取已登录对象。你可以覆盖{@link #getUser(HttpServletRequest)}方法，依据request对象
 * 获取已登录的用户并返回。通常，这需要和登录成功时缓存用户处，进行配合呼应
 *
 * <p> @Date : 2019/12/13 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
@AllArgsConstructor
public abstract class AbstractUserService<U extends User, UR extends UserWithRolesWrapper> implements UserService<U>, LoginService<UR> {

    @Override
    public UR getUser(HttpServletRequest request) {
        //cookie load
        String token = "";
// TODO cookie和token是否需要都支持，然后配置应用？目前我们token用的居多，暂时不启用cookie
//        if (request.getCookies() != null) {
//            Optional<Cookie> cookie = Arrays.stream(request.getCookies()).filter(c -> c.getName().equalsIgnoreCase(cookieKey())).findFirst();
//            if (cookie.isPresent()) {
//                token = cookie.get().getValue();
//            }
//        }

        //request load
        token = StrKit.defaultIfBlank(token, request.getHeader(tokenKey()));
        if (StrKit.notBlank(token)) {
            UR user = (UR) AuthenticationManager.me().getLoginUsers().getIfPresent(token);
            return user;
        }
        return null;
    }

    @Override
    public Map<String, UR> getAllLoggedUsers() {
        return (Map<String, UR>) AuthenticationManager.me().getLoginUsers().asMap();
    }

    @Override
    public boolean isExpired(UR user) {
        return !logged(user);
    }
}
