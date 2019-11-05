package com.hthjsj.web.ui;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.hthjsj.analysis.meta.IMetaField;
import com.hthjsj.analysis.meta.IMetaObject;
import com.hthjsj.web.ServiceManager;
import com.hthjsj.web.component.ComponentType;
import com.hthjsj.web.component.ViewComponent;
import com.hthjsj.web.component.ViewFactory;
import com.hthjsj.web.component.form.FormFieldFactory;
import com.jfinal.kit.Kv;

import java.util.Collection;
import java.util.List;

/**
 * <p> @Date : 2019/11/4 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
public class SmartAssemble {

    /**
     * 使用系统默认ComponentConfig 信息构建容器View组件
     *
     * @param metaObject
     * @param componentType
     *
     * @return
     */
    public static IViewAdapter<IMetaObject> analysisObject(IMetaObject metaObject, ComponentType componentType) {

        /**
         * 分析元对象
         * 适配选择Component,进行构建IViewAdapter
         */
        ViewComponent containerComponent = null;
        switch (componentType) {
            case TABLEVIEW:
            case FORMVIEW:
            default:
                containerComponent = ViewFactory.createViewComponent(componentType.getCode());
                break;
        }
        Kv globalAllConfig = ServiceManager.componentService().loadComponentsFlatMap();
        Kv containerConfig = JSON.parseObject(globalAllConfig.getStr(componentType.getCode()), Kv.class);
        List<IViewAdapter<IMetaField>> fields = analysisFields(metaObject.fields(), globalAllConfig);
        IViewAdapter<IMetaObject> defaultObjectViewAdapter = new DefaultObjectViewAdapter(metaObject, containerComponent, containerConfig, fields);
        return defaultObjectViewAdapter;
    }

    private static List<IViewAdapter<IMetaField>> analysisFields(Collection<IMetaField> fields, Kv globalConfig) {

        List<IViewAdapter<IMetaField>> metaFields = Lists.newArrayList();

        for (IMetaField field : fields) {
            ViewComponent component = recommendComponent(field);
            Kv fieldConfig = JSON.parseObject(globalConfig.getStr(component.componentType().getCode()), Kv.class);
            metaFields.add(new DefaultFieldViewAdapter(field, fieldConfig, component));
        }

        return metaFields;
    }

    private static ViewComponent recommendComponent(IMetaField metaField) {
        /**
         * 分析元字段
         * 拼装instanceFieldConfig
         *
         * FormFieldFactory.createFormField 负责创建动作
         * TODO 等待完善初级智能分配规则,根据元字段属性 指定一些控件类型
         */
        Kv instanceFieldConfig = Kv.create();
        instanceFieldConfig.set("component_name", ComponentType.TEXTBOX);
        return FormFieldFactory.createFormField(metaField, instanceFieldConfig);
    }

    static class DefaultObjectViewAdapter implements IViewAdapter<IMetaObject> {

        IMetaObject metaObject;

        ViewComponent viewComponent;

        Kv config;

        List<IViewAdapter<IMetaField>> fields;

        public DefaultObjectViewAdapter(IMetaObject metaObject, ViewComponent viewComponent, Kv config, List<IViewAdapter<IMetaField>> fields) {
            this.metaObject = metaObject;
            this.viewComponent = viewComponent;
            this.config = config;
            this.fields = fields;
        }

        @Override
        public IMetaObject getMeta() {
            return metaObject;
        }

        @Override
        public Kv instanceConfig() {
            return config;
        }

        @Override
        public ViewComponent getComponent() {
            return viewComponent;
        }

        @Override
        public List<IViewAdapter<IMetaField>> fields() {
            return fields;
        }
    }

    static class DefaultFieldViewAdapter implements IViewAdapter<IMetaField> {

        IMetaField metaField;

        Kv config;

        ViewComponent viewComponent;

        public DefaultFieldViewAdapter(IMetaField metaField, Kv config, ViewComponent viewComponent) {
            this.metaField = metaField;
            this.config = config;
            this.viewComponent = viewComponent;
        }

        @Override
        public IMetaField getMeta() {
            return metaField;
        }

        @Override
        public Kv instanceConfig() {
            return config;
        }

        @Override
        public ViewComponent getComponent() {
            return viewComponent;
        }

        @Override
        public List<IViewAdapter<IMetaField>> fields() {
            return null;
        }
    }
}
