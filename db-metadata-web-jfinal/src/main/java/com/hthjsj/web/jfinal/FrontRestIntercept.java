package com.hthjsj.web.jfinal;

import com.hthjsj.web.controller.FrontRestController;
import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;
import com.jfinal.core.Controller;
import com.jfinal.kit.Ret;

/**
 * <p> Class title: </p>
 * <p> @Describe: </p>
 * <p> @Date : 2019/10/15 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
public class FrontRestIntercept implements Interceptor {

    @Override
    public void intercept(Invocation inv) {
        Controller ctrl = inv.getController();
        if (FrontRestController.class.isAssignableFrom(ctrl.getClass())) {
            inv.invoke();
            Object returnVal = inv.getReturnValue();
            if (returnVal != null && returnVal instanceof Ret) {
                ctrl.renderJson(returnVal);
            }
        }
    }
}
