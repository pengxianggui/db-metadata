package com.github.md.web.controller.vo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author pengxg
 * @date 2022/2/17 2:40 下午
 */
@NoArgsConstructor
@Getter
@Setter
public class AppPropVO {
    private String version; // dbmeta版本
    private String name; // 系统名
    private String logo; // 系统logo
    private boolean registerable; // 用户是否可注册
    private boolean addable; // 用户是否可添加
    private boolean enableCertification; // 启用登录
    private boolean devMode; // 开发模式
    private String tokenKey; // 前后端token存储于header中key
    private String tokenIn; // 前端token存储位置，可选: localStorage 或 cookies
    private String loginKey; // 登录时的用户名键
    private String pwdKey; // 登录时的密码键
    private String loginBg;
    private boolean showGreeting = true; // 显示问候
    private boolean showThemeSetting = true; // 显示主题设置入口
    private boolean allowCustomTheme = false; // 是否允许用户自定义主题
    private String docUrl; // dbmeta官方文档地址


    public AppPropVO(String version,
                     String name,
                     String logo,
                     boolean registerable,
                     boolean enableCertification,
                     boolean devMode,
                     String tokenKey,
                     String tokenIn,
                     String loginKey,
                     String pwdKey,
                     String loginBg,
                     boolean showGreeting,
                     boolean showThemeSetting,
                     boolean allowCustomTheme,
                     String docUrl) {
        this.version = version;
        this.name = name;
        this.logo = logo;
        this.registerable = registerable;
        this.enableCertification = enableCertification;
        this.devMode = devMode;
        this.tokenKey = tokenKey;
        this.tokenIn = tokenIn;
        this.loginKey = loginKey;
        this.pwdKey = pwdKey;
        this.loginBg = loginBg;
        this.showGreeting = showGreeting;
        this.showThemeSetting = showThemeSetting;
        this.allowCustomTheme = allowCustomTheme;
        this.docUrl = docUrl;
    }
}
