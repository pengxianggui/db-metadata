package com.hthjsj.jfinalspringbootdemo;

import com.dbmeta.starter.annotation.JFinalScan;
import com.dbmeta.starter.annotation.RouterPath;
import com.jfinal.aop.Interceptor;
import com.jfinal.config.Routes;
import com.jfinal.core.Controller;
import com.jfinal.handler.Handler;
import com.jfinal.render.Render;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@JFinalScan(basePackages = "com.hthjsj.jfinalspringbootdemo", markerInterfaces = {Controller.class, Interceptor.class, Routes.class, Handler.class,
        Render.class}, annotationClass = RouterPath.class)
@SpringBootApplication
public class JFinalSpringbootDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(JFinalSpringbootDemoApplication.class, args);
    }

//
//    @Bean
//    public DefaultJFinalConfig jFinalConfig() {
//        DefaultJFinalConfig defaultJFinalConfig = new DefaultJFinalConfig();
//
//        // 配置扩展方式
//        defaultJFinalConfig.addExtConfig(new ExtJFinalConfig() {
//            @Override
//            public void configHandler(Handlers me) {
//                me.add(new UrlSkipHandler("^/sp/.*", true));
//            }
//        });
//
//        defaultJFinalConfig.userConfig(new ExtJFinalConfig() {
//            @Override
//            public void configInterceptor(Interceptors me) {
//                super.configInterceptor(me);
//            }
//        });
//
//
//        return defaultJFinalConfig;
//    }
}
