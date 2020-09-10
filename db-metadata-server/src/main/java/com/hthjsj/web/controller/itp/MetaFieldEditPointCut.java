package com.hthjsj.web.controller.itp;

import com.hthjsj.analysis.component.ComponentType;
import com.hthjsj.analysis.meta.IMetaObject;
import com.hthjsj.analysis.meta.MetaData;
import com.hthjsj.analysis.meta.aop.AopInvocation;
import com.hthjsj.analysis.meta.aop.UpdatePointCut;
import com.hthjsj.web.ServiceManager;
import com.hthjsj.web.ui.MetaFieldViewAdapter;
import com.hthjsj.web.ui.MetaObjectViewAdapter;
import com.hthjsj.web.ui.UIManager;
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
            //TODO 无法作用到自定义instanceCode上的实例配置;
            List<ComponentType> existTypes = ServiceManager.componentService().loadTypesByObjectCode(metaObject.code());
            for (ComponentType type : existTypes) {
                MetaObjectViewAdapter metaObjectViewAdapter = UIManager.getView(metaObject, type);
                MetaFieldViewAdapter metaFieldViewAdapter = metaObjectViewAdapter.getFieldAdapter(fieldCode);
                UIManager.update(metaFieldViewAdapter, metaObjectViewAdapter.getComponent().componentType());
            }
        }
        return true;
    }
}
