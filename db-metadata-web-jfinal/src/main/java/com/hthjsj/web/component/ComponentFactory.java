package com.hthjsj.web.component;

import com.hthjsj.analysis.meta.IMetaField;
import com.hthjsj.analysis.meta.MetaObject;
import com.jfinal.kit.Kv;

import java.util.ArrayList;
import java.util.List;

/**
 * <p> @Date : 2019/10/21 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
public class ComponentFactory {

    public static TableView createTableView(String name, String label, MetaObject metaObject) {
        TableView tableView = new TableView(name, label);
        tableView.setInject(new ViewDataInject() {

            @Override
            public void inject(Kv meta, Kv conf) {
                if (metaObject != null) {
                    List<Kv> fs = new ArrayList<>();
                    for (IMetaField field : metaObject.fields()) {
                        fs.add(itemInject().inject(meta, conf, field));
                    }
                    meta.set("columns", fs);
                }
            }

            @Override
            public FieldDataInject itemInject() {
                return new FieldDataInject<IMetaField>() {

                    @Override
                    public Kv inject(Kv meta, Kv conf, IMetaField field) {
                        return Kv.create().set("component_name", "TextBox").set("name", field.en()).set("label", field.cn());
                    }
                };
            }
        });
        return tableView;
    }
}
