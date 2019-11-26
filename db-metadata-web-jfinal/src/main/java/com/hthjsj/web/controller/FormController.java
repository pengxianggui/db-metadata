package com.hthjsj.web.controller;

import com.hthjsj.analysis.meta.MetaData;
import com.hthjsj.analysis.meta.MetaObject;
import com.hthjsj.analysis.meta.MetaObjectConfigParse;
import com.hthjsj.analysis.meta.aop.AddPointCut;
import com.hthjsj.analysis.meta.aop.AopInvocation;
import com.hthjsj.analysis.meta.aop.UpdatePointCut;
import com.hthjsj.web.component.ViewFactory;
import com.hthjsj.web.component.form.FormView;
import com.hthjsj.web.query.FormDataBuilder;
import com.hthjsj.web.query.QueryHelper;
import com.jfinal.kit.Ret;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.IAtom;
import com.jfinal.plugin.activerecord.Record;
import lombok.extern.slf4j.Slf4j;

import java.sql.SQLException;

/**
 * <p> @Date : 2019/10/16 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
@Slf4j
public class FormController extends FrontRestController {

    @Override
    public void index() {
    }

    @Override
    public void toAdd() {
        QueryHelper queryHelper = new QueryHelper(this);
        String objectCode = queryHelper.getObjectCode();

        MetaObject metaObject = (MetaObject) metaService().findByCode(objectCode);

        FormView formView = ViewFactory.createFormView(metaObject).action("/form/doAdd");

        renderJson(Ret.ok("data", formView.toKv()));
    }

    @Override
    public void doAdd() {

        QueryHelper queryHelper = new QueryHelper(this);
        String objectCode = queryHelper.getObjectCode();

        MetaObject metaObject = (MetaObject) metaService().findByCode(objectCode);

        MetaData metadata = FormDataBuilder.buildFormData(getRequest().getParameterMap(), metaObject, true);

        MetaObjectConfigParse metaObjectConfigParse = new MetaObjectConfigParse(metaObject.config(), metaObject.code());
        AddPointCut pointCut = metaObjectConfigParse.interceptor();
        AopInvocation invocation = new AopInvocation(metaObject, metadata);

        boolean status = Db.tx(new IAtom() {

            @Override
            public boolean run() throws SQLException {
                boolean s = false;
                try {
                    pointCut.addBefore(invocation);
                    s = metaService().saveData(invocation.getMetaObject(), invocation.getMetaData());
                    pointCut.addAfter(s, invocation);
                } catch (Exception e) {
                    log.error("保存异常\n元对象:{},错误信息:{}", metaObject.code(), e.getMessage());
                    s = false;
                }
                return s;
            }
        });

        renderJson(status ? Ret.ok() : Ret.fail());
    }

    @Override
    public void toUpdate() {

        QueryHelper queryHelper = new QueryHelper(this);
        String objectCode = queryHelper.getObjectCode();

        MetaObject metaObject = (MetaObject) metaService().findByCode(objectCode);
        String dataId = getPara(metaObject.primaryKey());

        FormView formView = ViewFactory.createFormView(metaObject).action("/form/doUpdate");

        Record d = metaService().findDataById(metaObject, dataId);

        renderJson(Ret.ok("data", formView.toKv().set("record", d)));
    }

    @Override
    public void doUpdate() {
        QueryHelper queryHelper = new QueryHelper(this);
        String objectCode = queryHelper.getObjectCode();

        MetaObject metaObject = (MetaObject) metaService().findByCode(objectCode);
        MetaData metadata = FormDataBuilder.buildFormData(getRequest().getParameterMap(), metaObject, false);

        MetaObjectConfigParse metaObjectConfigParse = new MetaObjectConfigParse(metaObject.config(), metaObject.code());
        UpdatePointCut pointCut = metaObjectConfigParse.interceptor();
        AopInvocation invocation = new AopInvocation(metaObject, metadata);

        boolean status = Db.tx(new IAtom() {

            @Override
            public boolean run() throws SQLException {
                boolean s = false;
                try {
                    pointCut.updateBefore(invocation);
                    s = metaService().updateData(invocation.getMetaObject(), invocation.getMetaData());
                    pointCut.updateAfter(s, invocation);
                } catch (Exception e) {
                    log.error("更新异常\n元对象:{},错误信息:{}", metaObject.code(), e.getMessage());
                    s = false;
                }
                return s;
            }
        });

        renderJson(status ? Ret.ok() : Ret.fail());
    }
}
