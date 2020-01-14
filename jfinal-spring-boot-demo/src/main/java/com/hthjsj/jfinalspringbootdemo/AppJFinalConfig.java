package com.hthjsj.jfinalspringbootdemo;

import com.github.artislong.config.DefaultJFinalConfig;
import com.hthjsj.web.AppWebConfig;
import com.jfinal.config.*;
import com.jfinal.ext.handler.UrlSkipHandler;

/**
 * spring.factories -> SpringJFinalConfiguration -> FilterRegistrationBean
 * -> JFinalFilter -> AppJFinalConfig
 * <p> @Date : 2019/12/31 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
public class AppJFinalConfig extends DefaultJFinalConfig {

    private AppWebConfig appWebConfig;

    public AppJFinalConfig(AppWebConfig appWebConfig) {
        this.appWebConfig = appWebConfig;
    }

    @Override
    public void configConstant(Constants me) {
        super.configConstant(me);
        appWebConfig.configConstant(me);
    }

    @Override
    public void configPlugin(Plugins me) {
        appWebConfig.configPlugin(me);
    }

    @Override
    public void configRoute(Routes me) {
        appWebConfig.configRoute(me);
        me.add("/spring-boot", IndexController.class);
    }

    @Override
    public void configInterceptor(Interceptors me) {
        appWebConfig.configInterceptor(me);
    }

    @Override
    public void configHandler(Handlers me) {
        appWebConfig.configHandler(me);
        me.add(new UrlSkipHandler("^/sp/.*", true));
    }

    @Override
    public void onStart() {
        appWebConfig.onStart();
    }
}
