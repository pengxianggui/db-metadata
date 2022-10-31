package com.github.md.web.user;

import com.github.md.web.ServiceManager;
import com.github.md.web.ex.OprNotSupportException;
import com.github.md.web.user.role.UserWithRolesWrapper;
import org.springframework.util.CollectionUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * <p> @Date : 2019/12/13 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
public interface LoginService<U extends UserWithRolesWrapper> {

    /**
     * 从Request中获取用户标识时需要用到得key;
     *
     * @return
     */
    default String tokenKey() {
        return ServiceManager.getAppProperties().getServer().getLogin().getTokenKey();
    }

    /**
     * 从cookie中获取用户标志时需要用到的key
     *
     * @return
     */
    @Deprecated
    default String cookieKey() {
        return ServiceManager.getAppProperties().getServer().getLogin().getCookieKey();
    }

    /**
     * 登录时 获取用户名的key
     * 如:username
     *
     * @return
     */
    default String loginKey() {
        return ServiceManager.getAppProperties().getServer().getLogin().getLoginKey();
    }

    /**
     * 登录时 获取密码的key
     *
     * @return
     */
    default String pwdKey() {
        return ServiceManager.getAppProperties().getServer().getLogin().getPwdKey();
    }

    /**
     * 从请求中构建用户，只能获取已登录的用户。
     *
     * @param request
     * @return
     */
    U getUser(HttpServletRequest request);

    /**
     * 刷新已登录的用户，主要是当用户角色、权限发生变化，需要刷新缓存中的登录用户信息，否则用户需要重新登录，体验不好。
     * <p>
     * 若用户未登录，则不执行任何操作。
     *
     * @param userId 用户主键
     * @return
     */
    default void refresh(String userId) {
    }

    /**
     * 登录验证。若验证成功，则返回用户。
     *
     * @param username
     * @param password
     * @return
     */
    U login(String username, String password);

    /**
     * 获取登录信息
     *
     * @param request
     * @return
     */
    LoginVO getInfo(HttpServletRequest request);

    /**
     * 新建用户动作(注册)
     *
     * @param username
     * @param password
     * @param attr
     */
    default boolean register(String username, String password, Map attr) {
        throw new OprNotSupportException("请实现注册方法！");
    }

    /**
     * 设置登录状态为已登录，即留存用户信息
     * <p>
     * 例如缓存到redis，或内存，或生成jwt。
     *
     * <b>注意: 系统仅仅在用户登录时调用此方法；系统不会在其他地方调用(除非你自己这么做，但是不建议！), 因为若方法中你基于user生成token, 往往token
     * 会不同，例如当用户更新了角色，系统可能要为ta刷新登录信息，若调用这个方法，可能因为token不同而导致刷新无效。</b>
     *
     * @param user
     * @return token 返回token
     */
    LoginVO setLogged(U user);

    /**
     * 登出操作
     *
     * @param user
     * @return
     */
    boolean logout(U user);

    /**
     * 获取所有已登录的用户
     *
     * @return
     */
    Map<String, U> getAllLoggedUsers();

    /**
     * 判断用户是否登录。
     *
     * @param user
     * @return
     */
    boolean logged(U user);

    /**
     * 判断用户是否登录
     *
     * @param userId 用户主键
     * @return
     */
    default boolean logged(String userId) {
        Map<String, U> loggedUsers = getAllLoggedUsers();
        if (CollectionUtils.isEmpty(loggedUsers)) {
            return false;
        }
        return loggedUsers.values().stream().anyMatch(u -> u.userId().equals(userId));
    }

    /**
     * 判断登录是否过期
     *
     * @param user
     * @return
     */
    boolean isExpired(U user);
}
