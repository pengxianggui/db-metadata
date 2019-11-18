package com.hthjsj.web.controller;

import com.hthjsj.analysis.meta.MetaObject;
import com.hthjsj.web.Dicts;
import com.hthjsj.web.ServiceManager;
import com.hthjsj.web.query.QueryHelper;
import com.jfinal.kit.Ret;

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


        MetaObject metaObject = (MetaObject) ServiceManager.metaService().findByCode(objectCode);
    }

    public void sex() {
        renderJson(Ret.ok("data", Dicts.me().getGender()));
    }

    /**
     * yes or no
     */
    public void yn() {
        renderJson(Ret.ok("data", Dicts.me().getYesNo()));
    }
}
