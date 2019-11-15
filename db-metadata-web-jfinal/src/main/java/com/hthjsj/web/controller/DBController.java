package com.hthjsj.web.controller;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.hthjsj.analysis.component.ComponentType;
import com.hthjsj.analysis.db.MysqlService;
import com.hthjsj.analysis.db.Table;
import com.hthjsj.analysis.meta.IMetaObject;
import com.hthjsj.analysis.meta.MetaObject;
import com.hthjsj.web.ServiceManager;
import com.hthjsj.web.component.ViewFactory;
import com.hthjsj.web.ui.MetaObjectViewAdapter;
import com.hthjsj.web.ui.RenderHelper;
import com.hthjsj.web.ui.SmartAssembleFactory;
import com.jfinal.aop.Aop;
import com.jfinal.aop.Before;
import com.jfinal.core.JFinal;
import com.jfinal.kit.Kv;
import com.jfinal.kit.Ret;
import com.jfinal.plugin.activerecord.Db;
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

    public void truncate() {
        String token = getPara(0, "");
        StringBuilder sb = new StringBuilder();
        sb.append("即将清除表:meta_component, meta_component_instance,meta_config,meta_field,meta_object");
        Preconditions.checkArgument(token.equalsIgnoreCase("hello"), "请输入token,{}" + sb.toString());
        Preconditions.checkArgument(JFinal.me().getConstants().getDevMode(), "未处于开发模式,无法执行该操作");
        log.info("清空meta相关表{}", sb.toString());
        Db.delete("delete from meta_component");
        Db.delete("delete from meta_component_instance");
        Db.delete("delete from meta_config");
        Db.delete("delete from meta_field");
        Db.delete("delete from meta_object");
        renderJson(Ret.ok("msg", sb.toString()));
    }

    @Before(Tx.class)
    public void init() {
        String token = getPara(0, "");
        Preconditions.checkArgument(token.equalsIgnoreCase("hello"), "口令错误,不能初始化系统");
        Preconditions.checkArgument(JFinal.me().getConstants().getDevMode(), "未处于开发模式,无法执行该操作");
        List<Table> tables = Aop.get(MysqlService.class).showTables("metadata");

        for (Table t : tables) {
            log.info("init table:{} - {}", t.getTableName(), t.getTableComment());
            if (!t.getTableName().equalsIgnoreCase("PDMAN_DB_VERSION".toLowerCase())) {
                IMetaObject metaObject = ServiceManager.metaService().importFromTable("metadata", t.getTableName());
                ServiceManager.metaService().saveMetaObject(metaObject, true);
                metaObject = ServiceManager.metaService().findByCode(t.getTableName());
                Kv metaConfig = Kv.create();

                //TableView
                MetaObjectViewAdapter metaObjectIViewAdapter = SmartAssembleFactory.analysisObject((MetaObject) metaObject, ComponentType.TABLEVIEW);
                metaConfig = RenderHelper.renderObjectFlatMap(metaObjectIViewAdapter);
                ServiceManager.componentService().newObjectConfig(ViewFactory.createEmptyViewComponent(ComponentType.TABLEVIEW.getCode()), metaObject, metaConfig);

                //FormView
                metaObjectIViewAdapter = SmartAssembleFactory.analysisObject((MetaObject) metaObject, ComponentType.FORMVIEW);
                metaConfig = RenderHelper.renderObjectFlatMap(metaObjectIViewAdapter);

                ServiceManager.componentService().newObjectConfig(ViewFactory.createEmptyViewComponent(ComponentType.FORMVIEW.getCode()), metaObject, metaConfig);
            }
        }
        renderJson(Ret.ok());
    }
}
