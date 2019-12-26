package com.hthjsj.web.controller;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.hthjsj.analysis.component.ComponentType;
import com.hthjsj.analysis.db.MysqlService;
import com.hthjsj.analysis.db.Table;
import com.hthjsj.analysis.meta.IMetaObject;
import com.hthjsj.web.AppConst;
import com.hthjsj.web.component.Components;
import com.hthjsj.web.kit.InitKit;
import com.hthjsj.web.ui.MetaObjectViewAdapter;
import com.hthjsj.web.ui.OptionsKit;
import com.hthjsj.web.ui.RenderHelper;
import com.hthjsj.web.ui.UIManager;
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
        renderJson(Ret.ok("data", OptionsKit.transKeyValue(schemas.toArray(new String[schemas.size()]))));
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
        renderJson(Ret.ok("data", results));
    }

    public void truncate() {
        String token = getPara(0, "");
        StringBuilder sb = new StringBuilder();
        sb.append("即将清除的数据表:meta_component, meta_component_instance,meta_config,meta_field,meta_object");
        Preconditions.checkArgument(JFinal.me().getConstants().getDevMode(), "未处于开发模式,无法执行该操作");
        Preconditions.checkArgument(token.equalsIgnoreCase("hello"), "开发token不正确:{}", sb.toString());
        log.warn("清空meta相关表{}", sb.toString());
        AppConst.SYS_TABLE.rowKeySet().forEach(key -> {
            Db.delete("delete from " + key);
        });
        renderJson(Ret.ok("msg", sb.toString()));
    }

    @Before(Tx.class)
    public void init() {
        String token = getPara(0, "");
        Preconditions.checkArgument(token.equalsIgnoreCase("hello"), "口令错误,不能初始化系统");
        Preconditions.checkArgument(JFinal.me().getConstants().getDevMode(), "未处于开发模式,无法执行该操作");
        List<Table> tables = Aop.get(MysqlService.class).showTables("metadata");

        Components.me().init();
        for (Table t : tables) {
            log.info("init table:{} - {}", t.getTableName(), t.getTableComment());
            if (!t.getTableName().equalsIgnoreCase("PDMAN_DB_VERSION".toLowerCase())) {
                IMetaObject metaObject = metaService().importFromTable("metadata", t.getTableName());
                metaService().saveMetaObject(metaObject, true);
                metaObject = metaService().findByCode(t.getTableName());

                smartInit(metaObject, ComponentType.TABLEVIEW);
                smartInit(metaObject, ComponentType.FORMVIEW);
                smartInit(metaObject, ComponentType.SEARCHVIEW);
            }
        }
        InitKit.me().importInstanceConfig().importMetaObjectConfig();
        renderJson(Ret.ok());
    }

    /**
     * 自动初始化 元对象*view配置;
     *
     * @param metaObject
     * @param componentType
     */
    private void smartInit(IMetaObject metaObject, ComponentType componentType) {
        MetaObjectViewAdapter metaObjectIViewAdapter = UIManager.getSmartAutoView(metaObject, componentType);
        Kv metaConfig = Kv.create().set(RenderHelper.renderObjectFlatMap(metaObjectIViewAdapter));
        componentService().newObjectConfig(metaObjectIViewAdapter.getComponent(), metaObject, metaConfig);
    }
}
