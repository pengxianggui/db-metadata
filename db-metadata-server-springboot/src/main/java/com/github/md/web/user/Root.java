package com.github.md.web.user;

import com.github.md.analysis.kit.Kv;
import com.github.md.web.ServiceManager;
import com.github.md.web.config.MetaProperties;
import com.github.md.web.ex.OprNotSupportException;
import com.github.md.web.kit.PassKit;
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
 * @deprecated 静态用户的缺点是很明显的。首先是灵活度上ROOT用户只能有一个账号，而且ROOT用户存在于配置文件中，跟数据库里的用户表是分开的，处理上很不便(这也是为什么
 * {@link AuthenticationManager#login(String, String)})中需要区分对待ROOT账号的原因，而且安全性上，也比较欠妥。
 */
@Deprecated
public final class Root implements UserWithRolesWrapper {
    private static Root instance;

    private String id;
    private String username; // 登录名
    private String password; // 登录密码
    private String avatar;
    private Map<String, String> attrs;

    private Root(Map<String, String> root) {
        final String loginKey = ServiceManager.getAppProperties().getServer().getLogin().getLoginKey();
        final String passKey = ServiceManager.getAppProperties().getServer().getLogin().getPwdKey();

        this.id = root.getOrDefault("id", "0");
        this.username = root.getOrDefault(loginKey, "ROOT");
        this.password = root.getOrDefault(passKey, PassKit.encryptPass("888888"));
        this.avatar = root.getOrDefault("avatar", "root");

        Map<String, String> attrs = new HashMap<>();
        attrs.put("id", id);
        attrs.put("username", username);
        attrs.put("avatar", avatar);
        this.attrs = attrs;
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

                    instance = new Root(root);
                }
            }
        }
        return instance;
    }

    /**
     * ROOT登录账号
     *
     * @return
     */
    public String getLoginName() {
        return username;
    }

    /**
     * ROOT登录密码
     *
     * @return
     */
    public String getLoginPass() {
        return password;
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
