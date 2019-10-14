package com.hthjsj.web.controller;

import com.hthjsj.analysis.meta.DbMetaService;
import com.hthjsj.analysis.meta.MetaObject;
import com.hthjsj.web.component.TableView;
import com.hthjsj.web.jfinal.SqlParaExt;
import com.hthjsj.web.query.QueryCondition;
import com.jfinal.aop.Aop;
import com.jfinal.kit.Ret;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;

/**
 * <pre>
 *     以组件名来命名的Controller
 *     TableController 主要处理 该类组件发来的请求;
 * </pre>
 * <p> @Date : 2019/10/10 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
public class TableController extends FrontRestController {

    /**
     * param : objectCode
     */
    @Override
    public Ret index() {
        String objectCode = getPara(0, getPara("objectCode"));
        MetaObject metaObject = (MetaObject) Aop.get(DbMetaService.class).findByCode(objectCode);
        TableView tableView = new TableView(metaObject);
        renderJson(tableView.config());
        return null;
    }

    @Override
    public Ret list() {
        /**
         * 1. query data by metaObject
         *  1.1 query all data paging
         *  1.2 query data by fields
         *  1.3 allow some conditions
         * 2. sort
         * 3. set fields or excludes fields
         * 4. paging
         * 5. escape fields value
         */

        String objectCode = getPara(0, getPara("objectCode"));
        Integer pageIndex = getInt("p", getInt("pageIndex", 1));
        Integer pageSize = getInt("s", getInt("pageSize", 20));
        String fieldss = getPara("f") == null ? (getPara("fields") == null ? "" : getPara("fields")) : getPara("f");
        String excludeFieldss = getPara("ef") == null ? (getPara("exfields") == null ? "" : getPara("exfields")) : getPara("ef");

        String[] fields = fieldss.length() > 0 ? fieldss.split(",") : new String[0];
        String[] excludeFields = excludeFieldss.split(",");

        //resolve other params



        MetaObject metaObject = (MetaObject) Aop.get(DbMetaService.class).findByCode(objectCode);
        QueryCondition queryCondition = new QueryCondition();
        SqlParaExt sqlPara = queryCondition.resolve(this, metaObject, fields, excludeFields);
        Page<Record> result = Db.paginate(pageIndex, pageSize, sqlPara.getSelect(), sqlPara.getFrom() + sqlPara.getSqlExceptSelect(), sqlPara.getPara());
        renderJson(Ret.ok("data", result.getList()));
        return null;
    }
    /**
     * ef=id,name,config&f=config 会滤出全部列
     *
     */
}
