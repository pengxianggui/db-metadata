package com.github.md.web.controller;

import com.github.md.analysis.kit.Kv;
import com.github.md.analysis.kit.Ret;
import com.github.md.analysis.meta.*;
import com.github.md.web.ServiceManager;
import com.github.md.web.component.SearchView;
import com.github.md.web.component.TableView;
import com.github.md.web.component.ViewFactory;
import com.github.md.web.kit.SqlParaExt;
import com.github.md.web.query.QueryConditionForMetaObject;
import com.github.md.web.query.QueryHelper;
import com.github.md.web.query.dynamic.CompileRuntime;
import com.github.md.web.ui.OptionsKit;
import com.github.md.web.user.auth.annotations.MetaAccess;
import com.github.md.web.user.auth.annotations.Type;
import com.google.common.base.Preconditions;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p> @Date : 2019/12/11 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
@RestController
@RequestMapping("find")
public class FindBoxController extends ControllerAdapter {

    @MetaAccess(value = Type.API_WITH_META_OBJECT)
    @GetMapping("meta")
    public Ret meta() {
        QueryHelper queryHelper = queryHelper();
        String objectCode = queryHelper.getObjectCode();
        String fieldCode = queryHelper.getFieldCode();
        IMetaField metaField = ServiceManager.metaService().findFieldByCode(objectCode, fieldCode);
        Preconditions.checkArgument(metaField.configParser().isSql(), "必须为该元子段配置数据源属性,指定sql");

        TableView tableView = null;
        SearchView searchView = null;
        String sql = metaField.configParser().scopeSql();
        IMetaObject metaObject = MetaFactory.createBySql(sql, objectCode);
        tableView = ViewFactory.tableView(metaObject);

        String url = CoreUrlBuilder.findBoxMetaUrl(objectCode, fieldCode);
        tableView.dataUrl(url);

        searchView = ViewFactory.searchView(metaObject);
        Kv result = Kv.by("table", tableView.toKv()).set("search", searchView.toKv());


        return Ret.ok("data", result);
    }

    @MetaAccess(value = Type.API_WITH_META_OBJECT)
    @GetMapping("list")
    public Object list() {
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
        QueryHelper queryHelper = queryHelper();
        String objectCode = queryHelper.getObjectCode();
        String fieldCode = queryHelper.getFieldCode();
        IMetaField metaField = ServiceManager.metaService().findFieldByCode(objectCode, fieldCode);


        boolean raw = parameterHelper().getParaToBoolean("raw", false);
        String[] fields = queryHelper.list().fields();
        String[] excludeFields = queryHelper.list().excludeFields();
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

        return renderJsonExcludes(Ret.ok("data", result.getList()).set("page", toPage(result.getTotalRow(), result.getPageNumber(), result.getPageSize())),
                                  excludeFields);
    }
}
