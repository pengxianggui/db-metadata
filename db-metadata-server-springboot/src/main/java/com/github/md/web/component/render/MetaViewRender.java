package com.github.md.web.component.render;

import com.github.md.web.component.form.FormField;
import com.github.md.web.component.form.FormFieldFactory;
import com.github.md.web.component.form.FormView;
import com.github.md.analysis.component.ComponentRender;
import com.github.md.analysis.component.ComponentType;
import com.github.md.analysis.component.ViewContainer;
import com.github.md.analysis.meta.IMetaField;
import com.github.md.analysis.meta.IMetaObject;
import com.github.md.web.kit.UtilKit;
import com.github.md.web.ui.ComponentInstanceConfig;
import com.github.md.analysis.kit.Kv;

/**
 * <p> @Date : 2019/11/27 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
public class MetaViewRender<C extends ViewContainer> implements ComponentRender<C> {

    IMetaObject metaObject;

    C component;

    ComponentInstanceConfig componentInstanceConfig;

    public MetaViewRender(IMetaObject metaObject, C component, ComponentInstanceConfig instanceFlatConfig) {
        this.metaObject = metaObject;
        this.component = component;
        this.componentInstanceConfig = instanceFlatConfig;
        component.getMeta().putIfAbsent("objectCode", metaObject.code());
        component.getMeta().putIfAbsent("objectPrimaryKey", metaObject.primaryKey());
    }

    @Override
    public C component() {
        return component;
    }

    /**
     * TABLEVIEW,SEARCHVIEW,FORMVIEW(ADD,UPDATE,DETAILS)共用的渲染逻辑;
     * TODO 各View逻辑复杂后,拆分
     * WARN render 需要保证幂等性
     *
     * @return
     */
    @Override
    public Kv render() {

        Kv kv = UtilKit.getKv(componentInstanceConfig, metaObject.code());
        UtilKit.deepMerge(component.getMeta(), kv, false);
        component.getFields().clear();

        for (IMetaField metaField : metaObject.fields()) {
            Kv config = UtilKit.getKv(componentInstanceConfig.getFieldsMap(), metaField.fieldCode());
            FormField formField = FormFieldFactory.createFormFieldInContainer(metaField, config, component);

            if (component.componentType() == ComponentType.TABLEVIEW) {
                if (metaField.configParser().isListShow()) {
                    component.getFields().add(formField);
                    continue;
                }
            }

            if (component.componentType() == ComponentType.SEARCHVIEW) {
                if (metaField.configParser().isSearch()) {
                    component.getFields().add(formField);
                    continue;
                }
            }
            if (component.componentType() == ComponentType.FORMVIEW) {
                if (((FormView) component).isAdd()) {
                    if (metaField.configParser().isAdd()) {
                        component.getFields().add(formField);
                        continue;
                    }
                } else if (((FormView) component).isUpdate()) {
                    if (metaField.configParser().isUpdate()) {
                        component.getFields().add(formField);
                        continue;
                    }
                } else if (((FormView) component).isView()) {
                    if (metaField.configParser().isView()) {
                        component.getFields().add(formField);
                        continue;
                    }
                } else {
                    component.getFields().add(formField);
                }
            }
            if (component.componentType() == ComponentType.TREEVIEW) {
                
            }
        }
        return component.getMeta();
    }
}
