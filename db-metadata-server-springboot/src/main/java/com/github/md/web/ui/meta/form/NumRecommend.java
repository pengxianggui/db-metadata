package com.github.md.web.ui.meta.form;

import com.github.md.analysis.component.ComponentType;
import com.github.md.analysis.meta.IMetaField;
import com.github.md.web.component.attr.AttributeBuilder;

/**
 * @author pengxg
 * @date 2022/4/10 8:33 上午
 */
public class NumRecommend extends FieldComponentConfigExtension {
    @Override
    public void config(IMetaField metaField, AttributeBuilder.FatAttributeBuilder builder, ComponentType type) {
        if (!metaField.dbType().isNumber()) {
            return;
        }

        switch (type) {
            case FORMVIEW:
                builder.componentName(ComponentType.NUMBERBOX);
                break;
            case SEARCHVIEW:
                builder.componentName(ComponentType.NUMBERBOX);
                builder.showSymbolOption(true); // 默认设为显示操作符选项
                break;
        }
    }
}
