package com.hthjsj.web.component.render;

import com.hthjsj.analysis.component.ComponentRender;
import com.hthjsj.analysis.meta.IMetaField;
import com.hthjsj.analysis.meta.IMetaObject;
import com.hthjsj.web.component.TreeTableView;
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
public class TreeInTableViewRender implements ComponentRender<TreeTableView> {

    private final IMetaObject metaObject;

    private final TreeTableView component;

    private final ComponentInstanceConfig componentInstanceConfig;

    public TreeInTableViewRender(IMetaObject metaObject, TreeTableView treeTableView, ComponentInstanceConfig instanceFlatConfig) {
        this.metaObject = metaObject;
        this.component = treeTableView;
        this.componentInstanceConfig = instanceFlatConfig;
        component.getMeta().putIfAbsent("objectCode", metaObject.code());
    }

    @Override
    public TreeTableView component() {
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
