package com.hthjsj.web.controller;

import com.google.common.base.Splitter;
import com.hthjsj.analysis.meta.DbMetaService;
import com.hthjsj.analysis.meta.MetaObject;
import com.hthjsj.web.component.TableView;
import com.hthjsj.web.jfinal.SqlParaExt;
import com.hthjsj.web.query.QueryCondition;
import com.hthjsj.web.query.QueryHelper;
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

    @Override
    public void api() {
        renderJson("hehehehehehe");
    }

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
         *  [x] 1.1 query all data paging
         *  [x] 1.2 query data by fields
         *  [x-] 1.3 allow some conditions
         * 2. sort
         * 3. set fields or excludes fields
         * [x] 4. paging
         * 5. escape fields value
         */
        QueryHelper queryHelper = new QueryHelper(this);
        String objectCode = queryHelper.getObjectCode();
        Integer pageIndex = queryHelper.getPageIndex();
        Integer pageSize = queryHelper.getPageSize();

        String includeFieldStr = getPara("fs", getPara("fields", ""));
        String excludeFieldStr = getPara("efs", getPara("exfields", ""));
        String[] fields = Splitter.on(",").omitEmptyStrings().trimResults().splitToList(includeFieldStr).toArray(new String[0]);
        String[] excludeFields = Splitter.on(",").omitEmptyStrings().trimResults().splitToList(excludeFieldStr).toArray(new String[0]);

        MetaObject metaObject = (MetaObject) Aop.get(DbMetaService.class).findByCode(objectCode);
        QueryCondition queryCondition = new QueryCondition();
        SqlParaExt sqlPara = queryCondition.resolve(getRequest().getParameterMap(), metaObject, fields, excludeFields);
        Page<Record> result = Db.paginate(pageIndex, pageSize, sqlPara.getSelect(), sqlPara.getFromWhere(), sqlPara.getPara());

        renderJsonExcludes(Ret.ok("data", result.getList()), excludeFields);
        return null;
    }
}
