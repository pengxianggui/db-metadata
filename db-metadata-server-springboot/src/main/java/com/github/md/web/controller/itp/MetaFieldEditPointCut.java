package com.github.md.web.controller.itp;

import com.github.md.analysis.component.ComponentType;
import com.github.md.analysis.meta.IMetaObject;
import com.github.md.analysis.meta.MetaData;
import com.github.md.analysis.meta.aop.AopInvocation;
import com.github.md.analysis.meta.aop.UpdatePointCut;
import com.github.md.web.ServiceManager;
import com.github.md.web.component.ComponentException;
import com.github.md.web.ui.MetaFieldViewAdapter;
import com.github.md.web.ui.MetaObjectViewAdapter;
import com.github.md.web.ui.UIManager;
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
public class MetaFieldEditPointCut implements UpdatePointCut {


    /**
     * 此处更新的是meta_field的记录,但是需要级联计算的是,该条记录中指向的[元对象,元子段]
     *
     * @param invocation
     */
    @Override
    public boolean updateAfter(AopInvocation invocation) {
        if (invocation.isPreOperateStatus()) {
            log.info("MetaFieldEditPointCut.updateAfter run");
            MetaData formData = invocation.getFormData();
            //获取表单数据中的
            String objectCode = formData.getStr("object_code");
            String fieldCode = formData.getStr("field_code");

            IMetaObject metaObject = ServiceManager.metaService().findByCode(objectCode);

            log.info("Update {} - field {},Biz data Primary key:{}", metaObject.code(), fieldCode, formData.getPks(metaObject.primaryKey()));
            // TODO 无法作用到自定义instanceCode上的实例配置;
            // TODO 元字段更新后，需要让用户去决定是否需要更新UI配置，否则容易覆盖用户自己的UI配置
            List<ComponentType> existTypes = ServiceManager.componentService().loadTypesByObjectCode(metaObject.code());
            for (ComponentType type : existTypes) {
                MetaObjectViewAdapter metaObjectViewAdapter = UIManager.getView(metaObject, type);
                MetaFieldViewAdapter metaFieldViewAdapter = metaObjectViewAdapter.getFieldAdapter(fieldCode);
                if (metaFieldViewAdapter == null) {
                    throw new ComponentException("元字段[%s > %s]在容器[%s]下无UI配置，请确认", objectCode, fieldCode, type.getCode());
                }
                UIManager.update(metaFieldViewAdapter, metaObjectViewAdapter.getComponent().componentType());
            }
        }
        return true;
    }
}
