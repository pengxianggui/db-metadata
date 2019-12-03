package com.hthjsj.web.controller;

import com.google.common.base.Preconditions;
import com.google.common.base.Splitter;
import com.hthjsj.analysis.meta.MetaObject;
import com.hthjsj.analysis.meta.MetaObjectConfigParse;
import com.hthjsj.analysis.meta.aop.AopInvocation;
import com.hthjsj.analysis.meta.aop.DeletePointCut;
import com.hthjsj.web.component.TableView;
import com.hthjsj.web.component.ViewFactory;
import com.hthjsj.web.jfinal.SqlParaExt;
import com.hthjsj.web.query.QueryCondition;
import com.hthjsj.web.query.QueryHelper;
import com.hthjsj.web.ui.OptionsKit;
import com.jfinal.kit.Ret;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.IAtom;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import lombok.extern.slf4j.Slf4j;

import java.sql.SQLException;
import java.util.List;

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

    public void meta() {
        String objectCode = new QueryHelper(this).getObjectCode();
        MetaObject metaObject = (MetaObject) metaService().findByCode(objectCode);
        TableView tableView = ViewFactory.tableView(metaObject).dataUrl("/table/list/" + metaObject.code());
        renderJson(Ret.ok("data", tableView.toKv()));
    }

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

        MetaObject metaObject = (MetaObject) metaService().findByCode(objectCode);
        QueryCondition queryCondition = new QueryCondition();
        SqlParaExt sqlPara = queryCondition.resolve(getRequest().getParameterMap(), metaObject, fields, excludeFields);
        Page<Record> result = Db.paginate(pageIndex, pageSize, sqlPara.getSelect(), sqlPara.getFromWhere(), sqlPara.getPara());

        /**
         * escape field value;
         * 1. 是否需要转义的规则;
         */
        if (!raw) {
            result.setList(OptionsKit.trans(metaObject.fields(), result.getList()));
        }

        renderJsonExcludes(Ret.ok("data", result.getList()).set("page", toPage(result.getTotalRow(), result.getPageNumber(), result.getPageSize())), excludeFields);
    }

    @Override
    public void delete() {
        String objectCode = getPara("objectCode");
        String idss = getPara("ids");
        List<String> ids = Splitter.on(",").omitEmptyStrings().trimResults().splitToList(idss);
        Preconditions.checkArgument(!ids.isEmpty(), "无效的数据id:[%s]", ids);
        MetaObject metaObject = (MetaObject) metaService().findByCode(objectCode);

        MetaObjectConfigParse metaObjectConfigParse = metaObject.configParser();
        DeletePointCut pointCut = metaObjectConfigParse.interceptor();
        AopInvocation invocation = new AopInvocation(metaObject, getKv());

        boolean status = Db.tx(new IAtom() {

            @Override
            public boolean run() throws SQLException {
                boolean s = false;
                try {
                    pointCut.deleteBefore(invocation);
                    s = metaService().deleteData(metaObject, ids.toArray(new String[ids.size()]));
                    pointCut.deleteAfter(s, invocation);
                } catch (Exception e) {
                    log.error("删除异常\n元对象:{},错误信息:{}", metaObject.code(), e.getMessage());
                    s = false;
                }
                return s;
            }
        });

        renderJson(status ? Ret.ok() : Ret.fail());
    }
}
