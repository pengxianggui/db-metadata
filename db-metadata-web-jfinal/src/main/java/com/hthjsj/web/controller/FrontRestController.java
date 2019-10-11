package com.hthjsj.web.controller;

import com.jfinal.core.Controller;
import com.jfinal.kit.Ret;

/**
 * <p> @Date : 2019/10/9 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
public abstract class FrontRestController extends Controller implements FrontRest {

    @Override
    public Ret index() {
        list();
        //        renderJson(faildMsgInfo());
        return null;
    }

    @Override
    public Ret toAdd() {
        renderJson(faildMsgInfo());
        return null;
    }

    @Override
    public Ret doAdd() {
        renderJson(faildMsgInfo());
        return null;
    }

    @Override
    public Ret toUpdate() {
        renderJson(faildMsgInfo());
        return null;
    }

    @Override
    public Ret doUpdate() {
        renderJson(faildMsgInfo());
        return null;
    }

    @Override
    public Ret detail() {
        renderJson(faildMsgInfo());
        return null;
    }

    @Override
    public Ret delete() {
        renderJson(faildMsgInfo());
        return null;
    }

    @Override
    public Ret list() {
        renderJson(Ret.fail("msg", ""));
        return null;
    }

    Ret faildMsgInfo() {
        Ret ret = Ret.fail();
        ret.set("request_uri", getRequest().getRequestURI());
        ret.set("msg", "not implementation!");
        return ret;
    }
}
