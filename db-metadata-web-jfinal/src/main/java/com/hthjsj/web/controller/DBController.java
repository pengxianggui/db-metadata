package com.hthjsj.web.controller;

import com.google.common.base.Preconditions;
import com.hthjsj.analysis.db.MysqlService;
import com.jfinal.aop.Aop;
import com.jfinal.kit.Ret;

/**
 * <p> @Date : 2019/10/9 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
public class DBController extends FrontRestController {

    @Override
    public Ret index() {
        return list();
    }

    @Override
    public Ret list() {
        renderJson(Ret.ok("data", Aop.get(MysqlService.class).showSchema()));
        return null;
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

        renderJson(Ret.ok("data", Aop.get(MysqlService.class).showTables(schemaName)));
    }
}
