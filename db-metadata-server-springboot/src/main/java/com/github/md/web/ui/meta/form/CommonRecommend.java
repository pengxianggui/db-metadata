package com.github.md.web.ui.meta.form;

import com.github.md.analysis.component.ComponentType;
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

        builder.componentName(ComponentType.TEXTBOX); // 缺省

        if (type == ComponentType.FORMVIEW) {
            // 此处不能对默认值进行计算，因为builder.defaultVal只是从元字段中推导默认值 到 实例配置中，当用户访问实例配置时，实例配置还会
            // 进行转义为真实值。通常情况下，其实这里计算也没什么问题，但是日期类型很特别，如果数据库默认是CURRENT_TIMESTAMP, 那么就有问题了
            // 此时如果转义，实例配置中的defaultValue就把结果(一个确定的时间)算出来了，显然是不对的，用户每次打开表单默认值都是这个时间，显然不对。
            // 我们希望的是用户打开表单的时候，计算CURRENT_TIMESTAMP的值(详见 FormDataFactory#buildFormData)。因此，这里取消转义。
//            builder.defaultVal(MetaDataTypeConvert.convert(metaField, metaFieldConfigParse.defaultVal()));
            builder.defaultVal(metaFieldConfigParse.defaultVal());

            if (metaFieldConfigParse.isRequired()) {
                builder.setConf("rules", new RulesBuilder().required(metaField).buildRules(metaField.fieldCode()));
            }
        }
    }
}
