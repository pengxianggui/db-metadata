package com.github.md.web.controller.itp;

import com.github.md.analysis.component.ComponentType;
import com.github.md.analysis.kit.Kv;
import com.github.md.analysis.meta.IMetaObject;
import com.github.md.analysis.meta.MetaData;
import com.github.md.analysis.meta.aop.DeleteInvocation;
import com.github.md.analysis.meta.aop.DeletePointCut;
import com.github.md.analysis.meta.aop.FormInvocation;
import com.github.md.analysis.meta.aop.UpdatePointCut;
import com.github.md.web.ServiceManager;
import com.github.md.web.ex.WebException;
import com.github.md.web.kit.AssertKit;
import com.github.md.web.ui.MetaFieldViewAdapter;
import com.github.md.web.ui.MetaObjectViewAdapter;
import com.github.md.web.ui.UIManager;
import com.jfinal.plugin.activerecord.Record;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * 元子段编辑拦截器,类的调用配置在
 * MetaObject.config -> bizInterceptor 字段中
 * <p> @Date : 2019/12/3 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
@Slf4j
public class MetaFieldEditPointCut implements UpdatePointCut, DeletePointCut {

    /**
     * 此处更新的是meta_field的记录,但是需要级联计算的是,该条记录中指向的[元对象,元子段]
     *
     * @param invocation
     */
    @Override
    public boolean updateAfter(FormInvocation invocation) {
        // TODO 最好让用户去决定是否需要更新UI配置，否则容易覆盖用户自己的UI配置。但是这个不好实现, 因为元字段的编辑也是通过
        //  功能模板来做的，元字段编辑表单也是模板渲染的，很难在更新表单提交按钮旁加一个"是否重新计算UI配置的"勾选框。也许只能让元字段编辑
        //  表单脱离模板单独实现，然后将表单提交和UI更新分两步走，分别调用两个接口去实现。这显然也不太好，不能因为模板实现不了，就彻底不用
        //  模板, 至少dbmeta的开发人员不能这样做，而是应该考虑，如果这个场景是通用的，能否让模板支持，或者让模板扩展支持。
        if (!ServiceManager.getAppProperties().getServer().getInstance().isRecomputeWhenMetaFieldUpdate()) {
            return true;
        }

        if (invocation.isPreOperateStatus()) {
            log.info("MetaFieldEditPointCut.updateAfter run");
            MetaData formData = invocation.getFormData();
            Kv httpParams = invocation.getHttpParams();
            //获取表单数据中的
            String objectCode = httpParams.getStr("object_code");
            String fieldCode = httpParams.getStr("field_code");

            IMetaObject metaObject = ServiceManager.metaService().findByCode(objectCode);

            log.info("Update {} - field {},Biz data Primary key:{}", metaObject.code(), fieldCode, formData.getPks(metaObject.primaryKey()));

            List<ComponentType> existTypes = ServiceManager.componentService().loadTypesByObjectCode(metaObject.code());
            for (ComponentType type : existTypes) {
                MetaObjectViewAdapter metaObjectViewAdapter = UIManager.getView(metaObject, type);
                MetaFieldViewAdapter metaFieldViewAdapter = metaObjectViewAdapter.getFieldAdapter(fieldCode);
                if (metaFieldViewAdapter == null) {
                    log.debug(String.format("元字段[%s > %s]在容器[%s]下无UI配置，请确认", objectCode, fieldCode, type.getCode()));
                    // TODO 无UI配置，则是否应当帮助其自动计算生成
                    continue;
                }
                UIManager.update(metaFieldViewAdapter, metaObjectViewAdapter.getComponent().componentType());
            }
        }
        return true;
    }

    /**
     * 对于元字段的删除，也需要级联删除的还有: 元字段实例配置
     *
     * @param invocation
     * @return
     */
    @Override
    public boolean deleteBefore(DeleteInvocation invocation) {
        IMetaObject metaObject = invocation.getMetaObject();
        Object[] ids = invocation.getIds();

        for (Object id : ids) {
            Record record = ServiceManager.businessService().findDataByIds(metaObject, id);
            AssertKit.isTrue(record != null, new WebException("数据不存在，请尝试刷新页面"));

            ServiceManager.componentService().deleteFieldConfig(record.getStr("object_code"), record.getStr("field_code"));
        }

        return true;
    }
}
