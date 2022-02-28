package com.github.md.web.user;

import com.github.md.analysis.kit.Kv;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Set;

/**
 * 登录返回
 *
 * @author pengxg
 * @date 2022/2/28 6:10 下午
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class LoginVO {
    private String token;
    private String id;
    private String username;
    private Set<String> roles;
    private Set<String> auths;
    private Kv attrs;
}
