package com.github.md.web.user;

import com.github.md.analysis.kit.Kv;
import com.github.md.web.ServiceManager;
import com.github.md.web.config.MetaProperties;
import com.github.md.web.ex.OprNotSupportException;
import com.github.md.web.user.auth.IAuth;
import com.github.md.web.user.role.MRRole;
import com.github.md.web.user.role.UserWithRolesWrapper;

import java.util.HashMap;
import java.util.Map;

/**
 * 静态ROOT用户, 由配置 meta.app.root决定
 *
 * @author pengxg
 * @date 2022/3/7 8:55 上午
 */
public final class Root implements UserWithRolesWrapper {
    private static Root instance;

    private String id;
    private String username;
    private String password;
    private String avatar;
    private Map<String, String> attrs;

    private Root(Map<String, String> root) {
        this.id = root.get("id");
        this.username = root.get("username");
        this.password = root.get("password");
        this.avatar = root.get("avatar");
        this.attrs = root;
    }

    public static Root me() {
        if (instance == null) {
            synchronized (Root.class) {
                if (instance == null) {
                    MetaProperties metaProp = ServiceManager.getAppProperties();
                    Map<String, String> root = metaProp.getApp().getRoot();

                    if (root == null) {
                        root = new HashMap<>();
                    }

                    if (!root.containsKey("id")) {
                        root.put("id", "0");
                    }
                    if (!root.containsKey("username")) {
                        root.put("username", "ROOT");
                    }
                    if (!root.containsKey("password")) {
                        root.put("password", "888888");
                    }
                    if (!root.containsKey("avatar")) {
                        root.put("avatar", "root");
                    }

                    instance = new Root(root);
                }
            }
        }
        return instance;
    }

    @Override
    public String userId() {
        return id;
    }

    @Override
    public String userName() {
        return username;
    }

    @Override
    public Kv attrs() {
        return Kv.create().set(attrs);
    }

    @Override
    public Kv attrs(Map attrs) {
        throw new OprNotSupportException("ROOT用户不允许动态添加属性");
    }

    @Override
    public String avatar() {
        return avatar;
    }

    @Override
    public String phone() {
        return null;
    }

    @Override
    public String email() {
        return null;
    }

    @Override
    public MRRole[] roles() {
        return new MRRole[0];
    }

    @Override
    public boolean hasRole(String nameOrCode) {
        return true; // ROOT用户视为有一切角色
    }

    @Override
    public IAuth[] auths() {
        return new IAuth[0];
    }

    @Override
    public boolean hasAuth(String... authCodes) {
        return true;
    }

    @Override
    public boolean hasAuth(IAuth... auths) {
        return true; // ROOT用户视为有一切权限
    }
}
