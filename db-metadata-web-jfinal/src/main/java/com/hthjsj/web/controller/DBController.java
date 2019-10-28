package com.hthjsj.web.controller;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.hthjsj.analysis.db.MysqlService;
import com.hthjsj.analysis.db.Table;
import com.jfinal.aop.Aop;
import com.jfinal.kit.Kv;
import com.jfinal.kit.Ret;

import java.util.List;

/**
 * <p> @Date : 2019/10/9 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
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
}
