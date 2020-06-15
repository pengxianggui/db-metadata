package com.hthjsj.web.user;

import com.hthjsj.web.user.local.LocalUserFactory;

/**
 * <p> @Date : 2019/12/13 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
public class UserManager {

    private static final UserManager me = new UserManager();

    private UserFactory userFactory;

    public static UserManager me() {
        if (me.userFactory == null) {
            me.userFactory = new LocalUserFactory();
        }
        return me;
    }

    public UserFactory getUserFactory() {
        return userFactory;
    }

    public void setUserFactory(UserFactory userFactory) {
        userFactory = userFactory;
    }

    public UserService userService() {
        return getUserFactory().createService();
    }

    public LoginService loginService() {
        return getUserFactory().loginService();
    }
}
