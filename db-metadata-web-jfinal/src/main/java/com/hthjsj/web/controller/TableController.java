package com.hthjsj.web.controller;

import com.google.common.base.MoreObjects;
import com.google.common.base.Preconditions;
import com.google.common.base.Splitter;
import com.hthjsj.analysis.meta.IMetaField;
import com.hthjsj.analysis.meta.IMetaObject;
import com.hthjsj.analysis.meta.MetaObjectConfigParse;
import com.hthjsj.analysis.meta.MetaSqlKit;
import com.hthjsj.analysis.meta.aop.AopInvocation;
import com.hthjsj.analysis.meta.aop.DeletePointCut;
import com.hthjsj.web.component.TableView;
import com.hthjsj.web.component.ViewFactory;
import com.hthjsj.web.jfinal.SqlParaExt;
import com.hthjsj.web.kit.UtilKit;
import com.hthjsj.web.query.QueryConditionForMetaObject;
import com.hthjsj.web.query.QueryHelper;
import com.hthjsj.web.ui.OptionsKit;
import com.jfinal.kit.Kv;
import com.jfinal.kit.Ret;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.IAtom;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import lombok.extern.slf4j.Slf4j;

import java.sql.SQLException;
import java.util.Collection;
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
public class TableController extends FrontRestController {

    @Override
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
         * 6. supported alias columns;
         */
        QueryHelper queryHelper = new QueryHelper(this);
        String objectCode = queryHelper.getObjectCode();
        Integer pageIndex = queryHelper.getPageIndex();
        Integer pageSize = queryHelper.getPageSize();

        String includeFieldStr = getPara("fs", getPara("fields", ""));
        String excludeFieldStr = getPara("efs", getPara("exfields", ""));
        boolean raw = getParaToBoolean("raw", false);
        String[] fields = Splitter.on(",").omitEmptyStrings().trimResults().splitToList(includeFieldStr).toArray(new String[0]);
        String[] excludeFields = Splitter.on(",").omitEmptyStrings().trimResults().splitToList(excludeFieldStr).toArray(new String[0]);

        IMetaObject metaObject = metaService().findByCode(objectCode);
        Collection<IMetaField> filteredFields = UtilKit.filter(fields, excludeFields, metaObject.fields());

        QueryConditionForMetaObject queryConditionForMetaObject = new QueryConditionForMetaObject(metaObject, filteredFields);
        SqlParaExt sqlPara = queryConditionForMetaObject.resolve(getRequest().getParameterMap(), fields, excludeFields);
        Page<Record> result = metaService().paginate(pageIndex,
                                                     pageSize,
                                                     metaObject,
                                                     sqlPara.getSelect(),
                                                     MetaSqlKit.where(sqlPara.getSql(), metaObject),
                                                     sqlPara.getPara());

        /**
         * escape field value;
         * 1. 是否需要转义的规则;
         */
        if (!raw) {
            result.setList(OptionsKit.trans(filteredFields, result.getList()));
        }

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

        renderJsonExcludes(Ret.ok("data", result.getList()).set("page", toPage(result.getTotalRow(), result.getPageNumber(), result.getPageSize())), excludeFields);
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
     * </pre>
     */
    @Override
    public void delete() {
        QueryHelper queryHelper = new QueryHelper(this);
        String objectCode = queryHelper.getObjectCode();

        IMetaObject metaObject = metaService().findByCode(objectCode);
        Object[] ids = queryHelper.getPks(metaObject);

        Preconditions.checkArgument(ids.length != 0, "无效的数据id:[%s]", MoreObjects.toStringHelper(ids));

        MetaObjectConfigParse metaObjectConfigParse = metaObject.configParser();
        DeletePointCut pointCut = metaObjectConfigParse.deletePointCut();
        AopInvocation invocation = new AopInvocation(metaObject, getKv());

        boolean status = Db.tx(new IAtom() {

            @Override
            public boolean run() throws SQLException {
                boolean s = false;
                try {
                    pointCut.deleteBefore(invocation);
                    s = metaService().deleteDatas(metaObject, ids);
                    pointCut.deleteAfter(s, invocation);
                } catch (Exception e) {
                    log.error("删除异常\n元对象:{},错误信息:{}", metaObject.code(), e.getMessage());
                    log.error(e.getMessage(), e);
                    s = false;
                }
                return s;
            }
        });

        renderJson(status ? Ret.ok() : Ret.fail());
    }
}
