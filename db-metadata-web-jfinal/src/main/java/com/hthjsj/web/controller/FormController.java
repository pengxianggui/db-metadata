package com.hthjsj.web.controller;

import com.hthjsj.analysis.meta.DbMetaService;
import com.hthjsj.analysis.meta.IMetaObject;
import com.hthjsj.web.query.QueryHelper;
import com.jfinal.aop.Aop;

/**
 * <p> @Date : 2019/10/16 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
public class FormController extends FrontRestController {

    @Override
    public void index() {
    }

    @Override
    public void toAdd() {
        QueryHelper queryHelper = new QueryHelper(this);
        String objectCode = queryHelper.getObjectCode();

        IMetaObject metaObject = Aop.get(DbMetaService.class).findByCode(objectCode);
    }
}
