package com.github.md.web.user;

import com.github.md.web.ServiceManager;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * <p> @Date : 2019/12/13 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
public interface LoginService<U extends User> {

    /**
     * 从Request中获取用户标识时需要用到得tokenKey;
     *
     * @return
     */
    default String tokenKey() {
        return ServiceManager.getAppProperties().getServer().getLogin().getTokenKey();
    }

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

    U getUser(HttpServletRequest request);

    /**
     * 实现登录。并设置好登录状态。例如缓存到redis，或内存，或生成jwt
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
        return false;
    }

    /**
     * 如外部已完成用户的login动作,可以将User用户手动登入
     * 方法主要逻辑在于显示得将某个用户注册到验证容器中
     *
     * @param user
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
