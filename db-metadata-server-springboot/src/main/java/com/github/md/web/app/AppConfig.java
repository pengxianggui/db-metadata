package com.github.md.web.app;

import com.jfinal.plugin.activerecord.Record;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AppConfig implements Serializable {
    private String id;
    private String name;
    private String logo;
    private Boolean registerable;
    private String defaultPass;
    private String passEncryptKey;
    private String loginBg;
    private String resetPass;
    private Boolean showGreeting;
    private Boolean showThemeSetting;
    private Boolean allowCustomTheme;
    private Integer version;

    public AppConfig(Record r) {
        this(
                r.getStr("id"),
                r.getStr("name"),
                r.getStr("logo"),
                r.getBoolean("registerable"),
                r.getStr("default_pass"),
                r.getStr("pass_encrypt_key"),
                r.getStr("login_bg"),
                r.getStr("reset_pass"),
                r.getBoolean("show_greeting"),
                r.getBoolean("show_theme_setting"),
                r.getBoolean("allow_custom_theme"),
                r.getInt("version")
        );
    }
}
