package com.github.md.web.controller;

import com.github.md.analysis.kit.Ret;
import com.github.md.web.Res;
import com.github.md.web.ServiceManager;
import com.github.md.web.app.AppConfig;
import com.github.md.web.config.MetaProperties;
import com.github.md.web.controller.vo.AppPropVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author pengxg
 * @date 2022/2/17 2:39 下午
 */
@RestController
@RequestMapping("/app")
public class AppController extends ControllerAdapter {

    @Autowired
    private MetaProperties metaProperties;

    /**
     * 获取系统配置。包含配置文件维护的静态配置 + 数据库维护的动态配置。
     *
     * @return
     */
    @GetMapping("config")
    public Res config() {
        AppConfig appConfig = ServiceManager.getAppConfigService().getLatest(true);
        return Res.ok(new AppPropVO(metaProperties, appConfig));
    }

    /**
     * 获取系统动态配置，即meta_app_config表中最新应用的动态配置。
     *
     * @return
     */
    @GetMapping("dynamic-config")
    public Res dynamicConfig() {
        AppConfig appConfig = ServiceManager.getAppConfigService().getLatest(false);
        return Res.ok(appConfig);
    }
}
