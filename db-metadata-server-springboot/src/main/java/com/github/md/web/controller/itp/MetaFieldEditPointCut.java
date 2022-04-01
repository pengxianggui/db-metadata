package com.github.md.web.controller.itp;

import cn.com.asoco.util.AssertUtil;
import com.github.md.analysis.component.ComponentType;
import com.github.md.analysis.kit.Kv;
import com.github.md.analysis.meta.IMetaObject;
import com.github.md.analysis.meta.MetaData;
import com.github.md.analysis.meta.aop.*;
import com.github.md.web.ServiceManager;
import com.github.md.web.WebException;
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
        if (invocation.isPreOperateStatus()) {
            log.info("MetaFieldEditPointCut.updateAfter run");
            MetaData formData = invocation.getFormData();
            Kv httpParams = invocation.getHttpParams();
            //获取表单数据中的
            String objectCode = httpParams.getStr("object_code");
            String fieldCode = httpParams.getStr("field_code");

            IMetaObject metaObject = ServiceManager.metaService().findByCode(objectCode);

            log.info("Update {} - field {},Biz data Primary key:{}", metaObject.code(), fieldCode, formData.getPks(metaObject.primaryKey()));


            // TODO 元字段更新后，最好让用户去决定是否需要更新UI配置，否则容易覆盖用户自己的UI配置
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
            AssertUtil.isTrue(record != null, new WebException("数据不存在，请尝试刷新页面"));

            ServiceManager.componentService().deleteFieldConfig(record.getStr("object_code"), record.getStr("field_code"));
        }

        return true;
    }
}
