package com.hthjsj.web.component.render;

import com.hthjsj.analysis.component.Component;
import com.hthjsj.analysis.component.ComponentRender;
import com.hthjsj.analysis.meta.IMetaField;
import com.hthjsj.analysis.meta.MetaFieldConfigParse;
import com.hthjsj.web.component.attr.AttributeBuilder;
import com.hthjsj.web.component.form.FormView;
import com.hthjsj.web.kit.UtilKit;
import com.jfinal.kit.Kv;

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
                if (metaField.configParser().isAdd()) {
                    if (MetaFieldConfigParse.READONLY == metaField.configParser().addStatus()) {
                        builder.disabled(true);
                    } else if (MetaFieldConfigParse.DISABLE == metaField.configParser().addStatus()) {
                        builder.disabled(true);
                    } else if (MetaFieldConfigParse.HIDDEN == metaField.configParser().addStatus()) {
                        // TODO 实现隐藏功能
                        throw new RuntimeException("MetaFieldConfigParse.HIDDEN == metaField.configParser().addStatus() not finished");
                    }
                }
            }

            if (formView.isUpdate()) {
                if (metaField.configParser().isUpdate()) {
                    if (MetaFieldConfigParse.READONLY == metaField.configParser().updateStatus()) {
                        builder.disabled(true);
                    } else if (MetaFieldConfigParse.DISABLE == metaField.configParser().updateStatus()) {
                        builder.disabled(true);
                    } else if (MetaFieldConfigParse.HIDDEN == metaField.configParser().updateStatus()) {
                        // TODO 实现隐藏
                        throw new RuntimeException("MetaFieldConfigParse.HIDDEN == metaField.configParser().updateStatus() not finished");
                    }
                }
            }

            if (formView.isView()) {
                if (metaField.configParser().isView()) {
                    if (MetaFieldConfigParse.READONLY == metaField.configParser().viewStatus()) {
                        builder.disabled(true);
                    } else if (MetaFieldConfigParse.DISABLE == metaField.configParser().viewStatus()) {
                        builder.disabled(true);
                    } else if (MetaFieldConfigParse.HIDDEN == metaField.configParser().viewStatus()) {
                        // TODO 实现隐藏
                        throw new RuntimeException("MetaFieldConfigParse.HIDDEN == metaField.configParser().viewStatus() not finished");
                    }
                }
            }

            UtilKit.deepMerge(component.getMeta(), builder.render(), true);
        }
        return component.getMeta();
    }
}
