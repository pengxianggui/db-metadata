package com.hthjsj.web.ui;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.hthjsj.analysis.component.Component;
import com.hthjsj.analysis.component.ComponentType;
import com.hthjsj.analysis.meta.FieldConfigWrapper;
import com.hthjsj.analysis.meta.IMetaField;
import com.hthjsj.analysis.meta.IMetaObject;
import com.hthjsj.web.ServiceManager;
import com.hthjsj.web.Utils;
import com.hthjsj.web.component.ViewFactory;
import com.hthjsj.web.component.attr.AttributeBuilder;
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
public class SmartAssemble {

    /**
     * 使用系统默认ComponentConfig 信息构建容器View组件
     *
     * @param metaObject
     * @param componentType
     *
     * @return
     */
    public static MetaObjectViewAdapter analysisObject(IMetaObject metaObject, ComponentType componentType) {

        /**
         * 分析元对象
         * 适配选择Component,进行构建IViewAdapter
         */
        Component containerComponent = ViewFactory.createEmptyViewComponent(componentType.getCode());
        Kv globalAllConfig = ServiceManager.componentService().loadComponentsFlatMap();
        Kv containerConfig = Utils.getKv(globalAllConfig, componentType.getCode());
        List<MetaFieldViewAdapter> fields = analysisFields(metaObject.fields(), globalAllConfig);
        return new MetaObjectViewAdapter(metaObject, containerComponent, containerConfig, fields);
    }

    private static List<MetaFieldViewAdapter> analysisFields(Collection<IMetaField> fields, Kv globalConfig) {

        List<MetaFieldViewAdapter> metaFields = Lists.newArrayList();

        // 读取globalConfig中的配置
        // WARN recommendComponent 中会根据各种规则,动态配置config,如与globalConfig中有冲突配置,使用覆盖策略;
        for (IMetaField field : fields) {
            Kv recommendConfig = recommendConfig(field);
            Kv fieldConfig = JSON.parseObject(globalConfig.getStr(recommendConfig.getStr("component_name")), Kv.class);
            recommendConfig.forEach((k, v) -> fieldConfig.merge(k, v, (oldValue, newValue) -> newValue));
            metaFields.add(new MetaFieldViewAdapter(field, FormFieldFactory.createFormFieldDefault(field, fieldConfig)));
        }
        return metaFields;
    }

    private static Kv recommendConfig(IMetaField metaField) {
        /**
         * 分析元字段
         * 拼装instanceFieldConfig
         *
         * FormFieldFactory.createFormField 负责创建动作
         * TODO 等待完善初级智能分配规则,根据元字段属性 指定一些控件类型
         */
        AttributeBuilder.AttributeSteps builder = AttributeBuilder.newBuilder();
        //set default componentName is "TextBox"
        builder.componentName(ComponentType.TEXTBOX.getCode());
        if (metaField.dbType().isText()) {
            if (metaField.dbTypeLength() == 1L) {
                builder.componentName(ComponentType.BOOLBOX.getCode());
            } else {
                builder.maxlength(metaField.dbTypeLength().intValue());
            }
            if (metaField.dbTypeLength() > 255L) {
                builder.componentName(ComponentType.TEXTAREABOX.getCode());
                builder.resizeable("none");
                builder.showOverflowTooltip(true);
            }
        }
        if (metaField.dbType().isDate()) {
            if (metaField.dbType().isDateTime()) {
                builder.componentName(ComponentType.DATETIMEBOX.getCode());
            }
            if (metaField.dbType().isTime()) {
                builder.componentName(ComponentType.TIMEBOX.getCode());
            }
            if (metaField.dbType().isDateOnly()) {
                builder.componentName(ComponentType.DATEBOX.getCode());
            }
        }
        if (metaField.dbType().isNumber()) {
            builder.componentName(ComponentType.NUMBERBOX.getCode());
        }
        if (metaField.dbType().isJson()) {
            builder.componentName(ComponentType.JSONBOX.getCode());
            builder.showOverflowTooltip(true);
        }
        log.debug("auto compute config : {}", builder.render().toJson());
        FieldConfigWrapper fieldConfigWrapper = new FieldConfigWrapper(metaField.config());
        return builder.render();
    }
}
