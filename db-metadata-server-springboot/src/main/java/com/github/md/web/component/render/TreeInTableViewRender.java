package com.github.md.web.component.render;

import com.alibaba.fastjson.JSON;
import com.github.md.web.component.TableTreeView;
import com.google.common.base.Preconditions;
import com.github.md.analysis.component.ComponentRender;
import com.github.md.analysis.meta.IMetaField;
import com.github.md.analysis.meta.IMetaObject;
import com.github.md.web.component.form.FormField;
import com.github.md.web.component.form.FormFieldFactory;
import com.github.md.web.kit.UtilKit;
import com.github.md.web.ui.ComponentInstanceConfig;
import com.github.md.analysis.kit.Kv;

/**
 * <p> @Date : 2020/8/5 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
public class TreeInTableViewRender implements ComponentRender<TableTreeView> {

    private final IMetaObject metaObject;

    private final TableTreeView component;

    private final ComponentInstanceConfig componentInstanceConfig;

    public TreeInTableViewRender(IMetaObject metaObject, TableTreeView tableTreeView, ComponentInstanceConfig instanceFlatConfig) {
        this.metaObject = metaObject;
        this.component = tableTreeView;
        this.componentInstanceConfig = instanceFlatConfig;
        component.getMeta().putIfAbsent("objectCode", metaObject.code());
        Preconditions.checkNotNull(metaObject.configParser().treeConfig(), "未找到[%s]对象的数据结构配置信息,请在[元对象配置]设置[数据结构->树形表]", metaObject.code());
        // 将元对象的树结构配置设置到组件实例配置中
        component.getMeta().putIfAbsent("treeConfig", JSON.parseObject(metaObject.configParser().treeConfig()));
    }

    @Override
    public TableTreeView component() {
        return component;
    }

    @Override
    public Kv render() {
        Kv kv = UtilKit.getKv(componentInstanceConfig, metaObject.code());
        UtilKit.deepMerge(component.getMeta(), kv, false);
        component.getFields().clear();

        for (IMetaField metaField : metaObject.fields()) {
            Kv config = UtilKit.getKv(componentInstanceConfig.getFieldsMap(), metaField.fieldCode());
            FormField formField = FormFieldFactory.createFormFieldInContainer(metaField, config, component);

            if (metaField.configParser().isListShow()) {
                component.getFields().add(formField);
                continue;
            }
        }
        return component.getMeta();
    }
}
