package com.hthjsj.web.ui;

import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import com.hthjsj.analysis.component.Component;
import com.hthjsj.analysis.component.ComponentType;
import com.hthjsj.analysis.meta.IMetaField;
import com.hthjsj.analysis.meta.IMetaObject;
import com.hthjsj.web.ServiceManager;
import com.hthjsj.web.WebException;
import com.hthjsj.web.component.ViewFactory;
import com.hthjsj.web.component.attr.AttributeBuilder;
import com.hthjsj.web.component.form.FormFieldFactory;
import com.hthjsj.web.kit.UtilKit;
import com.jfinal.kit.Kv;
import com.jfinal.kit.Okv;
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
public class SmartAssembleFactory implements MetaViewAdapterFactory {

    private static final MetaViewAdapterFactory me = new SmartAssembleFactory();

    public static MetaViewAdapterFactory me() {
        return me;
    }

    private Kv recommendObjectConfig(IMetaObject metaObject, ComponentType componentType, Kv globalComponentConfig) {
        AttributeBuilder.FatAttributeBuilder builder = new AttributeBuilder.FatAttributeBuilder();
        builder.componentName(componentType);
        switch (componentType) {
            case TABLEVIEW:
                builder.dataUrl("/table/list/" + metaObject.code());
                builder.deleteUrl("/table/delete/" + metaObject.code());
                break;
            case SEARCHVIEW:
                if (metaObject.configParser().isTreeStructure()) {
                    builder.dataUrl("/table/tree/" + metaObject.code());
                }
                break;
            case TABLETREEVIEW:
                builder.dataUrl("/table/tree/" + metaObject.code());
                builder.deleteUrl("/table/delete/" + metaObject.code());
                break;
        }
        return UtilKit.mergeUseNew(globalComponentConfig, builder.render());
    }

    private Kv recommendFieldConfig(IMetaField metaField, ComponentType componentType) {
        return ComputeKit.recommendFieldConfig(metaField, componentType);
    }

    /**
     * 1. 推测控件(metafield)
     * 2. 推测样式配置(metafield,ComponentType)
     *
     * @param fields
     * @param globalComponentAllConfig 所有组件的全局配置
     *
     * @return
     */
    private List<MetaFieldViewAdapter> analysisFields(Collection<IMetaField> fields, Kv globalComponentAllConfig, ComponentType componentType) {

        List<MetaFieldViewAdapter> metaFields = Lists.newArrayList();

        // 读取globalConfig中的配置
        // WARN recommendComponent 中会根据各种规则,动态配置config,如与globalConfig中有冲突配置,使用覆盖策略;
        for (IMetaField field : fields) {
            Kv recommendConfig = recommendFieldConfig(field, componentType);
            Kv globalComponentConfig = UtilKit.getKv(globalComponentAllConfig, recommendConfig.getStr("component_name"));
            Kv fieldInstanceConfig = UtilKit.mergeUseNew(globalComponentConfig, recommendConfig);
            Component fieldComponent = FormFieldFactory.createFormFieldDefault(field, fieldInstanceConfig);
            metaFields.add(new MetaFieldViewAdapter(field, fieldComponent, globalComponentConfig, fieldInstanceConfig));
        }
        return metaFields;
    }

    /**
     * 使用系统默认ComponentConfig 信息构建容器View组件
     *
     * @param metaObject
     * @param componentType
     *
     * @return
     */
    @Override
    public MetaObjectViewAdapter createMetaObjectViewAdapter(IMetaObject metaObject, ComponentType componentType) {

        /**
         * 分析元对象
         * 适配选择Component,进行构建IViewAdapter
         */
        Component containerComponent = ViewFactory.createEmptyViewComponent(componentType.getCode());
        // 全部全局配置
        Kv globalComponentAllConfig = ServiceManager.componentService().loadComponentsFlatMap();
        //某一组件全局配置
        Kv globalComponentConfig = UtilKit.getKv(globalComponentAllConfig, componentType.getCode());


        // 因配置为自动计算,所以传入组件全局配置map
        List<MetaFieldViewAdapter> fields = analysisFields(metaObject.fields(), globalComponentAllConfig, componentType);
        Okv fieldsMap = Okv.create();
        fields.forEach(m -> {
            fieldsMap.set(m.getMetaField().fieldCode(), m.getFieldInstanceConfig().toJson());
        });


        String instanceCode = Joiner.on(".").join(metaObject.code(), componentType.getCode());
        Kv objectConfig = Kv.by(metaObject.code(), recommendObjectConfig(metaObject, componentType, globalComponentConfig));

        ComponentInstanceConfig componentInstanceConfig = ComponentInstanceConfig.Load(objectConfig,
                                                                                       fieldsMap,
                                                                                       metaObject.code(),
                                                                                       instanceCode,
                                                                                       "自动计算配置",
                                                                                       containerComponent.componentType());

        return new MetaObjectViewAdapter(metaObject, containerComponent, globalComponentConfig, componentInstanceConfig, fields);
    }

    @Override
    public MetaObjectViewAdapter createMetaObjectViewAdapter(String instanceCode) {
        //TODO
        throw new WebException("This is not to be implemented!");
    }

    @Override
    public boolean reCompute(IMetaField metaField, Component fieldComponent, Kv toBeUpdatedFieldInstanceConfig) {
        throw new WebException("This is not intended to be implemented!");
    }

    @Override
    public boolean reCompute(MetaFieldViewAdapter metaFieldViewAdapter, Kv toBeUpdatedFieldInstanceConfig) {
        throw new WebException("This is not intended to be implemented!");
    }
}
