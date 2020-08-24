package com.hthjsj.web.user.role;

import com.hthjsj.web.user.User;
import com.hthjsj.web.user.auth.MRRole;
import com.jfinal.kit.Kv;
import lombok.Data;

import java.util.Map;

/**
 * <p> @Date : 2020/8/12 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
@Data
public class DefaultUserWithRoles implements UserWithRolesWrapper {

    private MRRole[] roles;

    private User user;

    public DefaultUserWithRoles(User user, MRRole role) {
        this.user = user;
        this.roles = new MRRole[] { role };
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

    @Override
    public String userId() {
        return user.userId();
    }

    @Override
    public String userName() {
        return user.userName();
    }

    @Override
    public Kv attrs() {
        return user.attrs();
    }

    @Override
    public Kv attrs(Map attrs) {
        return user.attrs(attrs);
    }
}
