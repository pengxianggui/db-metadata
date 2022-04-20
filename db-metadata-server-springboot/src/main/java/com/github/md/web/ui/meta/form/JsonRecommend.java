package com.github.md.web.ui.meta.form;

import com.github.md.analysis.component.ComponentType;
import com.github.md.analysis.meta.IMetaField;
import com.github.md.web.component.attr.AttributeBuilder;

/**
 * @author pengxg
 * @date 2022/4/10 8:33 上午
 */
public class JsonRecommend extends FieldComponentConfigExtension {
    @Override
    public void config(IMetaField metaField, AttributeBuilder.FatAttributeBuilder builder, ComponentType type) {
        if (!metaField.dbType().isJson()) {
            return;
        }

        builder.componentName(ComponentType.JSONBOX);
    }
}
