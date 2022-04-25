package com.github.md.web.user;

/**
 * 鉴权配置器。
 * <p>
 * 实现此接口，并注册Spring bean实现，认证授权的自定义
 *
 * @author pengxg
 * @date 2022/2/28 8:50 上午
 */
public interface AuthenticationConfigurer {

    /**
     * 用户、权限相关配置
     *
     * @param registry
     */
    default void configAuthentication(AuthenticationRegistry registry) {
    }
}
