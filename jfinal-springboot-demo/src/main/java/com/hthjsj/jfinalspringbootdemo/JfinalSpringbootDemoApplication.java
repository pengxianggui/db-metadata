package com.hthjsj.jfinalspringbootdemo;

import com.github.artislong.annotation.JFinalScan;
import com.github.artislong.annotation.RouterPath;
import com.jfinal.aop.Interceptor;
import com.jfinal.config.Routes;
import com.jfinal.core.Controller;
import com.jfinal.handler.Handler;
import com.jfinal.render.Render;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@JFinalScan(basePackages = "com.hthjsj.jfinalspringbootdemo", markerInterfaces = { Controller.class, Interceptor.class, Routes.class, Handler.class,
        Render.class }, annotationClass = RouterPath.class)
@SpringBootApplication
public class JfinalSpringbootDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(JfinalSpringbootDemoApplication.class, args);
    }
}
