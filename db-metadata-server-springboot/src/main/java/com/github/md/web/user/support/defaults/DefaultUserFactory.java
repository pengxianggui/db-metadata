package com.github.md.web.user.support.defaults;

import com.github.md.web.user.LoginService;
import com.github.md.web.user.UserFactory;
import com.github.md.web.user.UserService;

/**
 * @author pengxg
 * @date 2022/2/18 3:24 下午
 */
public class DefaultUserFactory implements UserFactory<DefaultUser> {

    @Override
    public UserService<DefaultUser> userService() {
        return new DefaultUserService();
    }

    @Override
    public LoginService<DefaultUser> loginService() {
        return new DefaultUserService();
    }
}
