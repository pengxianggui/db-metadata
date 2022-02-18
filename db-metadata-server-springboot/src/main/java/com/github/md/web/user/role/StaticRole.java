package com.github.md.web.user.role;

import com.github.md.analysis.kit.Kv;
import com.github.md.web.user.auth.IAuth;

import java.util.ArrayList;
import java.util.List;

/**
 * @author pengxg
 * @date 2022/2/18 4:58 下午
 */
public class StaticRole implements MRRole {

    private String code;
    private String name;
    private final List<IAuth> auths;

    public StaticRole(String code, String name) {
        this.code = code;
        this.name = name;
        this.auths = new ArrayList<>();
    }

    @Override
    public String id() {
        return null;
    }

    @Override
    public String code() {
        return code;
    }

    @Override
    public String name() {
        return name;
    }

    @Override
    public IAuth[] auths() {
        return auths.toArray(new IAuth[auths.size()]);
    }

    @Override
    public boolean hasAuth(IAuth auth) {
        for (IAuth a : auths) {
            if (a.code().equalsIgnoreCase(auth.code().toLowerCase()) || a.name().equalsIgnoreCase(auth.name().toLowerCase())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Kv toKv() {
        return Kv.create()
                .set("code", code)
                .set("name", name);
    }

    public void addPermission(IAuth auth) {
        auths.add(auth);
    }
}
