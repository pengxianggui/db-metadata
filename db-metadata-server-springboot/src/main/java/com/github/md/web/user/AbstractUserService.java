package com.github.md.web.user;

import com.github.md.web.ServiceManager;
import com.github.md.web.user.role.UserWithRolesWrapper;
import com.jfinal.kit.StrKit;

import javax.servlet.http.HttpServletRequest;

/**
 * 按优先级依次从cookie或请求头中获取key，根据key值从内置的内存缓存中获取已登录对象。
 * 登录的用户也将存储在内存中。
 *
 * <p> @Date : 2019/12/13 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
public abstract class AbstractUserService<U extends User, UR extends UserWithRolesWrapper> implements UserService<U>, LoginService<UR> {

    @Override
    public UR getUser(HttpServletRequest request) {
        //cookie load
        String uid = "";
// TODO cookie和token是否需要都支持，然后配置应用？目前我们token用的居多，暂时不启用cookie
//        if (request.getCookies() != null) {
//            Optional<Cookie> cookie = Arrays.stream(request.getCookies()).filter(c -> c.getName().equalsIgnoreCase(cookieKey())).findFirst();
//            if (cookie.isPresent()) {
//                uid = cookie.get().getValue();
//            }
//        }
        //request load
        uid = StrKit.defaultIfBlank(uid, request.getHeader(tokenKey()));
        if (StrKit.notBlank(uid)) {
            UR user = (UR) AuthenticationManager.me().getLoginUsers().getIfPresent(uid);
            return user;
        }
        return null;
    }

    @Override
    public boolean setLogged(UR user) {
        AuthenticationManager.me().getLoginUsers().put(user.userId(), user); // 缓存到内存中
        return logged(user);
    }

    @Override
    public boolean logged(UR user) {
        return AuthenticationManager.me().getLoginUsers().getIfPresent(user.userId()) != null;
    }

    @Override
    public boolean logout(UR user) {
        AuthenticationManager.me().getLoginUsers().invalidate(user.userId());
        return !logged(user);
    }

    @Override
    public boolean isExpired(UR user) {
        return !logged(user);
    }

    @Override
    public String tokenKey() {
        return ServiceManager.getAppProperties().getServer().getLogin().getTokenKey();
    }

    @Override
    public String cookieKey() {
        return ServiceManager.getAppProperties().getServer().getLogin().getCookieKey();
    }

    @Override
    public String loginKey() {
        return ServiceManager.getAppProperties().getServer().getLogin().getLoginKey();
    }

    @Override
    public String pwdKey() {
        return ServiceManager.getAppProperties().getServer().getLogin().getPwdKey();
    }
}
