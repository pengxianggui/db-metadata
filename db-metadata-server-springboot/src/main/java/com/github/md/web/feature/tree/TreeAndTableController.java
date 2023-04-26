package com.github.md.web.feature.tree;

import com.github.md.analysis.component.ComponentType;
import com.github.md.analysis.kit.Kv;
import com.github.md.analysis.kit.Ret;
import com.github.md.analysis.meta.*;
import com.github.md.analysis.meta.aop.*;
import com.github.md.web.ServiceManager;
import com.github.md.web.component.SearchView;
import com.github.md.web.component.TableView;
import com.github.md.web.component.ViewFactory;
import com.github.md.web.component.form.FormView;
import com.github.md.web.controller.ControllerAdapter;
import com.github.md.web.controller.ParameterHelper;
import com.github.md.web.event.EventKit;
import com.github.md.web.event.FormMessage;
import com.github.md.web.kit.SqlParaExt;
import com.github.md.web.kit.UtilKit;
import com.github.md.web.query.FormDataFactory;
import com.github.md.web.query.QueryConditionForMetaObject;
import com.github.md.web.query.QueryHelper;
import com.github.md.web.query.QueryUrlBuilder;
import com.github.md.web.query.dynamic.CompileRuntime;
import com.github.md.web.ui.ComponentInstanceConfig;
import com.github.md.web.ui.MetaObjectViewAdapter;
import com.github.md.web.ui.OptionsKit;
import com.github.md.web.ui.UIManager;
import com.github.md.web.user.auth.annotations.ApiType;
import com.github.md.web.user.auth.annotations.Type;
import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.IAtom;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;
import java.util.Collection;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * <p> @Date : 2020/9/10 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
@Slf4j
@RestController
@RequestMapping(value = {"feature/treeAndTable"})
public class TreeAndTableController extends ControllerAdapter {

    /**
     * 返回TreeAndTable 中meta数据
     * 1. 显式指定Tree组件的Data_Url
     * 2. 显式指定Table组件的DataUrl
     */
    @ApiType(value = Type.API_WITH_META_FEATURE)
    @GetMapping("meta")
    public Ret meta() {
        QueryHelper queryHelper = queryHelper();
        String featureCode = queryHelper.getFeatureCode();

        TreeAndTableConfig treeAndTableConfig = featureService().loadFeatureConfig(featureCode);
        String tableObjectCode = treeAndTableConfig.getTableConfig().getObjectCode();

        IMetaObject metaObject = metaService().findByCode(tableObjectCode);
        MetaObjectViewAdapter metaObjectTableViewAdapter = UIManager.getView(metaObject, ComponentType.TABLEVIEW);
        TableView tableView = (TableView) metaObjectTableViewAdapter.getComponent();
        tableView.dataUrl(FeatureTreeUrlBuilder.tableDataUrl(featureCode));

        Kv treeMeta = Kv.by("data_url", FeatureTreeUrlBuilder.treeDataUrl(featureCode, treeAndTableConfig.getTreeConfig().getObjectCode()));

        MetaObjectViewAdapter metaObjectSearchViewAdapter = UIManager.getView(metaObject, ComponentType.SEARCHVIEW);
        SearchView searchView = (SearchView) metaObjectSearchViewAdapter.getComponent();

        return (Ret.ok("data", Kv.create().set("table", tableView.toKv()).set("tree", treeMeta).set("search", searchView.toKv())));
    }

