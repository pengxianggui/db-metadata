package com.hthjsj.web.controller;

import com.alibaba.fastjson.JSON;
import com.hthjsj.analysis.meta.DbMetaService;
import com.hthjsj.analysis.meta.IMetaObject;
import com.hthjsj.web.component.form.DropDown;
import com.hthjsj.web.component.form.FormView;
import com.jfinal.aop.Aop;
import com.jfinal.kit.Ret;

/**
 * <p> Class title: </p>
 * <p> @Describe: </p>
 * <p> @Date : 2019/9/24 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
public class MetaController extends FrontRestController {

    /**
     * <pre>
     *
     * param :
     *      objectCode
     *
     * </pre>
     */
    @Override
    public Ret index() {
        String metaObjectCode = getPara(0, getPara("objectCode"));
        DbMetaService dbMetaService = Aop.get(DbMetaService.class);
        IMetaObject metaObject = dbMetaService.findByCode(metaObjectCode);
        renderJson(Ret.ok("data", metaObject));
        return null;
    }

    @Override
    public Ret toAdd() {
        FormView formView = new FormView();
        formView.getFields().add(new DropDown());
        renderJson(JSON.toJSONString(formView.renderMeta()));



        return null;
    }

    @Override
    public Ret doAdd() {




        return null;
    }
}
