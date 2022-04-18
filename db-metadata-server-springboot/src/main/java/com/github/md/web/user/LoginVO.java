package com.github.md.web.user;

import java.util.Map;
import java.util.Set;

/**
 * 登录返回
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
}
