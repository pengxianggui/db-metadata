package com.hthjsj.web.component;

import com.alibaba.fastjson.JSON;
import com.hthjsj.analysis.meta.IMetaField;
import com.hthjsj.analysis.meta.MetaObject;
import com.hthjsj.web.ServiceManager;
import com.hthjsj.web.component.form.*;
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
        //TODO bad small code
        tableView.dataUrl("/table/list/" + metaObject.code());

        Kv tableViewConfig = ServiceManager.componentService().loadObjectFlatConfig(tableView.type(), metaObject.code());
        log.info("ComponentTableViewConfig:{}", tableViewConfig.toJson());

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
                Kv kv = JSON.parseObject(tableViewConfig.getStr(field.fieldCode()), Kv.class);
                kv.forEach((k, v) -> meta.merge(k, v, (oldValue, newValue) -> oldValue));
                return kv;
            }
        });
        return tableView;
    }

    public static FormView createFormView(MetaObject metaObject) {
        Kv formViewConfig = ServiceManager.componentService().loadObjectFlatConfig(ComponentType.FORMVIEW.code, metaObject.code());
        log.info("ComponentTableViewConfig:{}", formViewConfig.toJson());
        MetaFormView formView = new MetaFormView(metaObject, formViewConfig);
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
            component = new DropDownBox();
            break;
        case FORMVIEW:
            component = new FormView();
            break;
        case TABLEVIEW:
            component = new TableView();
            break;
        case TEXTBOX:
            component = new TextBox();
            break;
        default:
            break;
        }
        return component;
    }
}
