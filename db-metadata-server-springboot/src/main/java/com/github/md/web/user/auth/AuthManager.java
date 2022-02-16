package com.github.md.web.user.auth;

import com.github.md.web.user.role.MRRole;

/**
 * <p> @Date : 2020/9/18 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
public interface AuthManager {

    boolean roleHasPermission(IAuth auth);

    boolean roleHasResource(MResource resource);

    MRRole[] getRoles();
}
