package com.github.md.web.ui.meta.form;

import com.github.md.analysis.component.ComponentType;
import com.github.md.analysis.meta.IMetaField;
import com.github.md.web.component.attr.AttributeBuilder;

/**
 * @author pengxg
 * @date 2022/4/10 8:27 上午
 */
public class TextRecommend extends FieldComponentConfigExtension {
    @Override
    public void config(IMetaField metaField, AttributeBuilder.FatAttributeBuilder builder, ComponentType type) {
        if (!metaField.dbType().isText()) {
            return;
        }

        switch (type) {
            case FORMVIEW:
                builderForFormView(metaField, builder);
                break;
            case SEARCHVIEW:
                builderForSearchView(metaField, builder);
                break;
        }

    }

    private void builderForSearchView(IMetaField metaField, AttributeBuilder.FatAttributeBuilder builder) {
        builder.componentName(ComponentType.TEXTBOX);

        // 内置的默认设为显示操作符选项，非内置的一般由用户使用, 一般无需选择模糊匹配模式
        builder.showSymbolOption(metaField.buildIn());
    }

    private void builderForFormView(IMetaField metaField, AttributeBuilder.FatAttributeBuilder builder) {
        if (metaField.dbType().isLongText()) {
            builder.componentName(ComponentType.RICHTEXTBOX);
        } else {
            if (metaField.dbType().is1Text(metaField.dbTypeLength().intValue())) { // 一个字符长度的text类型，视为下拉
                builder.componentName(ComponentType.DROPDOWN);
            }
            if (metaField.dbTypeLength() > 255L) {
                builder.componentName(ComponentType.TEXTAREABOX);
                builder.resizeable("none");
            }
        }

        builder.maxlength(metaField.dbTypeLength().intValue());
    }
}
