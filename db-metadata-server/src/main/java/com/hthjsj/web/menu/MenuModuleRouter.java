package com.hthjsj.web.menu;

import com.jfinal.config.Routes;

/**
 * <p> @Date : 2020/8/6 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
public class MenuModuleRouter extends Routes {

    @Override
    public void config() {
        add("/menu", MenuController.class);
        add("/router", RouterController.class);
    }
}
