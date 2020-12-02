package com.hthjsj.web.user.auth;

/**
 * <p> @Date : 2020/9/18 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
public interface AuthManager {

    boolean roleHasPermission(Permission permission);

    boolean roleHasResource(MResource resource);

    MRRole[] getRoles();
}
