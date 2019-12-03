package com.hthjsj.web.ui;

import com.google.common.collect.Lists;
import com.hthjsj.analysis.component.Component;
import com.hthjsj.analysis.component.ComponentType;
import com.hthjsj.analysis.meta.IMetaField;
import com.hthjsj.analysis.meta.IMetaObject;
import com.hthjsj.analysis.meta.MetaFieldConfigParse;
import com.hthjsj.web.ServiceManager;
import com.hthjsj.web.UtilKit;
import com.hthjsj.web.component.ViewFactory;
import com.hthjsj.web.component.attr.AttributeBuilder;
import com.hthjsj.web.component.attr.RulesBuilder;
import com.hthjsj.web.component.form.FormFieldFactory;
import com.jfinal.kit.Kv;
import lombok.extern.slf4j.Slf4j;

import java.util.Collection;
import java.util.List;

/**
 * <p> @Date : 2019/11/4 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
@Slf4j
public class SmartAssembleFactory implements MetaViewAdapterFactory {

    private static MetaViewAdapterFactory me = new SmartAssembleFactory();

    public static MetaViewAdapterFactory me() {
        return me;
    }

    private Kv recommendObjectConfig(IMetaObject metaObject) {
        return Kv.create();
    }

    private Kv recommendFieldConfig(IMetaField metaField) {
        /**
         * 分析元字段
         * 拼装instanceFieldConfig
         *
         * FormFieldFactory.createFormField 负责创建动作
         * TODO 等待完善初级智能分配规则,根据元字段属性 指定一些控件类型
         */
        AttributeBuilder.AttributeSteps builder = AttributeBuilder.newBuilder();
        //set default componentName is "TextBox"
        builder.componentName(ComponentType.TEXTBOX.getCode());
        if (metaField.dbType().isText()) {
            if (metaField.dbTypeLength() == 1L) {
                builder.componentName(ComponentType.BOOLBOX.getCode());
            } else {
                builder.maxlength(metaField.dbTypeLength().intValue());
            }
            if (metaField.dbTypeLength() > 255L) {
                builder.componentName(ComponentType.TEXTAREABOX.getCode());
                builder.resizeable("none");
                builder.showOverflowTooltip(true);
            }
        }

        //日期
        if (metaField.dbType().isDate()) {
            if (metaField.dbType().isDateTime()) {
                builder.componentName(ComponentType.DATETIMEBOX.getCode());
            }
            if (metaField.dbType().isTime()) {
                builder.componentName(ComponentType.TIMEBOX.getCode());
            }
            if (metaField.dbType().isDateOnly()) {
                builder.componentName(ComponentType.DATEBOX.getCode());
            }
        }

        //数值
        if (metaField.dbType().isNumber()) {
            builder.componentName(ComponentType.NUMBERBOX.getCode());
        }

        //Json
        if (metaField.dbType().isJson()) {
            builder.componentName(ComponentType.JSONBOX.getCode());
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
            builder.componentName(ComponentType.DROPDOWN.getCode());
        }

        if (metaFieldConfigParse.isRequired()) {
            builder.setConf("rules", new RulesBuilder().required(metaField).buildRules(metaField.fieldCode()));
        }


        return builder.render();
    }

    /**
     * 1. 推测控件(metafield)
     * 2. 推测样式配置(metafield,ComponentType)
     *
     * @param fields
     * @param globalComponentAllConfig 所有组件的全局配置
     *
     * @return
     */
    private List<MetaFieldViewAdapter> analysisFields(Collection<IMetaField> fields, Kv globalComponentAllConfig) {

        List<MetaFieldViewAdapter> metaFields = Lists.newArrayList();

        // 读取globalConfig中的配置
        // WARN recommendComponent 中会根据各种规则,动态配置config,如与globalConfig中有冲突配置,使用覆盖策略;
        for (IMetaField field : fields) {
            Kv recommendConfig = recommendFieldConfig(field);
            Kv globalComponentConfig = UtilKit.getKv(globalComponentAllConfig, recommendConfig.getStr("component_name"));
            Kv fieldInstanceConfig = UtilKit.mergeUseOld(globalComponentConfig, recommendConfig);
            Component fieldComponent = FormFieldFactory.createFormFieldDefault(field, fieldInstanceConfig);
            metaFields.add(new MetaFieldViewAdapter(field, fieldComponent, globalComponentConfig, fieldInstanceConfig));
        }
        return metaFields;
    }

    /**
     * 使用系统默认ComponentConfig 信息构建容器View组件
     *
     * @param metaObject
     * @param componentType
     *
     * @return
     */
    @Override
    public MetaObjectViewAdapter createMetaObjectViewAdapter(IMetaObject metaObject, ComponentType componentType) {

        /**
         * 分析元对象
         * 适配选择Component,进行构建IViewAdapter
         */
        Component containerComponent = ViewFactory.createEmptyViewComponent(componentType.getCode());
        //全部全局配置
        Kv globalComponentAllConfig = ServiceManager.componentService().loadComponentsFlatMap();
        //某一组件全局配置
        Kv globalComponentConfig = UtilKit.getKv(globalComponentAllConfig, componentType.getCode());

        Kv levelObjectConfig = recommendObjectConfig(metaObject);

        // 因配置为自动计算,所以传入组件全局配置map
        List<MetaFieldViewAdapter> fields = analysisFields(metaObject.fields(), globalComponentAllConfig);

        return new MetaObjectViewAdapter(metaObject, containerComponent, globalComponentConfig, levelObjectConfig, fields);
    }
}
