package com.hthjsj.web.controller;

import com.hthjsj.web.component.ComponentService;
import com.hthjsj.web.query.QueryHelper;
import com.jfinal.aop.Aop;
import com.jfinal.kit.Ret;

public class ComponentController extends FrontRestController {

    public void load() {
        /**
         * object Code
         * component Type
         *
         */
        /**
         * return
         * {
         *  config:{
         *
         *  }
         * }
         */
    }

    @Override
    public void toAdd() {
        QueryHelper queryHelper = new QueryHelper(this);
        String objectCode = queryHelper.getObjectCode();
        String compCode = queryHelper.getComponentCode();
        renderJson(Ret.ok("data", Aop.get(ComponentService.class).loadObjectConfig(compCode, objectCode)));
    }

    /**
     * set Component config
     */
    public void config() {
        /**
         * object code
         * component Type config string;
         */
    }

    /**
     * The component config default value;
     */
    public void tmpl() {
        /**
         * component Type
         */
        /**
         * return
         * {
         *    field 1:"",
         *    field 2:"",
         *    field 3:"",
         *    field 4:"",
         *    field 5:"",
         *    base :""
         * }
         */
    }
}
