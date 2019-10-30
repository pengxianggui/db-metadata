package com.hthjsj.web.component;

import com.hthjsj.analysis.meta.IMetaField;
import com.hthjsj.analysis.meta.MetaObject;
import com.hthjsj.web.ServiceManager;
import com.hthjsj.web.component.form.*;
import com.jfinal.aop.Aop;
import com.jfinal.kit.Kv;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

/**
 * <p> @Date : 2019/10/21 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
@Slf4j
public class ViewFactory {

    public static TableView createTableView(String name, String label, MetaObject metaObject) {
        TableView tableView = new TableView(name, label);

        Kv tableViewConfig = Kv.create().set(ServiceManager.componentService().loadObjectConfig(tableView.type(), metaObject.code()).getColumns());
        log.info("ComponentTableViewConfig:{}", tableViewConfig.toJson());

        Kv fieldsConfig = Aop.get(ComponentService.class).loadFieldsConfigMap(tableView.type(), metaObject.code());
        log.info("fieldsConfig:{}", fieldsConfig.toJson());

        tableView.setViewInject(new ViewInject<TableView>() {

            @Override
            public void inject(TableView component, Kv meta, Kv conf, FieldInject<IMetaField> fieldInject) {
                if (metaObject != null) {
                    meta.set("conf", tableViewConfig);
                    List<Kv> fs = new ArrayList<>();
                    for (IMetaField field : metaObject.fields()) {
                        fs.add(fieldInject.inject(meta, conf, field));
                    }
                    meta.set("columns", fs);
                }
            }
        });

        tableView.setFieldInject(new FieldInject.DefaultFieldInject<IMetaField>() {

            @Override
            public Kv inject(Kv meta, Kv conf, IMetaField field) {
                Kv kv = Kv.create().set("component_name", "TextBox").set("name", field.en()).set("label", field.cn());
                kv.set("conf", fieldsConfig.getOrDefault(field.en(), new Object()));
                return kv;
            }
        });
        return tableView;
    }

    public static FormView createFormView(MetaObject metaObject) {
        Kv formViewConfig = Kv.create().set(ServiceManager.componentService().loadObjectConfig(ComponentType.FORMVIEW.code, metaObject.code()).getColumns());
        log.info("ComponentTableViewConfig:{}", formViewConfig.toJson());
        Kv fieldsConfig = Aop.get(ComponentService.class).loadFieldsConfigMap(ComponentType.FORMVIEW.code, metaObject.code());
        MetaFormView formView = new MetaFormView(metaObject, formViewConfig, fieldsConfig);
        return formView;
    }

    public static ViewComponent createViewComponent(String typeString) {
        ComponentType type = ComponentType.V(typeString);
        ViewComponent component = null;
        switch (type) {
        case BUTTON:
            component = new Button();
            break;
        case DROPDOWN:
            component = new DropDown();
            break;
        case FORMVIEW:
            component = new FormView();
            break;
        case TABLEVIEW:
            component = new TableView();
            break;
        case INPUTFIELD:
            component = new InputField();
            break;
        default:
            break;
        }
        return component;
    }
}
