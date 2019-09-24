package com.hthjsj.web;

import com.hthjsj.analysis.meta.DbMetaService;
import com.hthjsj.analysis.meta.IMetaObject;
import com.jfinal.aop.Aop;
import com.jfinal.core.Controller;
import com.jfinal.kit.Ret;

/**
 * <p> Class title: </p>
 * <p> @Describe: </p>
 * <p> @Date : 2019/9/24 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
public class MetaController extends Controller {


    public void index() {
        String metaObjectCode = getPara(0);


        DbMetaService dbMetaService = Aop.get(DbMetaService.class);

        IMetaObject metaObject = dbMetaService.findByCode(metaObjectCode);


        renderJson(Ret.ok("data", metaObject));

    }


}
