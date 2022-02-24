package com.github.md.web.user;

import com.github.md.web.ex.OprNotSupportException;
import com.github.md.web.user.role.UserWithRolesWrapper;

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
    String tokenKey();

    /**
     * 从cookie中获取用户标志时需要用到的key
     *
     * @return
     */
    String cookieKey();

    /**
     * 登录时 获取用户名的key
     * 如:username
     *
     * @return
     */
    String loginKey();

    /**
     * 登录时 获取密码的key
     *
     * @return
     */
    String pwdKey();

    /**
     * 从请求中构建用户，只能获取已登录的用户。
     *
     * @param request
     * @return
     */
    U getUser(HttpServletRequest request);

    /**
     * 登录验证。若验证成功，则返回用户。
     *
     * @param username
     * @param password
     * @return
     */
    U login(String username, String password);

    /**
     * 新建用户动作(注册)
     *
     * @param username
     * @param password
     * @param attr
     */
    default boolean register(String username, String password, Map attr) {
        throw new OprNotSupportException("不支持注册！");
    }

    /**
     * 设置登录状态为已登录，即留存用户信息
     * <p>
     * 例如缓存到redis，或内存，或生成jwt。
     *
     * @param user
     * @return true-设置成功
     */
    boolean setLogged(U user);

    /**
     * 登出操作
     *
     * @param user
     * @return
     */
    boolean logout(U user);

    /**
     * 判断用户是否登录。
     *
     * @param user
     * @return
     */
    boolean logged(U user);

    /**
     * 判断登录是否过期
     *
     * @param user
     * @return
     */
    boolean isExpired(U user);
}
