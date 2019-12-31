package com.hthjsj.jfinalspringbootdemo;

import com.github.artislong.annotation.JFinalScan;
import com.github.artislong.annotation.RouterPath;
import com.hthjsj.web.AppWebConfig;
import com.jfinal.aop.Interceptor;
import com.jfinal.config.JFinalConfig;
import com.jfinal.config.Routes;
import com.jfinal.core.Controller;
import com.jfinal.core.JFinalFilter;
import com.jfinal.handler.Handler;
import com.jfinal.render.Render;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;

@JFinalScan(basePackages = "com.hthjsj", markerInterfaces = { Controller.class, Interceptor.class, Routes.class, Handler.class,
        Render.class }, annotationClass = RouterPath.class)
@SpringBootApplication
public class JFinalSpringbootDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(JFinalSpringbootDemoApplication.class, args);
    }

    @Bean
    public AppWebConfig appWebConfig() {
        return new AppWebConfig();
    }

    @Bean
    public AppJFinalConfig jFinalConfig(AppWebConfig config) {
        return new AppJFinalConfig(config);
    }
}
