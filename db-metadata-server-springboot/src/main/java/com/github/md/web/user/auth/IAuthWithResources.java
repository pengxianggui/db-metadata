package com.github.md.web.user.auth;

/**
 * <p> @Date : 2020/9/18 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
public interface IAuthWithResources extends IAuth {

    MResource[] resources();

    void addResource(MResource resource);

    boolean hasResource(String idOrName);

    void removeResource(String idOrName);
}
