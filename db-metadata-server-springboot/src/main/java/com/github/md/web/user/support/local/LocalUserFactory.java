package com.github.md.web.user.support.local;

import com.github.md.web.user.LoginService;
import com.github.md.web.user.UserService;
import com.github.md.web.user.AbstractUserFactory;

/**
 * <p> @Date : 2019/12/13 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
@Deprecated
public class LocalUserFactory extends AbstractUserFactory<LocalUser> {

    @Override
    public UserService<LocalUser> userService() {
        return new LocalUserService("user.json");
    }

    @Override
    public LoginService<LocalUser> loginService() {
        return new LocalUserService("user.json");
    }
}
