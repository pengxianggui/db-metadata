package com.hthjsj.web.component;

import com.alibaba.fastjson.JSON;
import com.hthjsj.analysis.component.Component;
import com.hthjsj.analysis.component.ComponentType;
import com.hthjsj.analysis.component.FieldInject;
import com.hthjsj.analysis.component.ViewInject;
import com.hthjsj.analysis.meta.IMetaField;
import com.hthjsj.analysis.meta.MetaObject;
import com.hthjsj.web.ServiceManager;
import com.hthjsj.web.component.form.*;
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

    public static TableView createTableView(String name, String label, MetaObject metaObject) {
        TableView tableView = new TableView(name, label);
        //TODO bad small code
        tableView.dataUrl("/table/list/" + metaObject.code());

        Kv tableViewConfig = ServiceManager.componentService().loadObjectConfigFlat(tableView.type(), metaObject.code());
        log.info("ComponentTableViewConfig:{}", tableViewConfig.toJson());

        tableView.setViewInject(new ViewInject<TableView>() {

            @Override
            public void inject(TableView component, Kv meta, FieldInject<IMetaField> fieldInject) {
                if (metaObject != null) {
                    meta.putIfAbsent("objectCode", metaObject.code());
                    meta.set("conf", tableViewConfig);
                    List<Kv> fs = new ArrayList<>();
                    for (IMetaField field : metaObject.fields()) {
                        fs.add(fieldInject.inject(meta, field));
                    }
                    meta.set("columns", fs);
                }
            }
        });

        tableView.setFieldInject(new FieldInject.DefaultFieldInject<IMetaField>() {

            @Override
            public Kv inject(Kv meta, IMetaField field) {
                Kv kv = JSON.parseObject(tableViewConfig.getStr(field.fieldCode()), Kv.class);
                kv.forEach((k, v) -> meta.merge(k, v, (oldValue, newValue) -> oldValue));
                return kv;
            }
        });
        return tableView;
    }

    public static FormView createFormView(MetaObject metaObject) {
        Kv formViewConfig = ServiceManager.componentService().loadObjectConfigFlat(ComponentType.FORMVIEW.getCode(), metaObject.code());
        log.info("ComponentFormViewConfig:{}", formViewConfig.toJson());

        FormView formView = FormView.POST("/form/doAdd", metaObject.name());
        formView.setViewInject(new ViewInject<FormView>() {

            @Override
            public void inject(FormView component, Kv meta, FieldInject<IMetaField> fieldInject) {
                meta.putIfAbsent("objectCode", metaObject.code());

                Kv kv = JSON.parseObject(formViewConfig.getStr(metaObject.code()), Kv.class);
                //https://blog.csdn.net/tangyaya8/article/details/91399650
                kv.forEach((k, v) -> meta.merge(k, v, (oldVal, newVal) -> oldVal));
                Kv config = null;
                for (IMetaField metaField : metaObject.fields()) {
                    config = JSON.parseObject(formViewConfig.getStr(metaField.fieldCode()), Kv.class);
                    FormField formField = FormFieldFactory.createFormField(metaField, config);
                    component.getFields().add(formField);
                }
                //overwrite columns
                meta.set("columns", component.getFields().stream().map((k) -> k.toKv()).collect(Collectors.toList()));
            }
        });
        return formView;
    }

    public static Component createViewComponent(String typeString) {
        ComponentType type = ComponentType.V(typeString);
        Component component = null;

        switch (type) {
            case BUTTON:
                component = new Button(type.getCn(), type.getCode());
                break;
            case DROPDOWN:
                component = new DropDownBox(type.getCn(), type.getCode());
                break;
            case FORMVIEW:
                component = new FormView(type.getCn(), type.getCode());
                break;
            case TABLEVIEW:
                component = new TableView(type.getCn(), type.getCode());
                break;
            case TEXTBOX:
                component = new TextBox(type.getCn(), type.getCode());
                break;
            default:
                break;
        }
        return component;
    }
}
