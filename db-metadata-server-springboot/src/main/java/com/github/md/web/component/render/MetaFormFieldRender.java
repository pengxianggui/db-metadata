package com.github.md.web.component.render;

import com.github.md.analysis.component.Component;
import com.github.md.analysis.component.ComponentRender;
import com.github.md.analysis.meta.IMetaField;
import com.github.md.analysis.meta.MetaFieldConfigParse;
import com.github.md.web.component.attr.AttributeBuilder;
import com.github.md.web.component.form.FormView;
import com.github.md.web.kit.UtilKit;
import com.github.md.analysis.kit.Kv;

/**
 * 元子段 与 元子控件的渲染
 * <p> @Date : 2019/11/27 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
public class MetaFormFieldRender<C extends Component> implements ComponentRender<C> {

    IMetaField metaField;

    C component;

    Kv fieldInstanceConfig;

    public MetaFormFieldRender(IMetaField metaField, C component, Kv fieldInstanceConfig) {
        this.metaField = metaField;
        this.component = component;
        this.fieldInstanceConfig = fieldInstanceConfig;
    }

    @Override
    public C component() {
        return component;
    }

    @Override
    public Kv render() {
        //先将实例配置,merge到meta
        //以数据库保存的实例配置为准
        UtilKit.deepMerge(component.getMeta(), fieldInstanceConfig, true);

        //TODO 根据表单和字段状态,计算显示属性 ReadOnly Disabled
        if (component.getContainer() instanceof FormView) {
            FormView formView = (FormView) component.getContainer();
            AttributeBuilder.FatAttributeBuilder builder = AttributeBuilder.newBuilder();
            if (formView.isAdd()) {
//                if (metaField.configParser().isAdd()) {
                    if (MetaFieldConfigParse.READONLY == metaField.configParser().addStatus()) {
                        builder.disabled(true);
                    } else if (MetaFieldConfigParse.HIDDEN == metaField.configParser().addStatus()) {
                        builder.hidden(true);
                    } else if (MetaFieldConfigParse.DISABLE == metaField.configParser().addStatus()) {
                        builder.disabled(true);
                    }
//                }
            }

            if (formView.isUpdate()) {
//                if (metaField.configParser().isUpdate()) {
                    if (MetaFieldConfigParse.READONLY == metaField.configParser().updateStatus()) {
                        builder.disabled(true);
                    } else if (MetaFieldConfigParse.HIDDEN == metaField.configParser().updateStatus()) {
                        builder.hidden(true);
                    } else if (MetaFieldConfigParse.DISABLE == metaField.configParser().updateStatus()) {
                        builder.disabled(true);
                    }
//                }
            }

            if (formView.isView()) {
//                if (metaField.configParser().isView()) {
                    builder.disabled(true);
//                }
            }

            UtilKit.deepMerge(component.getMeta(), builder.render(), true);
        }
        return component.getMeta();
    }
}
