package com.hthjsj.web.controller;

import com.hthjsj.analysis.meta.MetaData;
import com.hthjsj.analysis.meta.MetaObject;
import com.hthjsj.web.ServiceManager;
import com.hthjsj.web.component.ViewFactory;
import com.hthjsj.web.component.form.FormView;
import com.hthjsj.web.query.DataBuilder;
import com.hthjsj.web.query.QueryHelper;
import com.jfinal.kit.Ret;
import com.jfinal.plugin.activerecord.Record;

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

        MetaObject metaObject = (MetaObject) ServiceManager.metaService().findByCode(objectCode);

        FormView formView = ViewFactory.createFormView(metaObject);

        renderJson(Ret.ok("data", formView.toKv()));
    }

    @Override
    public void doAdd() {

        QueryHelper queryHelper = new QueryHelper(this);
        String objectCode = queryHelper.getObjectCode();

        MetaObject metaObject = (MetaObject) ServiceManager.metaService().findByCode(objectCode);

        MetaData metadata = DataBuilder.buildFormData(getRequest().getParameterMap(), metaObject, true);

        boolean status = ServiceManager.metaService().saveData(metaObject, metadata);

        renderJson(status ? Ret.ok() : Ret.fail());
    }

    @Override
    public void toUpdate() {

        QueryHelper queryHelper = new QueryHelper(this);
        String objectCode = queryHelper.getObjectCode();

        MetaObject metaObject = (MetaObject) ServiceManager.metaService().findByCode(objectCode);
        String dataId = getPara(metaObject.primaryKey());

        FormView formView = ViewFactory.createFormView(metaObject);

        Record d = ServiceManager.metaService().findData(metaObject, dataId);

        renderJson(Ret.ok("data", formView.toKv()).set("record", d));
    }

    @Override
    public void doUpdate() {
        QueryHelper queryHelper = new QueryHelper(this);
        String objectCode = queryHelper.getObjectCode();

        MetaObject metaObject = (MetaObject) ServiceManager.metaService().findByCode(objectCode);
        String dataId = getPara(metaObject.primaryKey());


        MetaData metadata = DataBuilder.buildFormData(getRequest().getParameterMap(), metaObject, false);
        metadata.set(metaObject.primaryKey(), dataId);
        boolean status = ServiceManager.metaService().updateData(metaObject, metadata);

        renderJson(status ? Ret.ok() : Ret.fail());
    }
}
