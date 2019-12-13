package com.hthjsj.web.user;

import com.jfinal.config.Routes;

/**
 * <p> @Date : 2019/12/13 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
public class UserRouter extends Routes {

    public static final String URL_PREFIX = "/user";

    @Override
    public void config() {
        add(URL_PREFIX, UserController.class);
    }
}
