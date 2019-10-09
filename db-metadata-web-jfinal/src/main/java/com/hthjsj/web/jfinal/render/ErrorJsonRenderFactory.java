package com.hthjsj.web.jfinal.render;

import com.jfinal.kit.Ret;
import com.jfinal.render.Render;
import com.jfinal.render.RenderFactory;

/**
 * <p> Class title: </p>
 * <p> @Describe: </p>
 * <p> @Date : 2019/10/9 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
public class ErrorJsonRenderFactory extends RenderFactory {
    @Override
    public Render getErrorRender(int errorCode, String view) {
        if (errorCode == 404) {
            return getJsonRender(Ret.fail("code", "404").set("msg", "Not found!"));
        }
        if (errorCode == 500) {
            return getJsonRender(Ret.fail("code", "500").set("msg", "Internal Error"));
        }
        return getJsonRender("999", "Render Error");
    }

    @Override
    public Render getErrorRender(int errorCode) {
        if (errorCode == 404) {
            return getJsonRender(Ret.fail("code", "404").set("msg", "Not found!"));
        }
        if (errorCode == 500) {
            return getJsonRender(Ret.fail("code", "500").set("msg", "Internal Error"));
        }
        return getJsonRender("999", "Render Error");
    }
}
