package com.hthjsj.web.controller;

import com.hthjsj.analysis.meta.IMetaField;
import com.hthjsj.analysis.meta.IMetaObject;
import com.hthjsj.analysis.meta.MetaFactory;
import com.hthjsj.web.ServiceManager;
import com.hthjsj.web.component.TableView;
import com.hthjsj.web.component.ViewFactory;
import com.hthjsj.web.query.QueryHelper;
import com.jfinal.core.Controller;
import com.jfinal.kit.Ret;

/**
 * <p> @Date : 2019/12/11 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
public class FindBoxController extends Controller {

    public void meta() {
        QueryHelper queryHelper = new QueryHelper(this);
        String objectCode = queryHelper.getObjectCode();
        String fieldCode = queryHelper.getFieldCode();
        IMetaField metaField = ServiceManager.metaService().findFieldByCode(objectCode, fieldCode);
        TableView tableView = null;
        if (metaField.configParser().isSql()) {
            String sql = metaField.configParser().scopeSql();
            IMetaObject metaObject = MetaFactory.createBySql(sql, objectCode);
            tableView = ViewFactory.tableView(metaObject);
//                    .dataUrl("/table/list/" + metaObject.code());
        }

        renderJson(Ret.ok("data", tableView.toKv()));
    }
}
