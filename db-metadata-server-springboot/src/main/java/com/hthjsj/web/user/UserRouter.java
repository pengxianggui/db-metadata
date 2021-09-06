package com.hthjsj.web.user;

import com.hthjsj.web.DefaultRouter;

/**
 * <p> @Date : 2019/12/13 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
public class UserRouter extends DefaultRouter {

    public static final String URL_PREFIX = "/user";

    public static final String URL_LOGIN = "/user/login";

    @Override
    public void config() {
        add(URL_PREFIX, UserController.class);
    }
}
