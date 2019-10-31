package com.hthjsj.web.controller;

import com.google.common.collect.Lists;
import com.hthjsj.analysis.meta.MetaObject;
import com.hthjsj.web.ServiceManager;
import com.hthjsj.web.query.QueryHelper;
import com.jfinal.kit.Kv;
import com.jfinal.kit.Ret;

import java.util.List;

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


        MetaObject metaObject = (MetaObject) ServiceManager.dbMetaService().findByCode(objectCode);
    }

    public void dict() {
        List<Kv> options = Lists.newArrayList();
        options.add(Kv.by("key", "男").set("value", "1"));
        options.add(Kv.by("key", "女").set("value", "2"));
        renderJson(Ret.ok("data", options));
    }
}
