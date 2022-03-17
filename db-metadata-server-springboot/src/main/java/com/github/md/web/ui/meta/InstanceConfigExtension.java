package com.github.md.web.ui.meta;

import com.github.md.web.component.attr.AttributeBuilder;
import com.github.md.web.component.attr.RulesBuilder;
import com.github.md.web.ui.OptionsKit;
import com.github.md.web.upload.UploadKit;
import com.github.md.analysis.component.ComponentType;
import com.github.md.analysis.meta.ConfigExtension;
import com.github.md.analysis.meta.IMetaField;
import com.github.md.analysis.meta.MetaFieldConfigParse;
import lombok.extern.slf4j.Slf4j;

/**
 * TODO 除了定义根据 字段类型的推荐， 还应当支持根据 组件类型。来达到更精确的配置推荐
 * 组件实例配置的扩展,ComponentType 传的是容器type
 * <p> @Date : 2019/12/18 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
@Slf4j
public class InstanceConfigExtension implements ConfigExtension<IMetaField, AttributeBuilder.FatAttributeBuilder, ComponentType> {

    private final ConfigExtension<IMetaField, AttributeBuilder.FatAttributeBuilder, ComponentType> textRecommend = (metaField, builder, containerType) -> {
        if (metaField.dbType().isText()) {
            if (metaField.dbType().is1Text(metaField.dbTypeLength().intValue())) { // 一个字符长度的text类型，视为下拉
                builder.componentName(ComponentType.DROPDOWN);
            }
            if (metaField.dbTypeLength() > 255L) {
                builder.componentName(ComponentType.TEXTAREABOX);
                builder.resizeable("none");
                builder.showOverflowTooltip(true);
            }
            builder.maxlength(metaField.dbTypeLength().intValue());
        }
    };

    private final ConfigExtension<IMetaField, AttributeBuilder.FatAttributeBuilder, ComponentType> boolRecommend = ((metaField, builder, containerType) -> {
        if (metaField.dbType().isBoolean()) {
            switch (containerType) {
                case FORMVIEW:
                    builder.componentName(ComponentType.BOOLBOX);
                    break;
                case SEARCHVIEW:
                    builder.componentName(ComponentType.DROPDOWN);
                    break;
            }
        }
    });

    private final ConfigExtension<IMetaField, AttributeBuilder.FatAttributeBuilder, ComponentType> dateRecommend = (metaField, builder, containerType) -> {
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

    private final ConfigExtension<IMetaField, AttributeBuilder.FatAttributeBuilder, ComponentType> numberRecommend = (metaField, builder, containerType) -> {
        //数值
        if (metaField.dbType().isNumber()) {
            builder.componentName(ComponentType.NUMBERBOX);
        }
    };

    private final ConfigExtension<IMetaField, AttributeBuilder.FatAttributeBuilder, ComponentType> jsonRecommend = (metaField, builder, containerType) -> {
        //Json
        if (metaField.dbType().isJson()) {
            builder.componentName(ComponentType.JSONBOX);
            builder.showOverflowTooltip(true);
        }
    };

    private final ConfigExtension<IMetaField, AttributeBuilder.FatAttributeBuilder, ComponentType> optionsRecommend = (metaField, builder, containerType) -> {
        log.debug("analysis metafield config");
        MetaFieldConfigParse metaFieldConfigParse = metaField.configParser();
        if (metaFieldConfigParse.hasTranslation()) {
            builder.dataUrl(OptionsKit.buildUrl(metaField.objectCode(), metaField.fieldCode()));
            builder.componentName(ComponentType.DROPDOWN);
        }
    };

    private final ConfigExtension<IMetaField, AttributeBuilder.FatAttributeBuilder, ComponentType> commonRecommend = (metaField, builder, containerType) -> {
        MetaFieldConfigParse metaFieldConfigParse = metaField.configParser();
        if (metaFieldConfigParse.isMultiple()) {
            builder.multiple(true);
        }
        
        if (containerType == ComponentType.FORMVIEW) {
            builder.defaultVal(metaFieldConfigParse.defaultVal());
        }
    };

    private final ConfigExtension<IMetaField, AttributeBuilder.FatAttributeBuilder, ComponentType> uploadRecommend = (metaField, builder, containerType) -> {

        //上传框
        if (metaField.fieldCode().contains("file") || metaField.configParser().isFile()) {
            builder.componentName(ComponentType.FILEBOX);
            builder.actionUrl(UploadKit.uploadUrl(metaField.objectCode(), metaField.fieldCode()));
            builder.autoUpload(true);
        }
    };

    private final ConfigExtension<IMetaField, AttributeBuilder.FatAttributeBuilder, ComponentType> validateRecommend = (metaField, builder, containerType) -> {
        MetaFieldConfigParse metaFieldConfigParse = metaField.configParser();
        if (metaFieldConfigParse.isRequired()) {
            builder.setConf("rules", new RulesBuilder().required(metaField).buildRules(metaField.fieldCode()));
        }
    };

    @Override
    public void config(IMetaField metaField, AttributeBuilder.FatAttributeBuilder config, ComponentType containerType) {
        textRecommend.config(metaField, config, containerType);
        boolRecommend.config(metaField, config, containerType);
        dateRecommend.config(metaField, config, containerType);
        numberRecommend.config(metaField, config, containerType);
        jsonRecommend.config(metaField, config, containerType);
        optionsRecommend.config(metaField, config, containerType);
        commonRecommend.config(metaField, config, containerType);
        uploadRecommend.config(metaField, config, containerType);
        validateRecommend.config(metaField, config, containerType);
    }
}
