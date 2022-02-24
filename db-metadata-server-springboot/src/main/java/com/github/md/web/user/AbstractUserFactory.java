package com.github.md.web.user;

import com.github.md.web.user.role.UserWithRolesWrapper;

/**
 * <p> @Date : 2019/12/13 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
@Deprecated
public abstract class AbstractUserFactory<U extends User, UR extends UserWithRolesWrapper> implements UserFactory<U, UR> {

}
