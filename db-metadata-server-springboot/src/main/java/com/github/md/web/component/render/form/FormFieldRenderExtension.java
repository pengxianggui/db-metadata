package com.github.md.web.component.render.form;

import com.github.md.analysis.component.ComponentType;
import com.github.md.analysis.component.ViewContainer;
import com.github.md.analysis.meta.ConfigExtension;
import com.github.md.analysis.meta.IMetaField;
import com.github.md.analysis.meta.MetaFieldConfigParse;
import com.github.md.web.component.attr.AttributeBuilder;
import com.github.md.web.component.form.FormField;
import com.github.md.web.component.form.FormView;

/**
 * 表单项渲染扩展。处理formType不同时的差异
 */
public class FormFieldRenderExtension implements ConfigExtension<IMetaField, AttributeBuilder.FatAttributeBuilder, FormField> {

    @Override
    public void config(IMetaField metaField, AttributeBuilder.FatAttributeBuilder builder, FormField component) {
        ViewContainer container = component.getContainer();
        if (container.componentType() != ComponentType.FORMVIEW) {
            return;
        }

        FormView formView = (FormView) container;
        if (formView.isAdd()) {
            if (MetaFieldConfigParse.READONLY == metaField.configParser().addStatus()) {
                builder.disabled(true);
            } else if (MetaFieldConfigParse.HIDDEN == metaField.configParser().addStatus()) {
                builder.hidden(true);
            } else if (MetaFieldConfigParse.DISABLE == metaField.configParser().addStatus()) {
                builder.disabled(true);
            }
        } else if (formView.isUpdate()) {
            if (MetaFieldConfigParse.READONLY == metaField.configParser().updateStatus()) {
                builder.disabled(true);
            } else if (MetaFieldConfigParse.HIDDEN == metaField.configParser().updateStatus()) {
                builder.hidden(true);
            } else if (MetaFieldConfigParse.DISABLE == metaField.configParser().updateStatus()) {
                builder.disabled(true);
            }
        } else if (formView.isView()) {
            builder.disabled(true);
        }
    }
}
