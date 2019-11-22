package com.hthjsj.web.controller;

import com.google.common.base.Preconditions;
import com.hthjsj.web.ui.SqlAnalysis;
import com.jfinal.core.Controller;
import com.jfinal.core.JFinal;
import com.jfinal.kit.Ret;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;

import java.util.List;

/**
 * <p> @Date : 2019/10/23 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
public class ValidatorController extends Controller {

    /**
     * 配置Metafield config 时需用来验证sql 有效性
     * http://0.0.0.0:8888/check/sql?sql=select id ,ac from meta_dict
     * http://0.0.0.0:8888/check/sql?sql=select id ,name cn from meta_dict
     */
    public void sql() {
        String exeSql = getPara("sql", "");
        if (JFinal.me().getConstants().getDevMode()) {
            Preconditions.checkArgument(SqlAnalysis.check(exeSql), "无效SQL [%s]", exeSql);
            Preconditions.checkArgument(SqlAnalysis.checkIdCn(exeSql), "非法列的查询,只允许以ID,CN列返回,查询SQL:[%s]", exeSql);
            List<Record> result = Db.find(exeSql);
            renderJson(Ret.ok("data", result));
        }
    }
}
