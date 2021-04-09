package com.hthjsj.web.controller;

import com.google.common.collect.Lists;
import com.hthjsj.analysis.meta.IMetaObject;
import com.hthjsj.analysis.meta.MetaData;
import com.hthjsj.analysis.meta.MetaObjectConfigParse;
import com.hthjsj.analysis.meta.aop.*;
import com.hthjsj.web.component.ViewFactory;
import com.hthjsj.web.component.form.FormView;
import com.hthjsj.web.jfinal.HttpRequestHolder;
import com.hthjsj.web.jms.EventKit;
import com.hthjsj.web.jms.FormMessage;
import com.hthjsj.web.query.FormDataFactory;
import com.hthjsj.web.query.QueryHelper;
import com.hthjsj.web.ui.OptionsKit;
import com.jfinal.aop.Before;
import com.jfinal.kit.Kv;
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

        //TODO 手工build,方便后面编程式操作表单内元子控件
        Kv disableMetaFields = queryHelper.hasMetaParams(metaObject);
        if (!disableMetaFields.isEmpty()) {
            formView.buildChildren();
            disableMetaFields.forEach((key, value) -> {
                formView.getField(String.valueOf(key)).disabled(true).defaultVal(String.valueOf(value));
            });
        }
        renderJson(Ret.ok("data", formView.toKv()));
    }

    @Override
    public void doAdd() {

        QueryHelper queryHelper = new QueryHelper(this);
        String objectCode = queryHelper.getObjectCode();

        IMetaObject metaObject = metaService().findByCode(objectCode);

        MetaData metadata = FormDataFactory.buildFormData(getRequest().getParameterMap(), metaObject, true);

        MetaObjectConfigParse metaObjectConfigParse = metaObject.configParser();
        AddPointCut[] pointCut = metaObjectConfigParse.addPointCut();
        AopInvocation invocation = new AopInvocation(metaObject, metadata, getKv(), this);

        boolean status = Db.tx(new IAtom() {

            @Override
            public boolean run() throws SQLException {
                boolean s = false;
                try {
                    PointCutChain.addBefore(pointCut, invocation);
                    s = metaService().saveData(invocation.getMetaObject(), invocation.getFormData());
                    invocation.setPreOperateStatus(s);
                    PointCutChain.addAfter(pointCut, invocation);
                } catch (Exception e) {
                    log.error("保存异常\n元对象:{},错误信息:{}", metaObject.code(), e.getMessage());
                    log.error(e.getMessage(), e);
                    throw e;
                }
                return s;
            }
        });

        EventKit.post(FormMessage.AddMessage(invocation));

        renderJson(status ? Ret.ok() : Ret.fail());
    }

    @Override
    public void toUpdate() {

        QueryHelper queryHelper = new QueryHelper(this);
        String objectCode = queryHelper.getObjectCode();

        IMetaObject metaObject = metaService().findByCode(objectCode);

        Object[] dataIds = queryHelper.getPks(metaObject);

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
        UpdatePointCut[] pointCut = metaObjectConfigParse.updatePointCut();
        AopInvocation invocation = new AopInvocation(metaObject, metadata, getKv(), this);

        boolean status = Db.tx(new IAtom() {

            @Override
            public boolean run() throws SQLException {
                boolean s = false;
                try {
                    PointCutChain.updateBefore(pointCut, invocation);
                    s = metaService().updateData(invocation.getMetaObject(), invocation.getFormData());
                    invocation.setPreOperateStatus(s);
                    PointCutChain.updateAfter(pointCut, invocation);
                } catch (Exception e) {
                    log.error("更新异常\n元对象:{},错误信息:{}", metaObject.code(), e.getMessage());
                    log.error(e.getMessage(), e);
                    invocation.getRet().setFail();
                    s = false;
                }
                return s;
            }
        });
        if (status) {
            EventKit.post(FormMessage.UpdateMessage(invocation));
        }
        renderJson(invocation.getRet());
    }

    @Before(HttpRequestHolder.class)
    @Override
    public void detail() {
        QueryHelper queryHelper = new QueryHelper(this);
        String objectCode = queryHelper.getObjectCode();

        IMetaObject metaObject = metaService().findByCode(objectCode);

        FormView formView = ViewFactory.formView(metaObject).viewForm();

        ViewPointCut[] viewPointCuts = metaObject.configParser().viewPointCut();
        FormQueryInvocation invocation = new FormQueryInvocation(metaObject, this);

        boolean status = Db.tx(() -> {
            boolean s = true;
            try {
                PointCutChain.viewBefore(viewPointCuts, invocation);
                Object[] dataIds = queryHelper.getPks(metaObject);
                Record d = metaService().findDataByIds(metaObject, dataIds);
//                    FormDataFactory.buildUpdateFormData(metaObject, d);
                /**
                 * escape field value;
                 * 1. 是否需要转义的规则;
                 */
                if (!queryHelper.list().raw()) {
                    d = OptionsKit.trans(metaObject.fields(), Lists.newArrayList(d)).get(0);
                }
                invocation.setData(d);
                PointCutChain.viewAfter(viewPointCuts, invocation);
            } catch (Exception e) {
                log.error("获取记录详情错误\n元对象:{}, 错误信息:{}", metaObject.code(), e.getMessage());
                invocation.getRet().setFail();
                s = false;
            }
            return s;
        });

//        renderJson(Ret.ok("data", formView.toKv().set("record", d)));
        renderJson(invocation.getRet().setOk().set("data", formView.toKv().set("record", invocation.getData())));
    }
}
