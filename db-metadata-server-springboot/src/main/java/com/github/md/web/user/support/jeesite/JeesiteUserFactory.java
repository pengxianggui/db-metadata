package com.github.md.web.user.support.jeesite;

import com.github.md.web.user.AbstractUserFactory;
import com.github.md.web.user.LoginService;
import com.github.md.web.user.UserService;

/**
 * <p> @Date : 2019/12/13 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
public class JeesiteUserFactory extends AbstractUserFactory<JeesiteUser> {

    @Override
    public UserService<JeesiteUser> userService() {
        return new JeesiteUserService();
    }

    @Override
    public LoginService<JeesiteUser> loginService() {
        return new JeesiteUserService();
    }
}