    @ApiType(value = Type.API_WITH_META_FEATURE)
    @GetMapping("toAdd")
    public Ret toAdd() {
        QueryHelper queryHelper = queryHelper();
        ParameterHelper parameterHelper = parameterHelper();
        String featureCode = queryHelper.getFeatureCode();
        TreeAndTableConfig treeAndTableConfig = featureService().loadFeatureConfig(featureCode);

        String instanceCode = treeAndTableConfig.getInstanceCodeInTable(ComponentType.FORMVIEW);
        ComponentInstanceConfig componentInstanceConfig = ServiceManager.componentService().loadObjectConfig(instanceCode);

        String foreignFieldCodeKey = treeAndTableConfig.getTableConfig().getForeignPrimaryKey();
        /** 优先通过ForeignFieldCodeKey取值 如无,再通过RELATE_ID_KEY  */
        String relateIdValue = parameterHelper.getPara(foreignFieldCodeKey, parameterHelper.getPara(TreeAndTableConfig.RELATE_ID_KEY, ""));
        Preconditions.checkArgument(StrKit.notBlank(relateIdValue), "树->表 关联ID[%s]丢失,请检查.", TreeAndTableConfig.RELATE_ID_KEY);

        /** 构建Table元对象 */
        IMetaObject metaObject = metaService().findByCode(treeAndTableConfig.getTableConfig().getObjectCode());

        /**
         * 没有设置foreignFieldCodeKey,意味着靠拦截器的方式干预查询和保存动作
         * RELATE_ID_KEY需要传递到表单中,通过构建一个虚拟字段来存放
         * */
        if (StrKit.isBlank(foreignFieldCodeKey)) {
            IMetaField virtualField = MetaFactory.createReadonlyMetaField(metaObject, TreeAndTableConfig.RELATE_ID_KEY, "外键字段", relateIdValue);
            metaObject.addField(virtualField);
        }

        QueryUrlBuilder queryUrlBuilder = queryHelper.queryBuilder();
        queryUrlBuilder.param("featureCode", featureCode);

        FormView formView = ViewFactory.formView(metaObject, componentInstanceConfig)
                .action("/feature/treeAndTable/doAdd" + queryUrlBuilder.toQueryString(true)).addForm();
        /** 公共逻辑: 获取请求中已挂的参数 */
        Kv preFillMetaFields = queryHelper.hasMetaParams(metaObject);

        /** 将关联RELATE_ID_KEY的value 获取到后,放入要预填充的字段map中 */
        if (StrKit.isBlank(foreignFieldCodeKey)) {
            preFillMetaFields.put(TreeAndTableConfig.RELATE_ID_KEY, relateIdValue);
        } else {
            preFillMetaFields.put(foreignFieldCodeKey, relateIdValue);
        }

        if (!preFillMetaFields.isEmpty()) {
            formView.buildChildren();
            preFillMetaFields.forEach((key, value) -> {
                formView.getField(String.valueOf(key)).defaultVal(value);
            });
        }

        return Ret.ok("data", formView.toKv());
    }

    @ApiType(value = Type.API_WITH_META_FEATURE)
    @PostMapping("doAdd")
    public Ret doAdd() {
        QueryHelper queryHelper = queryHelper();
        ParameterHelper parameterHelper = parameterHelper();
        String featureCode = queryHelper.getFeatureCode();
        TreeAndTableConfig treeAndTableConfig = featureService().loadFeatureConfig(featureCode);

        IMetaObject metaObject = metaService().findByCode(treeAndTableConfig.getTableConfig().getObjectCode());

        MetaData metadata = FormDataFactory.buildFormData(getRequest().getParameterMap(), metaObject, true);

        MetaObjectConfigParse metaObjectConfigParse = metaObject.configParser();
        AddPointCut addPointCut = (AddPointCut) treeAndTableConfig.getTreeFeatureIntercept().tableIntercept();
        /** 将TreeAndTable中拦截器取出合并到AddPointCut拦截器中 */
        AddPointCut[] pointCut = Lists.asList(addPointCut, metaObjectConfigParse.addPointCut()).toArray(new AddPointCut[0]);
        FormInvocation invocation = new FormInvocation(metaObject, parameterHelper.getKv(), getRequest(), metadata);

        boolean status = Db.tx(new IAtom() {

            @Override
            public boolean run() throws SQLException {
                boolean s = false;
                try {
                    PointCutChain.addBefore(pointCut, invocation);
                    s = metaService().saveData(invocation.getMetaObject(), invocation.getFormData());
                    invocation.setPreOperateStatus(s);
                    PointCutChain.addAfter(pointCut, invocation);
                } catch (Exception e) {
                    log.error("保存异常\n元对象:{},错误信息:{}", metaObject.code(), e.getMessage());
                    log.error(e.getMessage(), e);
                    invocation.getRet().setFail();
                    s = false;
                    throw e;
                }
                return s;
            }
        });

        if (status) {
            EventKit.post(FormMessage.AddMessage(invocation));
        }

        return invocation.getRet();
    }

