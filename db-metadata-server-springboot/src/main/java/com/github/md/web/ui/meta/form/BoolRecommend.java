package com.github.md.web.ui.meta.form;

import com.github.md.analysis.component.ComponentType;
import com.github.md.analysis.meta.IMetaField;
import com.github.md.web.component.attr.AttributeBuilder;

/**
 * @author pengxg
 * @date 2022/4/10 8:31 上午
 */
public class BoolRecommend extends FieldComponentConfigExtension {
    @Override
    public void config(IMetaField metaField, AttributeBuilder.FatAttributeBuilder builder, ComponentType type) {
        if (metaField.dbType().isBoolean()) {
            switch (type) {
                case FORMVIEW:
                    builder.componentName(ComponentType.BOOLBOX);
                    break;
                case SEARCHVIEW:
                    builder.componentName(ComponentType.DROPDOWN);
                    builder.dataUrl("/dict?name=yn");
                    break;
            }
        }
    }
}
