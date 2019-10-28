package com.hthjsj;

import com.hthjsj.analysis.meta.DbMetaService;
import com.hthjsj.analysis.meta.MetaObject;
import com.hthjsj.web.component.ComponentFactory;
import com.hthjsj.web.component.ComponentService;
import com.hthjsj.web.component.TableView;
import com.jfinal.aop.Aop;
import com.jfinal.kit.Kv;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;

import java.util.Date;

/**
 * <p> Class title: </p>
 * <p> @Describe: </p>
 * <p> @Date : 2019/10/10 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
public class ComponentTest {

    public static void main(String[] args) {
        AnalysisConfig analysisConfig = AnalysisConfig.me();
        analysisConfig.start();
        MetaObject metaObject = (MetaObject) Aop.get(DbMetaService.class).findByCode("meta_object");
        TableView tableView = ComponentFactory.createTableView(metaObject.name(), metaObject.code(), metaObject);
        //        init(metaObject);
        ComponentService componentService = Aop.get(ComponentService.class);
        Kv kv = componentService.loadFieldsConfigMap(tableView.type(), "object_code_admin111");
        System.out.println(kv.toJson());
    }

    public static void init(MetaObject metaObject) {
        TableView tableView = ComponentFactory.createTableView(metaObject.name(), metaObject.code(), metaObject);
        {
            //            tableView.setGlobal("hahaa", "xixi");
            Record record = new Record();
            record.set("id", new Date().getTime());
            record.set("config", tableView.toKv().toJson());
            record.set("en", tableView.type());
            record.set("cn", tableView.type());
            Db.save("meta_component", "id", record);
        }
        ComponentService componentService = Aop.get(ComponentService.class);
        componentService.newObjectConfig(tableView, "object_code_admin111", Kv.create());
        componentService.newFieldConfig(tableView, "object_code_admin111", "one1");
        componentService.newFieldConfig(tableView, "object_code_admin111", "one2");
        componentService.newFieldConfig(tableView, "object_code_admin111", "one3");
        componentService.newFieldConfig(tableView, "object_code_admin111", "one4");
    }
}
