package com.hthjsj.web.user;

import com.hthjsj.web.user.local.LocalUserFactory;

/**
 * <p> @Date : 2019/12/13 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
public class UserManager {

    private static UserManager me = new UserManager();

    private UserFactory userFactory;

    public static UserManager me() {
        if (me.getUserFactory() == null) {
            me.userFactory = new LocalUserFactory();
        }
        return me;
    }

    public UserFactory getUserFactory() {
        return userFactory;
    }

    public void setUserFactory(UserFactory userFactory) {
        me().setUserFactory(userFactory);
    }

    public UserService userService() {
        return me().getUserFactory().createService();
    }

    public LoginService loginService() {
        return me().getUserFactory().loginService();
    }
}
