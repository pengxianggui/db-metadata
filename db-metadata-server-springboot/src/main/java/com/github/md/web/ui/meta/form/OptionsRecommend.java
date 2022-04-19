package com.github.md.web.ui.meta.form;

import com.github.md.analysis.component.ComponentType;
import com.github.md.analysis.meta.IMetaField;
import com.github.md.analysis.meta.MetaFieldConfigParse;
import com.github.md.web.component.attr.AttributeBuilder;
import com.github.md.web.ui.OptionsKit;

/**
 * @author pengxg
 * @date 2022/4/10 8:34 上午
 */
public class OptionsRecommend extends FieldComponentConfigExtension {
    @Override
    public void config(IMetaField metaField, AttributeBuilder.FatAttributeBuilder builder, ComponentType type) {
        MetaFieldConfigParse metaFieldConfigParse = metaField.configParser();
        if (metaFieldConfigParse.hasTranslation()) {
            builder.dataUrl(OptionsKit.buildUrl(metaField.objectCode(), metaField.fieldCode()));
            builder.componentName(ComponentType.DROPDOWN);
            builder.showSymbolOption(false); // 先禁用。因为前端下拉组件没有很好的插槽位，以便呈现操作符选项列表
        }
    }
}
