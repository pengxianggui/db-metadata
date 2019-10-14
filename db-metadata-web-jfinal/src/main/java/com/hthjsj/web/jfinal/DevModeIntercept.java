package com.hthjsj.web.jfinal;

import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;
import com.jfinal.core.JFinal;

/**
 * <p> Class title: </p>
 * <p> @Describe: </p>
 * <p> @Date : 2019/10/14 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
public class DevModeIntercept implements Interceptor {

    @Override
    public void intercept(Invocation inv) {
        if (JFinal.me().getConstants().getDevMode() && inv.getController().getRequest().getRequestURI().endsWith("api")) {
            return;
        }
        inv.invoke();
    }
}
