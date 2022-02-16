package com.github.md.web.user.role;

import com.github.md.web.user.auth.IAuth;

import java.util.ArrayList;
import java.util.List;

/**
 * <p> @Date : 2020/8/12 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
class DefaultRole implements MRRole {

    private final List<IAuth> innerAuthList;

    private final String code;

    private final String name;

    private IAuth[] auths;

    public DefaultRole(String code, String name) {
        this.code = code;
        this.name = name;
        this.innerAuthList = new ArrayList<>();
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
        if (!innerAuthList.isEmpty()) {
            return innerAuthList.toArray(auths);
        }
        return auths;
    }

    @Override
    public boolean hasAuth(IAuth auth) {
        for (IAuth p : auths) {
            if (p.code().equalsIgnoreCase(auth.code().toLowerCase()) || p.name().equalsIgnoreCase(auth.name().toLowerCase())) {
                return true;
            }
        }
        return false;
    }

    public void addPermission(IAuth auth) {
        innerAuthList.add(auth);
    }
}
