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
        if (metaField.dbType().isText()) {
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
}
