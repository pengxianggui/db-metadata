package com.github.md.web.user.role;

import com.github.md.analysis.kit.Kv;
import com.github.md.web.user.User;
import com.github.md.web.user.support.defaults.DefaultUser;
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

    private DefaultUser user;

    public DefaultUserWithRoles(DefaultUser user, MRRole... roles) {
        this.user = user;
        this.roles = (roles != null ? roles : new MRRole[0]);
    }

    @Override
    public String avatar() {
        return user.avatar();
    }

    @Override
    public MRRole[] roles() {
        return roles;
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
