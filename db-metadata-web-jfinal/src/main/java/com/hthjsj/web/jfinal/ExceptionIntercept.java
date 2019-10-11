package com.hthjsj.web.jfinal;

import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;
import com.jfinal.core.Controller;
import com.jfinal.core.JFinal;
import com.jfinal.kit.Ret;
import lombok.extern.slf4j.Slf4j;

/**
 * <p> Class title: </p>
 * <p> @Describe: </p>
 * <p> @Date : 2019/10/10 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
@Slf4j
public class ExceptionIntercept implements Interceptor {

    @Override
    public void intercept(Invocation inv) {
        Controller controller = inv.getController();
        try {
            inv.invoke();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            Ret ret = Ret.fail();
            if (JFinal.me().getConstants().getDevMode()) {
                ret.set("request_uri", inv.getController().getRequest().getRequestURI());
                //                ret.set("ex_msg", Throwables.getStackTraceAsString(e));
            }
            controller.renderJson(ret.set("code", 500).set("msg", e.getMessage()));
        }
    }
}
