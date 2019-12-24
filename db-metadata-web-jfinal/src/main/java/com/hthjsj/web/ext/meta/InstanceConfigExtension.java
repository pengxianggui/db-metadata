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
 * 组件实例配置的扩展
 * <p> @Date : 2019/12/18 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
@Slf4j
public class InstanceConfigExtension implements ConfigExtension<IMetaField, AttributeBuilder.FatAttributeBuilder, ComponentType> {

    private ConfigExtension<IMetaField, AttributeBuilder.FatAttributeBuilder, ComponentType> textRecommend = (metaField, builder, containerType) -> {
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

    private ConfigExtension<IMetaField, AttributeBuilder.FatAttributeBuilder, ComponentType> dateRecommend = (metaField, builder, containerType) -> {
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

    private ConfigExtension<IMetaField, AttributeBuilder.FatAttributeBuilder, ComponentType> numberRecommend = (metaField, builder, containerType) -> {
        //数值
        if (metaField.dbType().isNumber()) {
            builder.componentName(ComponentType.NUMBERBOX);
        }
    };

    private ConfigExtension<IMetaField, AttributeBuilder.FatAttributeBuilder, ComponentType> jsonRecommend = (metaField, builder, containerType) -> {
        //Json
        if (metaField.dbType().isJson()) {
            builder.componentName(ComponentType.JSONBOX);
            builder.showOverflowTooltip(true);
        }
    };

    private ConfigExtension<IMetaField, AttributeBuilder.FatAttributeBuilder, ComponentType> optionsRecommend = (metaField, builder, containerType) -> {
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

    private ConfigExtension<IMetaField, AttributeBuilder.FatAttributeBuilder, ComponentType> commonRecommend = (metaField, builder, containerType) -> {
        MetaFieldConfigParse metaFieldConfigParse = metaField.configParser();
        if (metaFieldConfigParse.isMultiple()) {
            builder.multiple(true);
        }
        builder.defaultVal(metaFieldConfigParse.defaultVal());
    };

    private ConfigExtension<IMetaField, AttributeBuilder.FatAttributeBuilder, ComponentType> uploadRecommend = (metaField, builder, containerType) -> {

        //上传框
        if (metaField.fieldCode().contains("file")) {
            builder.componentName(ComponentType.FILEBOX);
            builder.actionUrl(UploadKit.uploadUrl(metaField.objectCode(), metaField.fieldCode()));
            builder.autoUpload(true);
        }
    };

    private ConfigExtension<IMetaField, AttributeBuilder.FatAttributeBuilder, ComponentType> validateRecommend = (metaField, builder, containerType) -> {
        MetaFieldConfigParse metaFieldConfigParse = metaField.configParser();
        if (metaFieldConfigParse.isRequired()) {
            builder.setConf("rules", new RulesBuilder().required(metaField).buildRules(metaField.fieldCode()));
        }
    };

    @Override
    public void config(IMetaField metaField, AttributeBuilder.FatAttributeBuilder config, ComponentType containerType) {
        textRecommend.config(metaField, config, containerType);
        dateRecommend.config(metaField, config, containerType);
        numberRecommend.config(metaField, config, containerType);
        jsonRecommend.config(metaField, config, containerType);
        optionsRecommend.config(metaField, config, containerType);
        commonRecommend.config(metaField, config, containerType);
        uploadRecommend.config(metaField, config, containerType);
        validateRecommend.config(metaField, config, containerType);
    }
}
