package com.github.md.web.user.role;

import com.github.md.web.user.User;
import com.github.md.web.user.auth.MRRole;
import com.github.md.web.user.auth.Permission;

/**
 * <p> @Date : 2020/8/12 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
public interface UserWithRolesWrapper extends User {

    MRRole[] roles();

    boolean hasRole(String nameOrCode);

    default Permission[] permissions() {
        return null;
    }

    default boolean hasPermission(String... permissions) {
        return false;
    }

    default boolean hasPermission(Permission... permissions) {
        return false;
    }
}
