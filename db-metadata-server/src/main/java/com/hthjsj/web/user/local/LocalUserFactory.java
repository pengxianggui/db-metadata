package com.hthjsj.web.user.local;

import com.hthjsj.web.user.AbstractUserFactory;
import com.hthjsj.web.user.LoginService;
import com.hthjsj.web.user.UserService;

/**
 * <p> @Date : 2019/12/13 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
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
