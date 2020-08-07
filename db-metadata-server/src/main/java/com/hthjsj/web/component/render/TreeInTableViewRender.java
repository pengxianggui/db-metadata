package com.hthjsj.web.component.render;

import com.alibaba.fastjson.JSON;
import com.google.common.base.Preconditions;
import com.hthjsj.analysis.component.ComponentRender;
import com.hthjsj.analysis.meta.IMetaField;
import com.hthjsj.analysis.meta.IMetaObject;
import com.hthjsj.web.component.TableTreeView;
import com.hthjsj.web.component.form.FormField;
import com.hthjsj.web.component.form.FormFieldFactory;
import com.hthjsj.web.kit.UtilKit;
import com.hthjsj.web.ui.ComponentInstanceConfig;
import com.jfinal.kit.Kv;

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
        component.getMeta().putIfAbsent("treeInTableConfig", JSON.parseObject(metaObject.configParser().treeConfig()));
    }

    @Override
    public TableTreeView component() {
        return component;
    }

    @Override
    public Kv render() {
        Kv kv = UtilKit.getKv(componentInstanceConfig, metaObject.code());
        UtilKit.deepMerge(component.getMeta(), kv, false);

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
