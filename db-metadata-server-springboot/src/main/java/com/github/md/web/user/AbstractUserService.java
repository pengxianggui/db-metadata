package com.github.md.web.user;

import com.github.md.web.user.role.UserWithRolesWrapper;
import com.github.md.web.user.support.defaults.DefaultTokenGenerator;
import com.jfinal.kit.StrKit;
import lombok.AllArgsConstructor;

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
@AllArgsConstructor
public abstract class AbstractUserService<U extends User, UR extends UserWithRolesWrapper> implements UserService<U>, LoginService<UR> {

    private final TokenGenerator tokenGenerator;

    public AbstractUserService() {
        this(new DefaultTokenGenerator());
    }

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
        token = StrKit.defaultIfBlank(token, getToken(request));
        if (StrKit.notBlank(token)) {
            UR user = (UR) AuthenticationManager.me().getLoginUsers().getIfPresent(token);
            return user;
        }
        return null;
    }

    @Override
    public String setLogged(UR user) {
        String token = tokenGenerator.generate(user);
        AuthenticationManager.me().getLoginUsers().put(token, user); // 缓存到内存中
        return token;
    }

    @Override
    public boolean logged(UR user) {
        String token = tokenGenerator.generate(user);
        return AuthenticationManager.me().getLoginUsers().getIfPresent(token) != null;
    }

    @Override
    public boolean logout(UR user) {
        String token = tokenGenerator.generate(user);
        AuthenticationManager.me().getLoginUsers().invalidate(token);
        return !logged(user);
    }

    @Override
    public boolean isExpired(UR user) {
        return !logged(user);
    }
}
