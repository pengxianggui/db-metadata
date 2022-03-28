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
    private String name; // 系统名
    private String logo; // 系统logo
    private boolean registerable; // 用户是否可注册
    private boolean addable; // 用户是否可添加
    private boolean enableCertification; // 启用登录
    private boolean devMode; // 开发模式
    private String tokenKey; // 前后端token存储于header中key
    private boolean showGreeting = true; // 显示问候
}
