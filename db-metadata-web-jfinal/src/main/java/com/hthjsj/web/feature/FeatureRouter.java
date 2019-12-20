package com.hthjsj.web.feature;

import com.hthjsj.web.feature.ms.MasterSlaveController;
import com.jfinal.config.Routes;

/**
 * [功能] 模块的设计,集中管理该模块下的映射规则
 * <p> @Date : 2019/12/9 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
public class FeatureRouter extends Routes {

    @Override
    public void config() {
        add("/f/ms", MasterSlaveController.class);
        add("/f", FeatureController.class);

        add("/feature/masterSlave", MasterSlaveController.class);
        add("/feature", FeatureController.class);
    }
}
