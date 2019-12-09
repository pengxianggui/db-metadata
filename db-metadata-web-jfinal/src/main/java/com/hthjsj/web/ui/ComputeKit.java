package com.hthjsj.web.ui;

import com.hthjsj.analysis.component.ComponentType;
import com.hthjsj.analysis.meta.IMetaField;
import com.hthjsj.analysis.meta.MetaFieldConfigParse;
import com.hthjsj.web.component.attr.AttributeBuilder;
import com.hthjsj.web.component.attr.RulesBuilder;
import com.hthjsj.web.upload.UploadKit;
import com.jfinal.kit.Kv;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ComputeKit {

    public static Kv recommendFieldConfig(IMetaField metaField) {
        /**
         * 分析元字段
         * 拼装instanceFieldConfig
         *
         * FormFieldFactory.createFormField 负责创建动作
         * TODO 等待完善初级智能分配规则,根据元字段属性 指定一些控件类型
         */
        AttributeBuilder.AttributeSteps builder = AttributeBuilder.newBuilder();
        //set default componentName is "TextBox"
        builder.componentName(ComponentType.TEXTBOX);
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

        //数值
        if (metaField.dbType().isNumber()) {
            builder.componentName(ComponentType.NUMBERBOX);
        }

        //Json
        if (metaField.dbType().isJson()) {
            builder.componentName(ComponentType.JSONBOX);
            builder.showOverflowTooltip(true);
        }
        log.debug("auto compute config : {}", builder.render().toJson());

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

        if (metaFieldConfigParse.isMultiple()) {
            builder.multiple(true);
        }

        if (metaFieldConfigParse.isRequired()) {
            builder.setConf("rules", new RulesBuilder().required(metaField).buildRules(metaField.fieldCode()));
        }

        //上传框
        if (metaField.fieldCode().contains("file")) {
            builder.componentName(ComponentType.FILEBOX);
            builder.actionUrl(UploadKit.uploadUrl(metaField.objectCode(), metaField.fieldCode()));
            builder.autoUpload(true);
        }

        builder.defaultVal(metaFieldConfigParse.defaultVal());
        return builder.render();
    }
}