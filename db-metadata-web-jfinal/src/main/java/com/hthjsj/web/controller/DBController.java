package com.hthjsj.web.controller;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.hthjsj.analysis.component.ComponentType;
import com.hthjsj.analysis.db.MysqlService;
import com.hthjsj.analysis.db.Table;
import com.hthjsj.analysis.meta.IMetaObject;
import com.hthjsj.web.ServiceManager;
import com.hthjsj.web.component.ViewFactory;
import com.hthjsj.web.ui.IViewAdapter;
import com.hthjsj.web.ui.SmartAssemble;
import com.jfinal.aop.Aop;
import com.jfinal.aop.Before;
import com.jfinal.kit.Kv;
import com.jfinal.kit.Ret;
import com.jfinal.plugin.activerecord.tx.Tx;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * <p> @Date : 2019/10/9 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
@Slf4j
public class DBController extends FrontRestController {

    @Override
    public void index() {
        list();
    }

    @Override
    public void list() {
        List<String> schemas = Aop.get(MysqlService.class).showSchema();
        List<Kv> results = Lists.newArrayList();
        for (String s : schemas) {
            results.add(Kv.by("key", s).set("value", s));
        }
        renderJson(Ret.ok("data", results));
    }

    /**
     * <pre>
     *  param:
     *      schemaName : default value "metadata"
     *  @return
     * </pre>
     */
    public void tables() {
        String schemaName = getPara(0, getPara("schemaName"));
        Preconditions.checkNotNull(schemaName, "[schemaName]数据库名称是必填参数");
        List<Table> tables = Aop.get(MysqlService.class).showTables(schemaName);
        List<Kv> results = Lists.newArrayList();
        tables.forEach(r -> {
            results.add(Kv.create().set("key", r.getTableName()).set("value", r.getTableName()));
        });
        //        List<String> tableNames = Aop.get(MysqlService.class).showTables(schemaName).stream().map(table -> table.getTableName()).collect(Collectors.toList());

        renderJson(Ret.ok("data", results));
    }

    @Before(Tx.class)
    public void init() {
        String token = getPara(0, "");
        Preconditions.checkArgument(token.equalsIgnoreCase("hello"), "口令错误,不能初始化系统");


        List<Table> tables = Aop.get(MysqlService.class).showTables("metadata");

        for (Table t : tables) {
            log.info("init table:{} - {}", t.getTableName(), t.getTableComment());
            if (!t.getTableName().equalsIgnoreCase("PDMAN_DB_VERSION".toLowerCase())) {
                IMetaObject metaObject = ServiceManager.metaService().importFromTable("metadata", t.getTableName());
                ServiceManager.metaService().saveMetaObject(metaObject, true);
                metaObject = ServiceManager.metaService().findByCode(t.getTableName());
                Kv metaConfig = Kv.create();

                //TableView
                IViewAdapter<IMetaObject> metaObjectIViewAdapter = SmartAssemble.analysisObject(metaObject, ComponentType.TABLEVIEW);
                metaConfig.set(metaObject.code(), metaObjectIViewAdapter.instanceConfig().toJson());
                metaObjectIViewAdapter.fields().forEach(m -> {
                    metaConfig.set(m.getMeta().fieldCode(), m.instanceConfig().toJson());
                });
                ServiceManager.componentService().newObjectConfig(ViewFactory.createViewComponent(ComponentType.TABLEVIEW.getCode()), metaObject, metaConfig, false);

                //FormView
                metaObjectIViewAdapter = SmartAssemble.analysisObject(metaObject, ComponentType.FORMVIEW);
                metaConfig.clear();
                metaConfig.set(metaObject.code(), metaObjectIViewAdapter.instanceConfig().toJson());
                metaObjectIViewAdapter.fields().forEach(m -> {
                    metaConfig.set(m.getMeta().fieldCode(), m.instanceConfig().toJson());
                });
                ServiceManager.componentService().newObjectConfig(ViewFactory.createViewComponent(ComponentType.FORMVIEW.getCode()), metaObject, metaConfig, false);
            }
        }
        renderJson(Ret.ok());
    }
}
