package com.github.md.web.ui.meta.form;

import com.github.md.analysis.component.ComponentType;
import com.github.md.analysis.meta.ConfigExtension;
import com.github.md.analysis.meta.IMetaField;
import com.github.md.web.component.attr.AttributeBuilder;

/**
 * 域组件配置扩展。
 *
 * @author pengxg
 * @date 2022/4/10 8:25 上午
 */
public abstract class FieldComponentConfigExtension implements ConfigExtension<IMetaField, AttributeBuilder.FatAttributeBuilder, ComponentType> {
    @Override
    public void config(IMetaField metaField, AttributeBuilder.FatAttributeBuilder builder, ComponentType type) {

    }
}
