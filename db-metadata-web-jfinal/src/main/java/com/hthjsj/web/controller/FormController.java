package com.hthjsj.web.controller;

import com.hthjsj.analysis.meta.DbMetaService;
import com.hthjsj.analysis.meta.MetaObject;
import com.hthjsj.web.ServiceManager;
import com.hthjsj.web.component.ViewFactory;
import com.hthjsj.web.component.form.FormView;
import com.hthjsj.web.query.FormDataBuilder;
import com.hthjsj.web.query.QueryHelper;
import com.jfinal.aop.Aop;
import com.jfinal.kit.Kv;
import com.jfinal.kit.Ret;

/**
 * <p> @Date : 2019/10/16 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
public class FormController extends FrontRestController {

    @Override
    public void index() {
    }

    @Override
    public void toAdd() {
        QueryHelper queryHelper = new QueryHelper(this);
        String objectCode = queryHelper.getObjectCode();

        DbMetaService dbMetaService = Aop.get(DbMetaService.class);

        MetaObject metaObject = (MetaObject) dbMetaService.findByCode(objectCode);

        FormView formView = ViewFactory.createFormView(metaObject);

        renderJson(Ret.ok("data", formView.toKv()));
    }

    @Override
    public void doAdd() {

        QueryHelper queryHelper = new QueryHelper(this);
        String objectCode = queryHelper.getObjectCode();

        MetaObject metaObject = (MetaObject) ServiceManager.dbMetaService().findByCode(objectCode);

        Kv metadata = FormDataBuilder.build(getRequest().getParameterMap(), metaObject, null);
        System.out.println(metadata.toJson());
        renderJson(Ret.ok());
    }
}
