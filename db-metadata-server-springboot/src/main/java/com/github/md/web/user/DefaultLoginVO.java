package com.github.md.web.user;

import lombok.Builder;
import lombok.Getter;

import java.util.Map;
import java.util.Set;

/**
 * @author pengxg
 * @date 2022/4/18 2:11 下午
 */
@Builder
@Getter
public class DefaultLoginVO implements LoginVO {
    private String token;
    private String id;
    private String username;
    private String avatar;
    private Set<String> roles;
    private Set<String> auths;
    private Map<String, Object> attrs;
}
