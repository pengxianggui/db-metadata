package com.github.md.web.user;

import java.util.Map;
import java.util.Set;

/**
 * very important! 此接口是前后端用户数据结构一致的保证。
 *
 * @author pengxg
 * @date 2022/2/28 6:10 下午
 */
public interface LoginVO {
    String getToken();

    String getId();

    String getUsername();

    String getAvatar();

    Set<String> getRoles();

    Set<String> getAuths();

    Map<String, Object> getAttrs();

    /**
     * 若返回true，则不进行鉴权。拥有所有权限
     *
     * @return
     */
    default boolean isRoot() {
        return false;
    }
}
