package com.hthjsj.web.component.render;

import com.hthjsj.analysis.component.ComponentRender;
import com.hthjsj.analysis.component.ComponentType;
import com.hthjsj.analysis.component.ViewContainer;
import com.hthjsj.analysis.meta.IMetaField;
import com.hthjsj.analysis.meta.IMetaObject;
import com.hthjsj.web.UtilKit;
import com.hthjsj.web.component.form.FormField;
import com.hthjsj.web.component.form.FormFieldFactory;
import com.hthjsj.web.component.form.FormView;
import com.jfinal.kit.Kv;

/**
 * <p> @Date : 2019/11/27 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
public class MetaViewRender<C extends ViewContainer> implements ComponentRender<C> {

    IMetaObject metaObject;

    C component;

    Kv instanceFlatConfig;

    public MetaViewRender(IMetaObject metaObject, C component, Kv instanceFlatConfig) {
        this.metaObject = metaObject;
        this.component = component;
        this.instanceFlatConfig = instanceFlatConfig;
        component.getMeta().putIfAbsent("objectCode", metaObject.code());
        component.getMeta().putIfAbsent("objectPrimaryKey", metaObject.primaryKey());
    }

    @Override
    public C component() {
        return component;
    }

    /**
     * TABLEVIEW,SEARCHVIEW,FORMVIEW(ADD,UPDATE,DETAILS)共用的渲染逻辑;
     * WARN render 需要保证幂等性
     *
     * @return
     */
    @Override
    public Kv render() {

        Kv kv = UtilKit.getKv(instanceFlatConfig, metaObject.code());
        UtilKit.deepMerge(component.getMeta(), kv, true);
        component.getFields().clear();

        for (IMetaField metaField : metaObject.fields()) {
            Kv config = UtilKit.getKv(instanceFlatConfig, metaField.fieldCode());
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
        }
        return component.getMeta();
    }
}
