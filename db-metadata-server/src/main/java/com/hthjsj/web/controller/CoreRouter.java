package com.hthjsj.web.controller;

import com.jfinal.config.Routes;

/**
 * 核心开关的控制路由
 * <p> @Date : 2019/12/11 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
public class CoreRouter extends Routes {

    @Override
    public void config() {
        add("/db", DBController.class);
        add("/meta", MetaController.class);
        add("/component", ComponentController.class);
        add("/component/options", OptionsController.class);
        add("/form", FormController.class);
        add("/dict", DictController.class);
        add("/check", ValidatorController.class);

        add("/table", TableController.class);
        add("/find", FindBoxController.class);
    }
}
