package com.hthjsj.web.ext.meta;

import com.hthjsj.analysis.component.ComponentType;
import com.hthjsj.analysis.meta.ConfigExtension;
import com.hthjsj.analysis.meta.IMetaField;
import com.hthjsj.analysis.meta.MetaFieldConfigParse;
import com.hthjsj.web.component.attr.AttributeBuilder;
import com.hthjsj.web.component.attr.RulesBuilder;
import com.hthjsj.web.ui.OptionsKit;
import com.hthjsj.web.upload.UploadKit;
import lombok.extern.slf4j.Slf4j;

/**
 * <p> @Date : 2019/12/18 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
@Slf4j
public class InstanceConfigExtension implements ConfigExtension<IMetaField, AttributeBuilder.FatAttributeBuilder> {

    private ConfigExtension<IMetaField, AttributeBuilder.FatAttributeBuilder> textRecommend = (metaField, builder) -> {
        if (metaField.dbType().isText()) {
            if (metaField.dbTypeLength() == 1L) {
                builder.componentName(ComponentType.BOOLBOX);
            } else {
                builder.maxlength(metaField.dbTypeLength().intValue());
            }
            if (metaField.dbTypeLength() > 255L) {
                builder.componentName(ComponentType.TEXTAREABOX);
                builder.resizeable("none");
                builder.showOverflowTooltip(true);
            }
        }
    };

    private ConfigExtension<IMetaField, AttributeBuilder.FatAttributeBuilder> dateRecommend = (metaField, builder) -> {
        //日期
        if (metaField.dbType().isDate()) {
            if (metaField.dbType().isDateTime()) {
                builder.componentName(ComponentType.DATETIMEBOX);
            }
            if (metaField.dbType().isTime()) {
                builder.componentName(ComponentType.TIMEBOX);
            }
            if (metaField.dbType().isDateOnly()) {
                builder.componentName(ComponentType.DATEBOX);
            }
        }
    };

    private ConfigExtension<IMetaField, AttributeBuilder.FatAttributeBuilder> numberRecommend = (metaField, builder) -> {
        //数值
        if (metaField.dbType().isNumber()) {
            builder.componentName(ComponentType.NUMBERBOX);
        }
    };

    private ConfigExtension<IMetaField, AttributeBuilder.FatAttributeBuilder> jsonRecommend = (metaField, builder) -> {
        //Json
        if (metaField.dbType().isJson()) {
            builder.componentName(ComponentType.JSONBOX);
            builder.showOverflowTooltip(true);
        }
    };

    private ConfigExtension<IMetaField, AttributeBuilder.FatAttributeBuilder> optionsRecommend = (metaField, builder) -> {
        log.debug("analysis metafield config");
        MetaFieldConfigParse metaFieldConfigParse = metaField.configParser();
        if (metaFieldConfigParse.hasTranslation()) {
            if (metaFieldConfigParse.isRange()) {
                builder.options(OptionsKit.transKeyValue(metaFieldConfigParse.range()));
            }
            if (metaFieldConfigParse.isSql()) {
                log.info("metaFieldConfigParse sql:{}", metaFieldConfigParse.scopeSql());
                builder.dataUrl(OptionsKit.buildUrl(metaField.objectCode(), metaField.fieldCode()));
            }
            if (metaFieldConfigParse.isOptions()) {
                builder.dataUrl(OptionsKit.buildUrl(metaField.objectCode(), metaField.fieldCode()));
            }
            builder.componentName(ComponentType.DROPDOWN);
        }
    };

    private ConfigExtension<IMetaField, AttributeBuilder.FatAttributeBuilder> commonRecommend = (metaField, builder) -> {
        MetaFieldConfigParse metaFieldConfigParse = metaField.configParser();
        if (metaFieldConfigParse.isMultiple()) {
            builder.multiple(true);
        }
        builder.defaultVal(metaFieldConfigParse.defaultVal());
    };

    private ConfigExtension<IMetaField, AttributeBuilder.FatAttributeBuilder> uploadRecommend = (metaField, builder) -> {

        //上传框
        if (metaField.fieldCode().contains("file")) {
            builder.componentName(ComponentType.FILEBOX);
            builder.actionUrl(UploadKit.uploadUrl(metaField.objectCode(), metaField.fieldCode()));
            builder.autoUpload(true);
        }
    };

    private ConfigExtension<IMetaField, AttributeBuilder.FatAttributeBuilder> validateRecommend = (metaField, builder) -> {
        MetaFieldConfigParse metaFieldConfigParse = metaField.configParser();
        if (metaFieldConfigParse.isRequired()) {
            builder.setConf("rules", new RulesBuilder().required(metaField).buildRules(metaField.fieldCode()));
        }
    };

    @Override
    public void config(IMetaField metaField, AttributeBuilder.FatAttributeBuilder config) {
        textRecommend.config(metaField, config);
        dateRecommend.config(metaField, config);
        numberRecommend.config(metaField, config);
        jsonRecommend.config(metaField, config);
        optionsRecommend.config(metaField, config);
        commonRecommend.config(metaField, config);
        uploadRecommend.config(metaField, config);
        validateRecommend.config(metaField, config);
    }
}
