package com.github.md.web.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SimplePropertyPreFilter;
import com.github.md.analysis.kit.Kv;
import com.github.md.analysis.meta.IMetaField;
import com.github.md.analysis.meta.IMetaObject;
import com.github.md.analysis.meta.MetaObjectConfigParse;
import com.github.md.analysis.meta.MetaSqlKit;
import com.github.md.analysis.meta.aop.DeleteInvocation;
import com.github.md.analysis.meta.aop.DeletePointCut;
import com.github.md.analysis.meta.aop.PointCutChain;
import com.github.md.analysis.meta.aop.TableQueryPointCut;
import com.github.md.web.ServiceManager;
import com.github.md.web.config.json.RecordSerializer;
import com.github.md.web.kit.PageKit;
import com.github.md.web.kit.SqlParaExt;
import com.github.md.web.kit.UtilKit;
import com.github.md.web.kit.tree.TreeConfig;
import com.github.md.web.kit.tree.TreeKit;
import com.github.md.web.kit.tree.TreeNode;
import com.github.md.web.query.QueryConditionForMetaObject;
import com.github.md.web.query.QueryHelper;
import com.github.md.web.query.dynamic.CompileRuntime;
import com.github.md.web.res.Res;
import com.github.md.web.ui.OptionsKit;
import com.github.md.web.user.auth.annotations.ApiType;
import com.github.md.web.user.auth.annotations.Type;
import com.google.common.base.MoreObjects;
import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.IAtom;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

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
@Slf4j
@RestController
@RequestMapping("table")
public class TableController extends ControllerAdapter {

