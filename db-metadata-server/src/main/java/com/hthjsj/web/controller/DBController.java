package com.hthjsj.web.controller;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.hthjsj.AnalysisConfig;
import com.hthjsj.analysis.component.ComponentType;
import com.hthjsj.analysis.db.MysqlService;
import com.hthjsj.analysis.db.Table;
import com.hthjsj.analysis.meta.IMetaObject;
import com.hthjsj.web.AppConst;
import com.hthjsj.web.component.Components;
import com.hthjsj.web.kit.InitKit;
import com.hthjsj.web.ui.MetaObjectViewAdapter;
import com.hthjsj.web.ui.OptionsKit;
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
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

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
        preConditionCheck();

        StringBuilder sb = new StringBuilder();

        Set<String> tables = AppConst.SYS_TABLE.column(AppConst.INITABLE).entrySet().stream()
                .filter(Map.Entry::getValue)
                .map(Map.Entry::getKey).collect(Collectors.toSet());

        sb.append("即将清除的数据表:").append(tables);
        log.warn("清空meta相关表{}", sb.toString());
        tables.forEach(key -> {
            Db.delete("delete from " + key);
        });

        renderJson(Ret.ok("msg", sb.toString()));
    }

    @Before(Tx.class)
    public void init() {
        preConditionCheck();

        Components.me().init(); // 初始化组件(包括组件配置)

        String mainDB = AnalysisConfig.me().dbMainStr();
        List<Table> sysTables = Aop.get(MysqlService.class).showTables(AnalysisConfig.me().dbMainStr())
                // 过滤出系统表
                .stream().filter(t -> AppConst.SYS_TABLE.rowKeySet().contains(t.getTableName()))
                .collect(Collectors.toList());

        for (Table t : sysTables) {
            log.info("init table:{} - {}", t.getTableName(), t.getTableComment());
            IMetaObject metaObject = metaService().importFromTable(mainDB, t.getTableName());
            metaService().saveMetaObject(metaObject, true);
            metaObject = metaService().findByCode(t.getTableName());

            // 根据 defaultInstance.json 初始化系统表元对象对应的容器组件
            for (ComponentType type : InitKit.me().getPredefinedComponentType(metaObject.code())) {
                MetaObjectViewAdapter metaObjectIViewAdapter = UIManager.getSmartAutoView(metaObject, type);
                componentService().newObjectConfig(metaObjectIViewAdapter.getComponent(), metaObject, metaObjectIViewAdapter.getInstanceConfig());
            }
        }

        // 根据固定文件(defaultInstance.json和defaultObject.json)更新元数据、实例配置
        InitKit.me().updateMetaObjectConfig().updateInstanceConfig();
        renderJson(Ret.ok());
    }

    private void preConditionCheck() {
        String token = getPara(0, "");
        Preconditions.checkArgument(JFinal.me().getConstants().getDevMode(), "未处于开发模式,无法执行该操作");
        Preconditions.checkArgument(token.equalsIgnoreCase("hello"), "口令错误,不能初始化系统");
    }
}
