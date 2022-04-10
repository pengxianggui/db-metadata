package com.github.md.web.ui.meta.form;

import com.github.md.analysis.component.ComponentType;
import com.github.md.analysis.db.MetaDataTypeConvert;
import com.github.md.analysis.meta.IMetaField;
import com.github.md.analysis.meta.MetaFieldConfigParse;
import com.github.md.web.component.attr.AttributeBuilder;
import com.github.md.web.component.attr.RulesBuilder;

/**
 * @author pengxg
 * @date 2022/4/10 8:35 上午
 */
public class CommonRecommend extends FieldComponentConfigExtension {
    @Override
    public void config(IMetaField metaField, AttributeBuilder.FatAttributeBuilder builder, ComponentType type) {
        MetaFieldConfigParse metaFieldConfigParse = metaField.configParser();
        if (metaFieldConfigParse.isMultiple()) {
            builder.multiple(true);
        }

        if (type == ComponentType.FORMVIEW) {
            builder.defaultVal(MetaDataTypeConvert.convert(metaField, metaFieldConfigParse.defaultVal()));

            if (metaFieldConfigParse.isRequired()) {
                builder.setConf("rules", new RulesBuilder().required(metaField).buildRules(metaField.fieldCode()));
            }
        }
    }
}
