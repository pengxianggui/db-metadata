package com.github.md.web.user;

/**
 * <p> @Date : 2019/12/13 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
@Deprecated
public interface UserFactory<U extends User> {

    UserService<U> userService();

    LoginService<U> loginService();
}
