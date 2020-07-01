package com.hthjsj.web.ui;

import com.hthjsj.analysis.component.ComponentType;
import com.hthjsj.analysis.meta.ConfigExtension;
import com.hthjsj.analysis.meta.IMetaField;
import com.hthjsj.web.component.attr.AttributeBuilder;
import com.jfinal.kit.Kv;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

/**
 * 元子段纬度的组件配置计算工具
 * 计算某一元子段+某类型组件时的前端配置
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
@Slf4j
public class ComputeKit {

    @Getter
    private static List<ConfigExtension<IMetaField, AttributeBuilder.FatAttributeBuilder, ComponentType>> instanceExtensions;

    public static Kv recommendFieldConfig(IMetaField metaField, ComponentType componentType) {
        /**
         * 分析元字段
         * 拼装instanceFieldConfig
         *
         * FormFieldFactory.createFormField 负责创建动作
         * TODO 等待完善初级智能分配规则,根据元字段属性 指定一些控件类型
         */
        AttributeBuilder.FatAttributeBuilder builder = AttributeBuilder.newBuilder();
        //set default componentName is "TextBox"
        builder.componentName(ComponentType.TEXTBOX);
        builder.name(metaField.fieldCode());
        builder.label(metaField.cn());
        log.debug("auto compute config : {}", builder.render().toJson());
        if (instanceExtensions != null) {
            for (ConfigExtension<IMetaField, AttributeBuilder.FatAttributeBuilder, ComponentType> configExtension : instanceExtensions) {
                configExtension.config(metaField, builder, componentType);
            }
        }

        return builder.render();
    }

    public static void addInstanceExtension(ConfigExtension extension) {
        if (instanceExtensions == null) {
            instanceExtensions = new ArrayList<>();
        }
        instanceExtensions.add(extension);
    }
}