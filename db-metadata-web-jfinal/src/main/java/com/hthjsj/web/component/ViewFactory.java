package com.hthjsj.web.component;

import com.hthjsj.analysis.component.Component;
import com.hthjsj.analysis.component.ComponentType;
import com.hthjsj.analysis.component.FieldInject;
import com.hthjsj.analysis.component.ViewInject;
import com.hthjsj.analysis.meta.IMetaField;
import com.hthjsj.analysis.meta.MetaObject;
import com.hthjsj.web.ServiceManager;
import com.hthjsj.web.UtilKit;
import com.hthjsj.web.component.form.FormField;
import com.hthjsj.web.component.form.FormFieldFactory;
import com.hthjsj.web.component.form.FormView;
import com.jfinal.kit.Kv;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p> @Date : 2019/10/21 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
@Slf4j
public class ViewFactory {

    public static TableView createTableView(MetaObject metaObject) {
        Kv instanceFlatConfig = ServiceManager.componentService().loadObjectConfigFlat(ComponentType.TABLEVIEW.getCode(), metaObject.code());
        return createTableView(metaObject, instanceFlatConfig);
    }

    public static TableView createTableView(MetaObject metaObject, Kv instanceFlatConfig) {
        TableView tableView = new TableView(metaObject.code(), metaObject.name());
        tableView.setViewInject(new ViewInject<TableView>() {

            @Override
            public void inject(TableView component, Kv meta, FieldInject<IMetaField> fieldInject) {
                meta.putIfAbsent("objectCode", metaObject.code());
                Kv kv = UtilKit.getKv(instanceFlatConfig, metaObject.code());
                UtilKit.mergeUseOld(meta, kv);

                List<Kv> fs = new ArrayList<>();
                for (IMetaField field : metaObject.fields()) {
                    fs.add(fieldInject.inject(meta, field));
                }
                meta.set("columns", fs);
            }
        });

        tableView.setFieldInject(new FieldInject.DefaultFieldInject<IMetaField>() {

            @Override
            public Kv inject(Kv meta, IMetaField field) {
                Kv kv = UtilKit.getKv(instanceFlatConfig, field.fieldCode());
                return UtilKit.mergeUseOld(meta, kv);
            }
        });
        return tableView;
    }

    public static FormView createFormView(MetaObject metaObject) {
        Kv instanceFlatConfig = ServiceManager.componentService().loadObjectConfigFlat(ComponentType.FORMVIEW.getCode(), metaObject.code());
        log.info("ComponentFormViewConfig:{}", instanceFlatConfig.toJson());
        return createFormView(metaObject, instanceFlatConfig);
    }

    public static FormView createFormView(MetaObject metaObject, Kv instanceFlatConfig) {

        FormView formView = new FormView(metaObject.code(), metaObject.name());
        formView.setViewInject(new ViewInject<FormView>() {

            @Override
            public void inject(FormView component, Kv meta, FieldInject<IMetaField> fieldInject) {
                meta.putIfAbsent("objectCode", metaObject.code());

                Kv kv = UtilKit.getKv(instanceFlatConfig, metaObject.code());
                UtilKit.mergeUseOld(meta, kv);

                for (IMetaField metaField : metaObject.fields()) {
                    Kv config = UtilKit.getKv(instanceFlatConfig, metaField.fieldCode());
                    FormField formField = FormFieldFactory.createFormField(metaField, config);
                    component.getFields().add(formField);
                }
                //overwrite columns
                meta.set("columns", component.getFields().stream().map((k) -> k.toKv()).collect(Collectors.toList()));
            }
        });
        return formView;
    }

    public static Component createViewComponent(MetaObject metaObject, ComponentType componentType) {
        Component component = null;
        switch (componentType) {
            case FORMVIEW:
                component = createFormView(metaObject);
                break;
            case TABLEVIEW:
                component = createTableView(metaObject);
                break;
            case SEARCHPANEL:
                throw new RuntimeException("not finished");
            default:
        }
        return component;
    }

    /**
     * 构建view容器
     *
     * @param typeString
     *
     * @return
     */
    public static Component createEmptyViewComponent(String typeString) {
        ComponentType type = ComponentType.V(typeString);
        Component component = null;

        switch (type) {
            case FORMVIEW:
                component = new FormView(type.getCn(), type.getCode());
                break;
            case TABLEVIEW:
                component = new TableView(type.getCn(), type.getCode());
                break;
            case SEARCHPANEL:
                throw new RuntimeException("not finished");
            default:
                throw new ComponentException("此操作不支持创建非容器控件 [%s]", typeString);
        }
        return component;
    }
}
