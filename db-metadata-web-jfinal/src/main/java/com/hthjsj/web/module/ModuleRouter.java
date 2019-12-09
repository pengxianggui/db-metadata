package com.hthjsj.web.module;

import com.hthjsj.web.module.ms.MasterSlaveController;
import com.jfinal.config.Routes;

/**
 * <p> @Date : 2019/12/9 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
public class ModuleRouter extends Routes {

    @Override
    public void config() {
        add("/m/ms", MasterSlaveController.class);
    }
}
