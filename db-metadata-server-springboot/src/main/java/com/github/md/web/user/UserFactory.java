package com.github.md.web.user;

import com.github.md.web.user.role.UserWithRolesWrapper;

/**
 * <p> @Date : 2019/12/13 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
@Deprecated
public interface UserFactory<U extends User, UR extends UserWithRolesWrapper> {

    UserService<U> userService();

    LoginService<UR> loginService();
}
