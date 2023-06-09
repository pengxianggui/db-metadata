package com.github.md.web.user.support.local;

import com.github.md.analysis.kit.Kv;
import com.github.md.web.kit.UtilKit;
import com.github.md.web.user.LoginVO;
import com.github.md.web.user.User;
import com.github.md.web.user.auth.IAuth;
import com.github.md.web.user.role.MRRole;
import com.github.md.web.user.role.UserWithRolesWrapper;

import java.util.Arrays;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * <p> @Date : 2019/12/13 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
public class LocalUser implements User, UserWithRolesWrapper, LoginVO {

    private static final String ROOT_USER_ID = "0";
    public static final String ID_KEY = "userId";

    Kv attrs;

    MRRole[] roles;

    public LocalUser(Map attr) {
        this.attrs = Kv.create().set(attr);
    }

    public LocalUser(Map attr, MRRole role) {
        this.attrs = Kv.create().set(attr);
        this.roles = new MRRole[]{role};
    }

    public LocalUser(Map attr, MRRole... roles) {
        this.attrs = Kv.create().set(attr);
        this.roles = roles;
    }

    @Override
    public String userId() {
        return attrs.getStr(ID_KEY);
    }

    @Override
    public String userName() {
        return attrs.getStr("userName");
    }

    public String password() {
        return attrs.getStr("password");
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
    public boolean isRoot() {
        return Objects.equals(userId(), ROOT_USER_ID); // 将id为0视作ROOT
    }

    @Override
    public String avatar() {
        return null;
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
    public String getToken() {
        return userId();
    }

    @Override
    public String getId() {
        return userId();
    }

    @Override
    public String getUsername() {
        return userName();
    }

    @Override
    public String getAvatar() {
        return avatar();
    }

    @Override
    public Set<String> getRoles() {
        return Arrays.stream(roles()).map(MRRole::code).collect(Collectors.toSet());
    }

    @Override
    public Set<String> getAuths() {
        return Arrays.stream(auths()).map(IAuth::code).collect(Collectors.toSet());
    }

    @Override
    public Map<String, Object> getAttrs() {
        return attrs();
    }
}
