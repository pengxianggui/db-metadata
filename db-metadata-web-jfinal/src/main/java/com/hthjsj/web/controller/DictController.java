package com.hthjsj.web.controller;

import com.hthjsj.analysis.meta.DbMetaService;
import com.hthjsj.analysis.meta.MetaObject;
import com.hthjsj.web.query.QueryHelper;
import com.jfinal.aop.Aop;

/**
 * <p> Class title: </p>
 * <p> @Describe: </p>
 * <p> @Date : 2019/10/15 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
public class DictController extends FrontRestController {

    @Override
    public void index() {
        QueryHelper queryHelper = new QueryHelper(this);
        String objectCode = queryHelper.getObjectCode();
        String fieldCode = queryHelper.getFieldCode();


        MetaObject metaObject = (MetaObject) Aop.get(DbMetaService.class).findByCode(objectCode);
    }
}
