package com.github.md.web.controller;

import com.github.md.analysis.kit.Ret;
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

    @GetMapping("config")
    public Ret config() {
        AppPropVO vo = new AppPropVO(
                metaProperties.getApp().getName(),
                metaProperties.getApp().getLogo(),
                metaProperties.getApp().getRegisterable(),
                metaProperties.getApp().getAddable(),
                metaProperties.getServer().isEnableCertification(),
                metaProperties.isDevMode(),
                metaProperties.getServer().getLogin().getTokenKey(),
                metaProperties.getApp().getLoginBg(),
                metaProperties.getApp().getShowGreeting(),
                metaProperties.getApp().getShowThemeSetting(),
                metaProperties.getApp().getAllowCustomTheme()
        );
        return Ret.ok("data", vo);
    }
}
