package com.hthjsj.web.controller;

import com.google.common.base.Preconditions;
import com.google.common.base.Splitter;
import com.hthjsj.analysis.meta.IMetaField;
import com.hthjsj.analysis.meta.IMetaObject;
import com.hthjsj.analysis.meta.MetaFactory;
import com.hthjsj.analysis.meta.MetaSqlKit;
import com.hthjsj.web.ServiceManager;
import com.hthjsj.web.component.SearchView;
import com.hthjsj.web.component.TableView;
import com.hthjsj.web.component.ViewFactory;
import com.hthjsj.web.jfinal.HttpRequestHolder;
import com.hthjsj.web.jfinal.SqlParaExt;
import com.hthjsj.web.query.QueryConditionForMetaObject;
import com.hthjsj.web.query.QueryHelper;
import com.hthjsj.web.query.dynamic.CompileRuntime;
import com.hthjsj.web.ui.OptionsKit;
import com.jfinal.aop.Before;
import com.jfinal.kit.Kv;
import com.jfinal.kit.Ret;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;

/**
 * <p> @Date : 2019/12/11 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
public class FindBoxController extends FrontRestController {

    /**
     * TODO 调试用
     */
    public void immediate() {
        String sql = getPara("sql");
        Preconditions.checkNotNull(sql, "sql条件不能为空");
        TableView tableView = null;
        SearchView searchView = null;
        String alignName = sql.replaceFirst("select ", "").replaceAll("from.*", "").replaceAll("\\s|\\,", "_");
        IMetaObject metaObject = MetaFactory.createBySql(sql, alignName);
        tableView = ViewFactory.tableView(metaObject);
        searchView = ViewFactory.searchView(metaObject);
        Kv result = Kv.create();
        result.set("table", tableView.toKv());
        result.set("search", searchView.toKv());
        renderJson(Ret.ok("data", result));
    }

    public void meta() {
        QueryHelper queryHelper = new QueryHelper(this);
        String objectCode = queryHelper.getObjectCode();
        String fieldCode = queryHelper.getFieldCode();
        IMetaField metaField = ServiceManager.metaService().findFieldByCode(objectCode, fieldCode);
        TableView tableView = null;
        SearchView searchView = null;
        if (metaField.configParser().isSql()) {
            String sql = metaField.configParser().scopeSql();
            IMetaObject metaObject = MetaFactory.createBySql(sql, objectCode);
            tableView = ViewFactory.tableView(metaObject);
            // url : /find/meta/?objectCode=aaa&fieldCode=111
            String url = "/find/list/" + queryHelper.builder("objectCode", objectCode).builder("fieldCode", fieldCode).buildQueryString(true);
            tableView.dataUrl(url);
            searchView = ViewFactory.searchView(metaObject);
        }
        Kv result = Kv.create();
        result.set("table", tableView.toKv());
        result.set("search", searchView.toKv());


        renderJson(Ret.ok("data", result));
    }

    @Override
    @Before(HttpRequestHolder.class)//OptionKit.trans->compileRuntime->需要从request中获取user对象;
    public void list() {
        /**
         * 1. query data by metaObject
         *  [x] 1.1 query all data paging
         *  [x] 1.2 query data by fields
         *  [x-] 1.3 allow some conditions
         * [x] 2. sort
         * [x] 3. set fields or excludes fields
         * [x] 4. paging
         * 5. escape fields value
         */
        QueryHelper queryHelper = new QueryHelper(this);
        String objectCode = queryHelper.getObjectCode();
        String fieldCode = queryHelper.getFieldCode();
        IMetaField metaField = ServiceManager.metaService().findFieldByCode(objectCode, fieldCode);

        String includeFieldStr = getPara("fs", getPara("fields", ""));
        String excludeFieldStr = getPara("efs", getPara("exfields", ""));
        boolean raw = getParaToBoolean("raw", false);
        String[] fields = Splitter.on(",").omitEmptyStrings().trimResults().splitToList(includeFieldStr).toArray(new String[0]);
        String[] excludeFields = Splitter.on(",").omitEmptyStrings().trimResults().splitToList(excludeFieldStr).toArray(new String[0]);
        IMetaObject metaObject = null;

        if (metaField.configParser().isSql()) {
            String sql = metaField.configParser().scopeSql();
            metaObject = MetaFactory.createBySql(sql, objectCode);
            metaObject.schemaName(metaField.configParser().dbConfig());
        }
        QueryConditionForMetaObject queryConditionForMetaObject = new QueryConditionForMetaObject(metaObject, null);
        SqlParaExt sqlPara = queryConditionForMetaObject.resolve(getRequest().getParameterMap(), fields, excludeFields);

        String compileWhere = new CompileRuntime().compile(metaObject.configParser().where(), getRequest());
        Page<Record> result = metaService().paginate(queryHelper.getPageIndex(),
                queryHelper.getPageSize(),
                metaObject,
                sqlPara.getSelect(),
                MetaSqlKit.where(sqlPara.getSql(), compileWhere, metaObject.configParser().orderBy()),
                sqlPara.getPara());

        /**
         * escape field value;
         * 1. 是否需要转义的规则;
         */
        if (!raw) {
            result.setList(OptionsKit.trans(metaObject.fields(), result.getList()));
        }

        renderJsonExcludes(Ret.ok("data", result.getList()).set("page", toPage(result.getTotalRow(), result.getPageNumber(), result.getPageSize())), excludeFields);
    }
}
