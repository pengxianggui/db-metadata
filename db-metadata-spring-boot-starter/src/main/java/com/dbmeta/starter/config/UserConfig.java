package com.dbmeta.starter.config;

import com.hthjsj.web.AppConst;
import com.hthjsj.web.ExtJFinalConfig;
import com.hthjsj.web.user.UserIntercept;
import com.hthjsj.web.user.UserRouter;
import com.hthjsj.web.user.auth.JsonUserPermit;
import com.hthjsj.web.user.auth.MRAuthIntercept;
import com.hthjsj.web.user.auth.MRManager;
import com.hthjsj.web.user.auth.jfinal.JFinalResourceLoader;
import com.jfinal.config.Interceptors;
import com.jfinal.config.Routes;

/**
 * @author pengxg
 * 2021/1/27 2:13 下午
 */
public class UserConfig extends ExtJFinalConfig {
    @Override
    public void configRoute(Routes me) {
        if (AppConst.getProp().getBoolean(AppConst.NEED_LOGIN)) {
            me.add(new UserRouter());
        }
    }

    @Override
    public void configInterceptor(Interceptors me) {
        if (AppConst.getProp().getBoolean(AppConst.NEED_LOGIN)) {
            me.add(new UserIntercept());
        }
        if (AppConst.getProp().getBoolean(AppConst.NEED_AUTH)) {
            me.add(new MRAuthIntercept());
        }
    }

    @Override
    public void onStart() {
        if (AppConst.getProp().getBoolean(AppConst.NEED_AUTH)) {
            MRManager mrManager = MRManager.me();
            mrManager.addLoader(new JFinalResourceLoader());
            mrManager.setPermit(new JsonUserPermit("userMRmap.json"));
            mrManager.load();
        }
    }
}
