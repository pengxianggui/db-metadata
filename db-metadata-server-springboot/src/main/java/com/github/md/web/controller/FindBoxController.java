package com.github.md.web.controller;

import cn.hutool.core.lang.Assert;
import com.github.md.analysis.AnalysisSpringUtil;
import com.github.md.analysis.kit.Kv;
import com.github.md.analysis.meta.*;
import com.github.md.web.ex.WebException;
import com.github.md.web.kit.PageKit;
import com.github.md.web.res.Res;
import com.github.md.web.ServiceManager;
import com.github.md.web.component.SearchView;
import com.github.md.web.component.TableView;
import com.github.md.web.component.ViewFactory;
import com.github.md.web.kit.SqlParaExt;
import com.github.md.web.query.QueryConditionForMetaObject;
import com.github.md.web.query.QueryHelper;
import com.github.md.web.query.dynamic.CompileRuntime;
import com.github.md.web.ui.OptionsKit;
import com.github.md.web.user.auth.annotations.ApiType;
import com.github.md.web.user.auth.annotations.Type;
import com.google.common.base.Preconditions;
import com.jfinal.kit.StrKit;
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

    @ApiType(value = Type.API_WITH_META_OBJECT)
    @GetMapping("meta")
    public Res meta() {
        QueryHelper queryHelper = queryHelper();
        String objectCode = queryHelper.getObjectCode();
        String fieldCode = queryHelper.getFieldCode();
        IMetaField metaField = ServiceManager.metaService().findFieldByCode(objectCode, fieldCode);

        // 调整为: sql针对的是下拉, 元数据针对查找框
        Preconditions.checkArgument(metaField.configParser().isMeta(), "必须为该元字段配置数据源属性,指定meta信息");

        TableView tableView = null;
        SearchView searchView = null;
        Kv scopeMeta = metaField.configParser().scopeMeta();
        Assert.notBlank(scopeMeta.getStr("objectCode"), () -> new WebException("请检查原字段数据源配置中的meta信息, 必须指定一个有效的objectCode值!"));
        IMetaObject metaObject = AnalysisSpringUtil.getBean(DbMetaService.class).findByCode(scopeMeta.getStr("objectCode"));
        tableView = ViewFactory.tableView(metaObject);

        String url = CoreUrlBuilder.findBoxMetaUrl(scopeMeta);
        tableView.dataUrl(url);

        searchView = ViewFactory.searchView(metaObject);
        Kv result = Kv.by("table", tableView.toKv()).set("search", searchView.toKv());

        return Res.ok(result);
    }

    @Deprecated
    @ApiType(value = Type.API_WITH_META_OBJECT)
    @GetMapping("list")
    public Res list() {
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
            String dbConfig = StrKit.defaultIfBlank(metaField.configParser().dbConfig(), metaObject.schemaName());
            metaObject.schemaName(dbConfig);
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

        return Res.ok(renderJsonExcludes(PageKit.toPage(result), excludeFields));
    }
}
