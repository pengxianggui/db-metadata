package com.github.md.web.controller.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author pengxg
 * @date 2022/2/17 2:40 下午
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AppPropVO {
    private String name;
    private String logo;
    private boolean registerable;
    private boolean addable;
    private boolean enableLogin;
    private boolean enableAuth;
    private boolean devMode;
    private String tokenKey;
}
