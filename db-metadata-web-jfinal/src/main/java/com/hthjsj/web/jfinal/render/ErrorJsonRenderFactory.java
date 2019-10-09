package com.hthjsj.web.jfinal.render;

import com.jfinal.kit.Ret;
import com.jfinal.render.Render;
import com.jfinal.render.RenderFactory;

/**
 * <p> @Date : 2019/10/9 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
public class ErrorJsonRenderFactory extends RenderFactory {
    @Override
    public Render getErrorRender(int errorCode, String view) {
        return _getRender(errorCode);
    }

    @Override
    public Render getErrorRender(int errorCode) {
        return _getRender(errorCode);
    }


    private Render _getRender(int errorCode) {
        if (errorCode == 404) {
            return getJsonRender(Ret.fail("code", "404").set("msg", "Not found!"));
        }
        if (errorCode == 500) {
            return getJsonRender(Ret.fail("code", "500").set("msg", "Internal Error"));
        }
        return getJsonRender("999", "Render Error");
    }
}
