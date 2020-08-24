package com.hthjsj.web.user.support.local;

import com.hthjsj.web.kit.UtilKit;
import com.hthjsj.web.user.User;
import com.hthjsj.web.user.auth.MRRole;
import com.hthjsj.web.user.role.UserWithRolesWrapper;
import com.jfinal.kit.Kv;

import java.util.Map;

/**
 * <p> @Date : 2019/12/13 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
public class LocalUser implements User, UserWithRolesWrapper {

    Kv attrs;

    MRRole[] roles;

    public LocalUser(Map attr) {
        this.attrs = Kv.create().set(attr);
    }

    public LocalUser(Map attr, MRRole role) {
        this.attrs = Kv.create().set(attr);
        this.roles = new MRRole[] { role };
    }

    @Override
    public String userId() {
        return attrs.getStr("userId");
    }

    @Override
    public String userName() {
        return attrs.getStr("userName");
    }

    @Override
    public Kv attrs() {
        return attrs;
    }

    @Override
    public Kv attrs(Map attrs) {
        UtilKit.deepMerge(this.attrs, attrs, true);
        return this.attrs;
    }

    @Override
    public MRRole[] roles() {
        return roles;
    }

    @Override
    public boolean hasRole(String nameOrCode) {
        for (MRRole role : roles) {
            if (role.name().equalsIgnoreCase(nameOrCode.toLowerCase()) || role.code().equalsIgnoreCase(nameOrCode.toLowerCase())) {
                return true;
            }
        }
        return false;
    }
}
