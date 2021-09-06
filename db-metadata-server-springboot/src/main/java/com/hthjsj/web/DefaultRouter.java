package com.hthjsj.web;

import com.hthjsj.AnalysisConfig;
import com.jfinal.config.Routes;
import com.jfinal.core.Controller;

/**
 * @author pengxg
 * @date 2021/1/28 10:31 上午
 */
public abstract class DefaultRouter extends Routes {
    private String routerPrefix;

    public DefaultRouter() {
        this.routerPrefix = processRouterPrefix(AnalysisConfig.me().getProp().get(AppConst.ROUTER_PREFIX, ""));
    }

    @Override
    public Routes add(String controllerKey, Class<? extends Controller> controllerClass) {
        return super.add(routerPrefix + controllerKey, controllerClass);
    }

    @Override
    public abstract void config();

    private String processRouterPrefix(String routerPrefix) {
        routerPrefix = routerPrefix.trim();

        if (routerPrefix.length() == 0) {
            return routerPrefix;
        }

        if (!routerPrefix.startsWith("/")) {
            routerPrefix = "/" + routerPrefix;
        }

        return routerPrefix;
    }
}