    @ApiType(value = Type.API_WITH_META_OBJECT)
    @GetMapping("all")
    public Res all() {
        QueryHelper queryHelper = queryHelper();
        String objectCode = queryHelper.getObjectCode();
        String[] fields = queryHelper.list().fields();
        String[] excludeFields = queryHelper.list().excludeFields();

        IMetaObject metaObject = metaService().findByCode(objectCode);

        Collection<IMetaField> filteredFields = UtilKit.filter(fields, excludeFields, metaObject.fields());
        QueryConditionForMetaObject queryConditionForMetaObject = new QueryConditionForMetaObject(metaObject, filteredFields);
        SqlParaExt sqlPara = queryConditionForMetaObject.resolve(getRequest().getParameterMap(), fields, excludeFields);
        /* 编译where后条件 */
        String compileWhere = new CompileRuntime().compile(metaObject.configParser().where(), getRequest());

        List<Record> result = businessService().findData(
                metaObject,
                sqlPara.getSelect(),
                MetaSqlKit.where(sqlPara.getSql(), compileWhere, metaObject.configParser().orderBy()),
                sqlPara.getPara());

        /*
         * escape field value;
         * 1. 是否需要默认转义的规则;
         */
        OptionsKit.trans(filteredFields, result);

        /*
         * 别名替换,参数中遇
         * a->b=123
         * c->d=123
         * 执行别名替换处理
         * TODO 废弃
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
            UtilKit.aliasList(result, alias);
        }

        // 序列化时排除
        SimplePropertyPreFilter filter = new SimplePropertyPreFilter();
        filter.getExcludes().addAll(Arrays.asList(excludeFields));
        RecordSerializer.setFilters(filter);

        return Res.ok(result);
    }

    @ApiType(value = Type.API_WITH_META_OBJECT)
    @GetMapping("list")
    public Res list() {
        /*
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
        String objectCode = queryHelper.getObjectCode();
        Integer pageIndex = queryHelper.getPageIndex();
        Integer pageSize = queryHelper.getPageSize();
        String[] fields = queryHelper.list().fields();
        String[] excludeFields = queryHelper.list().excludeFields();

        IMetaObject metaObject = metaService().findByCode(objectCode);

        Collection<IMetaField> filteredFields = UtilKit.filter(fields, excludeFields, metaObject.fields());
        QueryConditionForMetaObject queryConditionForMetaObject = new QueryConditionForMetaObject(metaObject, filteredFields);
        SqlParaExt sqlPara = queryConditionForMetaObject.resolve(getRequest().getParameterMap(), fields, excludeFields);
        /* 编译where后条件 */
        String compileWhere = new CompileRuntime().compile(metaObject.configParser().where(), getRequest());

        /* pointCut构建 */
        TableQueryPointCut tableQueryPointCut = metaObject.configParser().queryPointCut();
        TableQueryInvocation tableQueryInvocation = new TableQueryInvocation(metaObject, queryHelper);
        tableQueryInvocation.setSqlParaExt(sqlPara);
        tableQueryInvocation.setCompileWhere(compileWhere);
        tableQueryInvocation.setFilteredFields(filteredFields);

        PointCutChain.queryBefore(tableQueryInvocation, tableQueryPointCut); // 前置扩展

        Page<Record> result = null;
        if (tableQueryPointCut.prevent()) {
            result = tableQueryPointCut.getResult(tableQueryInvocation);
        } else {
            result = metaService().paginate(pageIndex,
                    pageSize,
                    metaObject,
                    sqlPara.getSelect(),
                    MetaSqlKit.where(sqlPara.getSql(), compileWhere, metaObject.configParser().orderBy()),
                    sqlPara.getPara());
        }

        // 后置扩展
        tableQueryInvocation.setData(result);
        PointCutChain.queryAfter(tableQueryInvocation, tableQueryPointCut);

        /*
         * escape field value;
         * 1. 是否需要默认转义的规则;
         */
        result.setList(OptionsKit.trans(filteredFields, result.getList()));

        /*
         * 别名替换,参数中遇
         * a->b=123
         * c->d=123
         * 执行别名替换处理
         * TODO 废弃
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

        // 序列化时排除
        SimplePropertyPreFilter filter = new SimplePropertyPreFilter();
        filter.getExcludes().addAll(Arrays.asList(excludeFields));
        RecordSerializer.setFilters(filter);

        return Res.ok(PageKit.toPage(result));
    }

    /**
     * <pre>
     * 删除逻辑:
     *      1.获取元对象
     *      2.拿到主键规则,策略
     *      3.从request中解析主键
     *      4.用service 根据业务主键删除业务数据
     * url样例:
     *      单主键:   http://localhost:8888/table/delete?id=1&id=2&id=3
     *      复合主键: http://localhost:8888/table/delete?id=pk1_v1,pk2_v2&id=pk1_v1,pk2_v2
     *
     * 树形删除逻辑:
     *     1. 先构建子树
     *     2. 遍历子树取id
     *     3. 构建Object[] ids 与普通原对象共用删除逻辑
     * </pre>
     */
    @ApiType(value = Type.API_WITH_META_OBJECT)
    @DeleteMapping("delete")
    public Res delete() {
        QueryHelper queryHelper = queryHelper();
        ParameterHelper parameterHelper = parameterHelper();
        String objectCode = queryHelper.getObjectCode();

        IMetaObject metaObject = metaService().findByCode(objectCode);
        Object[] ids;

        if (metaObject.configParser().isTreeStructure()) {
            Object[] idInParams = queryHelper.getPks(metaObject);
            boolean containsRoot = parameterHelper.getParaToBoolean("self", true);
            List<Object> idss = new ArrayList<>();

            // 指定删除节点的子节点，也需要删除
            for (Object idInParam : idInParams) {
                TreeConfig treeConfig = JSON.parseObject(metaObject.configParser().treeConfig(), TreeConfig.class);
                Preconditions.checkNotNull(treeConfig, "未找到[%s]对象的数据结构配置信息,请在[元对象配置]设置[数据结构->树形表]", metaObject.code());
                treeConfig.setRootIdentify(String.valueOf(((Object[]) idInParam)[0]));

                List<TreeNode<String, Record>> tree = treeService().tree(metaObject, treeConfig);
                if (containsRoot) {
                    idss.add(idInParam);
                }

                TreeKit.getChildIds(tree, idss);
            }

            ids = idss.toArray();
        } else {
            ids = queryHelper.getPks(metaObject);
        }

        Preconditions.checkArgument(ids.length != 0, "无效的数据id:[%s]", MoreObjects.toStringHelper(ids));

        MetaObjectConfigParse metaObjectConfigParse = metaObject.configParser();
        DeletePointCut[] pointCut = metaObjectConfigParse.deletePointCut();
        DeleteInvocation invocation = new DeleteInvocation(metaObject, parameterHelper.getKv(), getRequest(), ids);

        boolean status = Db.tx(new IAtom() {

            @Override
            public boolean run() throws SQLException {
                boolean s;
                try {
                    PointCutChain.deleteBefore(pointCut, invocation);
                    s = metaService().deleteDatas(metaObject, ids);
                    invocation.setPreOperateStatus(s);
                    PointCutChain.deleteAfter(pointCut, invocation);
                } catch (Exception e) {
                    log.error("删除异常\n元对象:{},错误信息:{}", metaObject.code(), e.getMessage());
                    if (log.isDebugEnabled()) {
                        log.error(e.getMessage(), e);
                    }
                    invocation.getContextParams().setEx(e);
                    s = false;
                }
                return s;
            }
        });

        if (status) {
            // TODO 发布删除事件
            return Res.ok();
        }

        return Res.fail(invocation.getContextParams().getEx());
    }

    /**
     * 树型数据
     * https://blog.csdn.net/u011627980/article/details/51454323?utm_medium=distribute.pc_relevant_t0.none-task-blog-BlogCommendFromMachineLearnPai2-1.channel_param&depth_1-utm_source=distribute.pc_relevant_t0.none-task-blog-BlogCommendFromMachineLearnPai2-1.channel_param
     */
    @ApiType(value = Type.API_WITH_META_OBJECT)
    @GetMapping("tree")
    public Res tree() {
        QueryHelper queryHelper = queryHelper();
        String objectCode = queryHelper.getObjectCode();
        IMetaObject metaObject = metaService().findByCode(objectCode);
        TreeConfig treeConfig = JSON.parseObject(metaObject.configParser().treeConfig(), TreeConfig.class);
        treeConfig.setOrderBy(metaObject.configParser().orderBy());

        Preconditions.checkNotNull(treeConfig, "未找到[%s]对象的数据结构配置信息,请在[元对象配置]设置[数据结构->树形表]", metaObject.code());
        String[] fields = queryHelper.list().fields();
        String[] excludeFields = queryHelper.list().excludeFields();

        Collection<IMetaField> filteredFields = UtilKit.filter(fields, excludeFields, metaObject.fields());

        QueryConditionForMetaObject queryConditionForMetaObject = new QueryConditionForMetaObject(metaObject, filteredFields);
        SqlParaExt sqlPara = queryConditionForMetaObject.resolve(getRequest().getParameterMap(), fields, excludeFields);

        String compileWhere = new CompileRuntime().compile(metaObject.configParser().where(), getRequest());
        List<Record> result = businessService().findData(metaObject,
                sqlPara.getSelect(),
                MetaSqlKit.where(sqlPara.getSql(), compileWhere, metaObject.configParser().orderBy()),
                sqlPara.getPara());

        List<TreeNode<String, Record>> tree = ServiceManager.treeService().treeByHitRecords(metaObject, result, treeConfig);

        return Res.ok(JSON.parseArray(JSON.toJSONString(tree, TreeKit.getAfterFilter(
                record -> OptionsKit.trans(filteredFields, Lists.newArrayList(record)) // 递归序列化时进行转义
        ))));
    }
}
