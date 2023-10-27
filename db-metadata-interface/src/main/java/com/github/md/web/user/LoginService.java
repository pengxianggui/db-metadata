package com.github.md.web.user;

import com.github.md.web.ex.WebException;
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
     * @return request中存储token的key值
     */
    String tokenKey();

    /**
     * 从Request中获取用户标识时需要用到的tokenIn, 标识token是存储在cookie中，还是请求头中
     *
     * @return 返回token在浏览器中存储的位置。如在session还是localStorage中
     */
    String tokenIn();

    /**
     * 从cookie中获取用户标志时需要用到的key
     *
     * @return token在cookie中存储的key值
     * @deprecated 废弃，前端请求时无论token放在header还是cookie， 其键名都通过{@link #tokenKey()}指定
     */
    @Deprecated
    default String cookieKey() {
        return null;
    }

    /**
     * 登录时 获取用户名的key
     * 如:username
     *
     * @return 登录时用户名的键值
     */
    String loginKey();

    /**
     * 登录时 获取密码的key
     *
     * @return 登录时密码的键名
     */
    String pwdKey();

    /**
     * 从请求中构建用户，只能获取已登录的用户。
     *
     * @param request
     * @return 登录的用户对象
     */
    U getUser(HttpServletRequest request);

    /**
     * 通过token获取用户信息
     *
     * @param token
     * @return
     */
    U getUser(String token);

    /**
     * 刷新已登录的用户，主要是当用户角色、权限发生变化，需要刷新缓存中的登录用户信息，否则用户需要重新登录，体验不好。
     * <p>
     * 若用户未登录，则不执行任何操作。
     *
     * @param userId 用户主键
     */
    default void refresh(String userId) {
    }

    /**
     * 登录验证。若验证成功，则返回用户。你应当在此方法中调用 {@link #setLogged(UserWithRolesWrapper)}, 以便缓存登录用户
     *
     * @param identity 一般为用户名、或手机号，也可以是其它能唯一定位用户的属性(取决于实现)
     * @param password 密码
     * @return 返回验证成功的用户对象
     */
    U login(String identity, String password);

    /**
     * 获取登录信息
     *
     * @param request
     * @return 登录信息
     */
    LoginVO getInfo(HttpServletRequest request);

    /**
     * 新建用户动作(注册)
     *
     * @param username 用户名
     * @param password 密码
     * @param attr 其它属性
     * @return 成功与否
     */
    default boolean register(String username, String password, Map attr) {
        throw new WebException("请实现注册方法！");
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
     * @deprecated 使用 {@link #logout(HttpServletRequest)} 替代。
     */
    @Deprecated
    boolean logout(U user);

    /**
     * 登出操作。用户自己主动登出。
     *
     * @param request
     * @return 是否登出成功
     */
    boolean logout(HttpServletRequest request);

    /**
     * 获取所有已登录的用户
     *
     * @return 返回所有已登录用户。key一般为用户id(或其他能唯一标识用户的属性，取决于具体实现)
     */
    Map<String, U> getAllLoggedUsers();

    /**
     * 判断用户是否已登录。
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
