package com.github.md.web.ui.meta.form;

import com.github.md.analysis.component.ComponentType;
import com.github.md.analysis.meta.IMetaField;
import com.github.md.web.component.attr.AttributeBuilder;

/**
 * @author pengxg
 * @date 2022/4/10 8:32 上午
 */
public class DateRecommend extends FieldComponentConfigExtension {
    @Override
    public void config(IMetaField metaField, AttributeBuilder.FatAttributeBuilder builder, ComponentType type) {
        if (metaField.dbType().isDate()) {
            if (metaField.dbType().isDateTime()) {
                builder.componentName(ComponentType.DATETIMEBOX);
            }
            if (metaField.dbType().isTime()) {
                builder.componentName(ComponentType.DATETIMEBOX);
            }
            if (metaField.dbType().isDateOnly()) {
                builder.componentName(ComponentType.DATEBOX);
            }
        }
    }
}
