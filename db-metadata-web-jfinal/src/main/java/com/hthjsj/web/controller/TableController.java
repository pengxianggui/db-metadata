package com.hthjsj.web.controller;

import com.hthjsj.analysis.meta.DbMetaService;
import com.hthjsj.analysis.meta.MetaObject;
import com.hthjsj.web.component.TableView;
import com.jfinal.aop.Aop;
import com.jfinal.core.Controller;

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
public class TableController extends Controller {

    /**
     * param : objectCode
     */
    public void index() {
        String objectCode = getPara();
        DbMetaService dbMetaService = Aop.get(DbMetaService.class);
        MetaObject metaObject = (MetaObject) dbMetaService.findByCode(objectCode);
        TableView tableView = new TableView(metaObject);
        renderJson(tableView);
    }

}
