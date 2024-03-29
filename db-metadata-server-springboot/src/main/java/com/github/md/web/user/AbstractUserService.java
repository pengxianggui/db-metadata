package com.github.md.web.user;

import com.github.md.web.ServiceManager;
import com.github.md.web.user.role.UserWithRolesWrapper;
import com.jfinal.kit.StrKit;
import lombok.AllArgsConstructor;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * 此抽象类将登录用户缓存到内存中。集群模式下会有问题，需要单独实现LoginService或覆盖此抽象类的相关方法。
 * <p>
 * 从请求头中获取key，根据key值从内置的内存缓存中获取已登录对象。
 * 你可以覆盖{@link #getUser(HttpServletRequest)}和{@link #getUser(String)}方法，依据request对象
 * 获取已登录的用户并返回。通常，这需要和登录成功时缓存用户处，进行配合呼应
 *
 * <p> @Date : 2019/12/13 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
@AllArgsConstructor
public abstract class AbstractUserService<U extends User, UR extends UserWithRolesWrapper> implements UserService<U>, LoginService<UR> {

    /**
     * 获取请求中的token
     *
     * @param request
     * @return
     */
    protected String getToken(HttpServletRequest request) {
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
        return StrKit.defaultIfBlank(token, request.getHeader(tokenKey()));
    }

    @Override
    public String tokenKey() {
        return ServiceManager.getAppProperties().getServer().getLogin().getTokenKey();
    }

    @Override
    public String tokenIn() {
        return ServiceManager.getAppProperties().getServer().getLogin().getTokenIn();
    }

    @Override
    public String loginKey() {
        return ServiceManager.getAppProperties().getServer().getLogin().getLoginKey();
    }

    @Override
    public String pwdKey() {
        return ServiceManager.getAppProperties().getServer().getLogin().getPwdKey();
    }

    @Override
    public UR getUser(HttpServletRequest request) {
        return getUser(getToken(request));
    }

    @Override
    public UR getUser(String token) {
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
    public boolean logout(HttpServletRequest request) {
        final String token = getToken(request);
        AuthenticationManager.me().getLoginUsers().invalidate(token);
        return true;
    }

    @Override
    public boolean isExpired(UR user) {
        return !logged(user);
    }
}