    @ApiType(value = Type.API_WITH_META_FEATURE)
    @GetMapping("tableList")
    public Object tableList() {
        /**
         * 1. query data by metaObject
         *  [x] 1.1 query all data paging
         *  [x] 1.2 query data by fields
         *  [x-] 1.3 allow some conditions
         * [x] 2. sort
         * [x] 3. set fields or excludes fields
         * [x] 4. paging
         * 5. escape fields value
         * 6. supported alias columns;
         */
        QueryHelper queryHelper = queryHelper();

        String featureCode = queryHelper.getFeatureCode();
        TreeAndTableConfig treeAndTableConfig = featureService().loadFeatureConfig(featureCode);

        Integer pageIndex = queryHelper.getPageIndex();
        Integer pageSize = queryHelper.getPageSize();
        String[] fields = queryHelper.list().fields();
        String[] excludeFields = queryHelper.list().excludeFields();

        IMetaObject metaObject = metaService().findByCode(treeAndTableConfig.getTableConfig().getObjectCode());

        Collection<IMetaField> filteredFields = UtilKit.filter(fields, excludeFields, metaObject.fields());
        QueryConditionForMetaObject queryConditionForMetaObject = new QueryConditionForMetaObject(metaObject, filteredFields);
        SqlParaExt sqlPara = queryConditionForMetaObject.resolve(getRequest().getParameterMap(), fields, excludeFields);
        /** 编译where后条件 */
        String compileWhere = new CompileRuntime().compile(metaObject.configParser().where(), getRequest());

        /** pointCut构建 */
        TableQueryPointCut tableQueryPointCut = (TableQueryPointCut) treeAndTableConfig.getTreeFeatureIntercept().tableIntercept();
        TreeAndTableQueryInvocation treeAndTableQueryInvocation = new TreeAndTableQueryInvocation(metaObject, queryHelper);
        treeAndTableQueryInvocation.setSqlParaExt(sqlPara);
        treeAndTableQueryInvocation.setCompileWhere(compileWhere);
        treeAndTableQueryInvocation.setFilteredFields(filteredFields);
        treeAndTableQueryInvocation.setTreeAndTableConfig(treeAndTableConfig);

        Page<Record> result = null;
        if (tableQueryPointCut.prevent()) {
            result = tableQueryPointCut.getResult(treeAndTableQueryInvocation);
        } else {
            SqlParaExt pointCutSqlPara = (SqlParaExt) tableQueryPointCut.queryWrapper(treeAndTableQueryInvocation);
            /** 当拦截点未设置时,使用默认查询逻辑 */
            if (pointCutSqlPara == null) {
                result = metaService().paginate(pageIndex,
                        pageSize,
                        metaObject,
                        sqlPara.getSelect(),
                        MetaSqlKit.where(sqlPara.getSql(), compileWhere, metaObject.configParser().orderBy()),
                        sqlPara.getPara());
            } else {
                /** 拦截器干预后的逻辑 */
                result = metaService().paginate(pageIndex, pageSize, metaObject, pointCutSqlPara.getSelect(),
                        //                        pointCutSqlPara.getFromWhere(),
                        MetaSqlKit.where(pointCutSqlPara.getSql(), compileWhere, metaObject.configParser().orderBy()), pointCutSqlPara.getPara());
            }
        }

        result.setList(OptionsKit.trans(filteredFields, result.getList())); // 转义

        /**
         * 别名替换,参数中遇
         * a->b=123
         * c->d=123
         * 执行别名替换处理
         */
        Kv alias = Kv.create();
        AtomicBoolean hasAlias = new AtomicBoolean(false);
        UtilKit.toObjectFlat(getRequest().getParameterMap()).forEach((key, value) -> {
            if (key.contains("->")) {
                String[] ss = key.split("->");
                alias.set(ss[0], ss[1]);
                hasAlias.set(true);
            }
        });
        if (hasAlias.get()) {
            result.setList(UtilKit.aliasList(result.getList(), alias));
        }
        return renderJsonExcludes(Ret.ok("data", result.getList()).set("page", toPage(result.getTotalRow(), result.getPageNumber(), result.getPageSize())),
                excludeFields);
    }
}
