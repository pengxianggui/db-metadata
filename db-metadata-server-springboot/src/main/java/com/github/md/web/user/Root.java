package com.github.md.web.user;

import com.github.md.analysis.kit.Kv;
import com.github.md.web.ServiceManager;
import com.github.md.web.config.MetaProperties;
import com.github.md.web.ex.OprNotSupportException;
import com.github.md.web.user.auth.IAuth;
import com.github.md.web.user.role.MRRole;
import com.github.md.web.user.role.UserWithRolesWrapper;
import com.github.md.web.user.support.defaults.DefaultUser;
import com.jfinal.plugin.activerecord.Record;

import java.util.HashMap;
import java.util.Map;

/**
 * 静态ROOT用户, 右配置 meta.app.root决定
 *
 * @author pengxg
 * @date 2022/3/7 8:55 上午
 */
public class Root extends DefaultUser implements UserWithRolesWrapper {
    private static Root instance;

    private Root(Record record) {
        super(record);
    }

    public static Root me() {
        if (instance == null) {
            synchronized (Root.class) {
                if (instance == null) {
                    Map<String, Object> attrs = new HashMap<>();
                    attrs.put("id", "0");
                    attrs.put("username", "ROOT");
                    attrs.put("password", "888888");


                    MetaProperties metaProp = ServiceManager.getAppProperties();
                    Map<String, String> root = metaProp.getApp().getRoot();

                    if (root != null) {
                        if (root.containsKey("id")) {
                            attrs.put("id", root.get("id"));
                        }
                        if (root.containsKey("username")) {
                            attrs.put("username", root.get("username"));
                        }
                        if (root.containsKey("password")) {
                            attrs.put("password", root.get("password"));
                        }
                        if (root.containsKey("avatar")) {
                            attrs.put("avatar", root.get("avatar"));
                        }
                    }

                    Record record = new Record();
                    record.setColumns(attrs);
                    record.set("attrs", attrs);
                    instance = new Root(record);
                }
            }
        }
        return instance;
    }

    @Override
    public Kv attrs(Map attrs) {
        throw new OprNotSupportException("ROOT用户不允许动态添加属性");
    }

    @Override
    public MRRole[] roles() {
        return new MRRole[0];
    }

    @Override
    public boolean hasRole(String nameOrCode) {
        return true;
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
        return true;
    }
}
