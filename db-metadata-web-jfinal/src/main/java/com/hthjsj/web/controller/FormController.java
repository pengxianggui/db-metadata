package com.hthjsj.web.controller;

import com.hthjsj.analysis.meta.IMetaObject;
import com.hthjsj.analysis.meta.MetaData;
import com.hthjsj.analysis.meta.MetaObjectConfigParse;
import com.hthjsj.analysis.meta.aop.AddPointCut;
import com.hthjsj.analysis.meta.aop.AopInvocation;
import com.hthjsj.analysis.meta.aop.UpdatePointCut;
import com.hthjsj.web.component.ViewFactory;
import com.hthjsj.web.component.form.FormView;
import com.hthjsj.web.query.FormDataFactory;
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

    /**
     * <pre>
     * 1. 允许带初始值构建form
     * 2. 允许传参,干预form的渲染
     *
     * 描述:
     *  TODO 控制字段只读,url带参 存在前端伪造的风险, 带参这部分逻辑等module模块敲定后,可以绑定在"功能"中
     * </pre>
     */
    @Override
    public void toAdd() {
        QueryHelper queryHelper = new QueryHelper(this);
        String objectCode = queryHelper.getObjectCode();

        IMetaObject metaObject = metaService().findByCode(objectCode);

        FormView formView = ViewFactory.formView(metaObject).action("/form/doAdd").addForm();

        renderJson(Ret.ok("data", formView.toKv()));
    }

    @Override
    public void doAdd() {

        QueryHelper queryHelper = new QueryHelper(this);
        String objectCode = queryHelper.getObjectCode();

        IMetaObject metaObject = metaService().findByCode(objectCode);

        MetaData metadata = FormDataFactory.buildFormData(getRequest().getParameterMap(), metaObject, true);

        MetaObjectConfigParse metaObjectConfigParse = metaObject.configParser();
        AddPointCut pointCut = metaObjectConfigParse.addPointCut();
        AopInvocation invocation = new AopInvocation(metaObject, metadata, getKv());

        boolean status = Db.tx(new IAtom() {

            @Override
            public boolean run() throws SQLException {
                boolean s = false;
                try {
                    pointCut.addBefore(invocation);
                    s = metaService().saveData(invocation.getMetaObject(), invocation.getFormData());
                    pointCut.addAfter(s, invocation);
                } catch (Exception e) {
                    log.error("保存异常\n元对象:{},错误信息:{}", metaObject.code(), e.getMessage());
                    log.error(e.getMessage(), e);
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

        IMetaObject metaObject = metaService().findByCode(objectCode);
        Object[] dataIds = queryHelper.getPks(metaObject.primaryKey(), "id");

        FormView formView = ViewFactory.formView(metaObject).action("/form/doUpdate").updateForm();

        Record d = metaService().findDataByIds(metaObject, dataIds);

        FormDataFactory.buildUpdateFormData(metaObject, d);

        renderJson(Ret.ok("data", formView.toKv().set("record", d)));
    }

    @Override
    public void doUpdate() {
        QueryHelper queryHelper = new QueryHelper(this);
        String objectCode = queryHelper.getObjectCode();

        IMetaObject metaObject = metaService().findByCode(objectCode);
        MetaData metadata = FormDataFactory.buildFormData(getRequest().getParameterMap(), metaObject, false);

        MetaObjectConfigParse metaObjectConfigParse = metaObject.configParser();
        UpdatePointCut pointCut = metaObjectConfigParse.updatePointCut();
        AopInvocation invocation = new AopInvocation(metaObject, metadata, getKv());

        boolean status = Db.tx(new IAtom() {

            @Override
            public boolean run() throws SQLException {
                boolean s = false;
                try {
                    pointCut.updateBefore(invocation);
                    s = metaService().updateData(invocation.getMetaObject(), invocation.getFormData());
                    pointCut.updateAfter(s, invocation);
                } catch (Exception e) {
                    log.error("更新异常\n元对象:{},错误信息:{}", metaObject.code(), e.getMessage());
                    log.error(e.getMessage(), e);
                    s = false;
                }
                return s;
            }
        });

        renderJson(status ? Ret.ok() : Ret.fail());
    }

    @Override
    public void detail() {
        QueryHelper queryHelper = new QueryHelper(this);
        String objectCode = queryHelper.getObjectCode();

        IMetaObject metaObject = metaService().findByCode(objectCode);
        Object[] dataIds = queryHelper.getPks(metaObject.primaryKey(), "id");

        FormView formView = ViewFactory.formView(metaObject).viewForm();

        Record d = metaService().findDataByIds(metaObject, dataIds);

        FormDataFactory.buildUpdateFormData(metaObject, d);

        renderJson(Ret.ok("data", formView.toKv().set("record", d)));
    }
}
