package com.hthjsj.web;

import com.hthjsj.AnalysisConfig;
import com.hthjsj.web.controller.*;
import com.hthjsj.web.jfinal.ExceptionIntercept;
import com.hthjsj.web.jfinal.fastjson.CrackFastJsonFactory;
import com.hthjsj.web.jfinal.render.ErrorJsonRenderFactory;
import com.jfinal.config.*;
import com.jfinal.server.undertow.UndertowServer;
import com.jfinal.template.Engine;

/**
 * <p> Class title: </p>
 * <p> @Describe: </p>
 * <p> @Date : 2019-08-22 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
public class AppWebConfig extends JFinalConfig {

    public static void main(String[] args) {
        UndertowServer.start(AppWebConfig.class, 8888, true);
    }

    @Override
    public void configConstant(Constants me) {
        loadPropertyFile("config.properties");
        me.setDevMode(getPropertyToBoolean("devMode", false));
        me.setJsonFactory(new CrackFastJsonFactory());
        me.setRenderFactory(new ErrorJsonRenderFactory());
    }

    @Override
    public void configRoute(Routes me) {
        me.setMappingSuperClass(true);
        me.add("/", IndexController.class);
        me.add("/meta", MetaController.class);
        me.add("/db", DBController.class);
        me.add("/component", ComponentController.class);
        me.add("/table", TableController.class);
    }

    @Override
    public void configEngine(Engine me) {

    }

    @Override
    public void configPlugin(Plugins me) {
        AnalysisConfig analysisConfig = new AnalysisConfig();
        analysisConfig.getPlugins().forEach(p -> me.add(p));

    }

    @Override
    public void configInterceptor(Interceptors me) {
        me.add(new ExceptionIntercept());
    }


    @Override
    public void configHandler(Handlers me) {

    }
}
