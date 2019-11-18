package com.hthjsj.web.ui;

import com.google.common.collect.Lists;
import com.hthjsj.analysis.component.Component;
import com.hthjsj.analysis.component.ComponentType;
import com.hthjsj.analysis.meta.IMetaField;
import com.hthjsj.analysis.meta.MetaObject;
import com.hthjsj.web.ServiceManager;
import com.hthjsj.web.UtilKit;
import com.hthjsj.web.component.ViewFactory;
import com.hthjsj.web.component.form.FormFieldFactory;
import com.jfinal.kit.Kv;
import lombok.extern.slf4j.Slf4j;

import java.util.Collection;
import java.util.List;

/**
 * <p> @Date : 2019/11/4 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
@Slf4j
public class ViewAssembleFactory {

    /**
     * 使用系统默认ComponentConfig 信息构建容器View组件
     *
     * @param metaObject
     * @param componentType
     *
     * @return
     */
    public static MetaObjectViewAdapter fetchObjectAdapter(MetaObject metaObject, ComponentType componentType) {

        /**
         * 校验元对象
         * 查找 元对象+component_instance 配置
         *
         * 构建 MetaFieldViewAdapter ( 需要 MetaFields + component_instance)
         *
         * 装配 MetaObjectViewAdapter
         *
         */
        Component containerComponent = ViewFactory.createEmptyViewComponent(componentType.getCode());
        //全部全局配置
        Kv globalAllConfig = ServiceManager.componentService().loadComponentsFlatMap();
        //某一组件全局配置
        Kv globalComponentConfig = UtilKit.getKv(globalAllConfig, componentType.getCode());
        //实例配置
        Kv instanceConfig = ServiceManager.componentService().loadObjectConfigFlat(componentType.getCode(), metaObject.code());

        List<MetaFieldViewAdapter> fields = fetchFieldsAdapter(metaObject.fields(), instanceConfig);

        MetaObjectViewAdapter objectViewAdapter = new MetaObjectViewAdapter(metaObject, containerComponent, globalComponentConfig, fields);

        return objectViewAdapter;
    }

    private static List<MetaFieldViewAdapter> fetchFieldsAdapter(Collection<IMetaField> fields, Kv instanceAllConfig) {

        List<MetaFieldViewAdapter> metaFields = Lists.newArrayList();

        for (IMetaField field : fields) {
            Kv instanceConfig = UtilKit.getKv(instanceAllConfig, field.fieldCode());
            Component fieldComponent = FormFieldFactory.createFormFieldDefault(field, instanceConfig);
            metaFields.add(new MetaFieldViewAdapter(field, fieldComponent));
        }

        return metaFields;
    }
}
