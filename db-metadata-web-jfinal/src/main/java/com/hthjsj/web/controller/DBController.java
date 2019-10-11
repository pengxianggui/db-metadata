package com.hthjsj.web.controller;

import com.hthjsj.analysis.db.MysqlService;
import com.jfinal.aop.Aop;
import com.jfinal.kit.Ret;

/**
 * <p> Class title: </p>
 * <p> @Describe: </p>
 * <p> @Date : 2019/10/9 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
public class DBController extends FrontRestController {

    /**
     * <pre>
     *  param:
     *      schemaName : default value "metadata"
     *  @return
     * </pre>
     */
    @Override public Ret list() {
        String schemaName = getPara(0, "metadata");
        MysqlService dbService = Aop.get(MysqlService.class);
        renderJson(Ret.ok("data", dbService.showTables(schemaName)));
        return null;
    }
}
