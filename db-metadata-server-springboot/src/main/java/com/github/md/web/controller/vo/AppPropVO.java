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
    private boolean enableLogin; // 启用登录
    private boolean enableAuth; // 启用鉴权
    private boolean devMode; // 开发模式
    private String tokenKey; // 前后端token存储于header中key
    private boolean showGreeting = true; // 显示问候


    // 一下配置为纯UI主题配置，前端直接存在localStorage
//    private Header header = new Header();
//    private Menu menu = new Menu();
//    private Tag tag = new Tag();
//
//    /**
//     * header配置
//     */
//    @Getter
//    @Setter
//    public static class Header {
//        private String textColor; // 文字颜色
//        private String backgroundColor; // 背景色
//        private Boolean showGreeting = true; // 显示问候
//    }
//
//    @Getter
//    @Setter
//    public static class Menu {
//        private String textColor; // 只支持hex
//        private String activeTextColor; // 选中菜单的文字颜色
//        private String backgroundColor; // 背景色
//        private Boolean uniqueOpened = false; // 只展开一个
//        private String mode = "vertical"; // 水平: horizontal; 垂直: vertical
//    }
//
//    /**
//     * tag配置
//     */
//    @Getter
//    @Setter
//    public static class Tag {
//        private boolean show;
//        private String color;
//        private String bgColor;
//    }
}
