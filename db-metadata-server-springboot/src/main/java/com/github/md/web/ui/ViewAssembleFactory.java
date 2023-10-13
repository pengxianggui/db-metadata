package com.github.md.web.ui;

import com.github.md.web.ServiceManager;
import com.github.md.web.component.ViewFactory;
import com.github.md.web.component.form.FormFieldFactory;
import com.google.common.collect.Lists;
import com.github.md.analysis.component.Component;
import com.github.md.analysis.component.ComponentType;
import com.github.md.analysis.meta.IMetaField;
import com.github.md.analysis.meta.IMetaObject;
import com.github.md.web.kit.UtilKit;
import com.github.md.analysis.kit.Kv;
import lombok.extern.slf4j.Slf4j;

import java.util.Collection;
import java.util.List;

/**
 * 可视装配工厂: 从已有实例配置中构建。可视体现在该实例配置已经可见了
 * <p> @Date : 2019/11/4 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
@Slf4j
public class ViewAssembleFactory implements MetaViewAdapterFactory {

    public static final MetaViewAdapterFactory me = new ViewAssembleFactory();

    public static MetaViewAdapterFactory me() {
        return me;
    }

    /**
     * 计算元子段列 实例配置,组装成MetaFieldViewAdapter对象
     *
     * @param fields
     * @param componentInstanceConfig  完整的实例配置,元对象级+字段级
     * @param globalComponentAllConfig 所有组件的全局配置
     * @return
     */
    private List<MetaFieldViewAdapter> fetchFieldsAdapter(Collection<IMetaField> fields, ComponentInstanceConfig componentInstanceConfig, Kv globalComponentAllConfig) {

        List<MetaFieldViewAdapter> metaFields = Lists.newArrayList();

        for (IMetaField field : fields) {
            Kv fieldInstanceConfig = UtilKit.getKv(componentInstanceConfig.getFieldsMap(), field.fieldCode());
            //TODO 配置为空时,该字段则不存在实例配置
            if (!fieldInstanceConfig.isEmpty()) {
                Component fieldComponent = FormFieldFactory.createFormField(field, fieldInstanceConfig);
                Kv globalComponentConfig = UtilKit.getKv(globalComponentAllConfig, fieldComponent.componentType().getCode());
                //携带 为Field 分配的Component 在全局的配置 + 字段实例的配置
                metaFields.add(new MetaFieldViewAdapter(field, fieldComponent, globalComponentConfig, fieldInstanceConfig));
            }
        }

        return metaFields;
    }

    /**
     * 使用系统默认ComponentConfig 信息构建容器View组件
     *
     * @param metaObject
     * @param componentType
     * @return
     */
    @Override
    public MetaObjectViewAdapter createMetaObjectViewAdapter(IMetaObject metaObject, ComponentType componentType) {

        /**
         * 校验元对象
         * 查找 元对象+component_instance 配置
         *
         * 构建 MetaFieldViewAdapter ( 需要 MetaFields + component_instance)
         *
         * 装配 MetaObjectViewAdapter
         *
         */

        //全部全局配置
        Kv globalComponentAllConfig = ServiceManager.componentService().loadComponentsFlatMap();
        //某一组件全局配置
        Kv globalComponentConfig = UtilKit.getKv(globalComponentAllConfig, componentType.getCode());
        //完整的实例配置,元对象级+字段级
        ComponentInstanceConfig componentInstanceConfig = ServiceManager.componentService().loadObjectConfig(componentType.getCode(), metaObject.code());

        Component containerComponent = ViewFactory.createViewComponent(metaObject, componentType, componentInstanceConfig);

        List<MetaFieldViewAdapter> fields = fetchFieldsAdapter(metaObject.fields(), componentInstanceConfig, globalComponentAllConfig);

        return new MetaObjectViewAdapter(metaObject, containerComponent, globalComponentConfig, componentInstanceConfig, fields);
    }

    @Override
    public MetaObjectViewAdapter createMetaObjectViewAdapter(String ic) {
        //全部全局配置
        Kv globalComponentAllConfig = ServiceManager.componentService().loadComponentsFlatMap();
        //完整的实例配置,元对象级+字段级
        ComponentInstanceConfig componentInstanceConfig = ServiceManager.componentService().loadObjectConfig(ic);
        //某一组件全局配置
        Kv globalComponentConfig = UtilKit.getKv(globalComponentAllConfig, componentInstanceConfig.getContainerType().getCode());
        // 构造容器对象
        IMetaObject metaObject = ServiceManager.metaService().findByCode(componentInstanceConfig.getObjectCode());
        Component containerComponent = ViewFactory.createViewComponent(metaObject, componentInstanceConfig.getContainerType(), componentInstanceConfig);
        // 构造域配置Adapter
        List<MetaFieldViewAdapter> fields = fetchFieldsAdapter(metaObject.fields(), componentInstanceConfig, globalComponentAllConfig);
        // 构造容器Adapter
        return new MetaObjectViewAdapter(metaObject, containerComponent, globalComponentConfig, componentInstanceConfig, fields);
    }

    @Override
    public boolean reCompute(IMetaField metaField, Component container, Kv toBeUpdatedFieldInstanceConfig) {
        return ServiceManager.componentService().updateFieldConfig(container.componentType(), metaField, toBeUpdatedFieldInstanceConfig);
    }

    @Override
    public boolean reCompute(MetaFieldViewAdapter metaFieldViewAdapter, Kv toBeUpdatedFieldInstanceConfig) {
        //获取原来配置
        Kv fieldInstanceConfig = metaFieldViewAdapter.getFieldInstanceConfig();
        //merge操作
        Kv mergedResult = Kv.create().set(UtilKit.deepMerge(fieldInstanceConfig, toBeUpdatedFieldInstanceConfig, true));
        return reCompute(metaFieldViewAdapter.getMetaField(), metaFieldViewAdapter.getContainer().getComponent(), mergedResult);
    }
}
