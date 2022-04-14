package com.github.md.web.user.role;

import com.github.md.analysis.kit.Kv;
import com.github.md.web.user.auth.IAuth;
import com.github.md.web.user.support.defaults.DefaultUser;
import lombok.Data;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

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

    @Override
    public Kv toKv() {
        return this.user.toKv()
                .set("id", userId())
                .set("username", userName())
                .set("roles", Arrays.stream(roles()).map(MRRole::code).collect(Collectors.toSet()))
                .set("auths", Arrays.stream(auths()).map(IAuth::code).collect(Collectors.toSet()))
                .set("attrs", attrs());
    }
}
