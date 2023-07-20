package com.github.md.web.component.render;

import com.github.md.analysis.component.ComponentRender;
import com.github.md.analysis.db.MetaDataTypeConvert;
import com.github.md.analysis.meta.IMetaField;
import com.github.md.web.component.attr.AttributeBuilder;
import com.github.md.web.component.form.FormField;
import com.github.md.web.kit.UtilKit;
import com.github.md.analysis.kit.Kv;

/**
 * 元子段 与 元子控件的渲染
 * <p> @Date : 2019/11/27 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
public class MetaFormFieldRender<C extends FormField> implements ComponentRender<C> {

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
        UtilKit.deepMerge(component.getMeta(), defaultValTypeConvert(fieldInstanceConfig), true);

        // 扩展
        AttributeBuilder.FatAttributeBuilder builder = new AttributeBuilder.FatAttributeBuilder(component.getMeta());
        RenderKit.renderExtend(metaField, builder, component);

        return component.getMeta();
    }

    /**
     * 默认值类型转换。例如, 数据库中设置的默认值可能是下面这样的值:
     * <pre>
     *     CURRENT_TIMESTAMP、now()、等，统一在MetaDataTypeConvert中进行转换处理
     * </pre>
     *
     * @param fieldInstanceConfig
     * @return
     */
    private Kv defaultValTypeConvert(Kv fieldInstanceConfig) {
        if (fieldInstanceConfig.containsKey("default_value")) {
            fieldInstanceConfig.set("default_value", MetaDataTypeConvert.convert(metaField, fieldInstanceConfig.get("default_value")));
        }
        return fieldInstanceConfig;
    }
}
