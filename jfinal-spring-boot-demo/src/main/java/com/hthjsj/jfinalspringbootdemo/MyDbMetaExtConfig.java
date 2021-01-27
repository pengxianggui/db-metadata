package com.hthjsj.jfinalspringbootdemo;

import com.dbmeta.starter.config.ExtConfig;
import com.jfinal.config.Handlers;
import com.jfinal.ext.handler.UrlSkipHandler;
import org.springframework.context.annotation.Configuration;

/**
 * @author pengxg
 * @date 2021/1/27 2:39 下午
 */
@Configuration
public class MyDbMetaExtConfig extends ExtConfig {
    @Override
    public void configHandler(Handlers me) {
        me.add(new UrlSkipHandler("^/sp/.*", true));
    }
}
