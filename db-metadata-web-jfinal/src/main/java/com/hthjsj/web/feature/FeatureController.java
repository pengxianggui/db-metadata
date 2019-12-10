package com.hthjsj.web.feature;

import com.alibaba.fastjson.JSON;
import com.google.common.base.Preconditions;
import com.google.common.base.Splitter;
import com.hthjsj.web.UtilKit;
import com.hthjsj.web.controller.FrontRestController;
import com.hthjsj.web.feature.ms.MasterSlaveConfig;
import com.hthjsj.web.query.QueryHelper;
import com.jfinal.kit.Ret;

import java.util.List;

/**
 * @author konbluesky
 */
public class FeatureController extends FrontRestController {

    @Override
    public void doAdd() {
        QueryHelper queryHelper = new QueryHelper(this);
        FeatureType type = FeatureType.V(queryHelper.getFeatureType());
        Preconditions.checkArgument(type != FeatureType.UNKNOWN, "未知的功能模板" + queryHelper.getFeatureType());
        String name = getPara("name", "tname_test");
        String code = getPara("code", "tcode_test");
        String jsonstr = UtilKit.loadContentByFile("ms.json");
        featureService().createFeature(type, name, code, JSON.parseObject(jsonstr, MasterSlaveConfig.class));
        renderJson(Ret.ok());
    }

    /**
     * 功能 = ((元对象(表),元对象(视图),元对象(非表)) + 控件) * n
     */
    public void load() {

        QueryHelper queryHelper = new QueryHelper(this);
        String featureCode = queryHelper.getFeatureCode();
        Feature feature = featureService().loadFeatureConfig(featureCode);

        renderJson(Ret.ok("data", feature.execute()));
    }

    @Override
    public void delete() {
        String idss = getPara("ids");
        List<String> ids = Splitter.on(",").omitEmptyStrings().trimResults().splitToList(idss);
        boolean status = featureService().deleteFeature(ids.toArray(new String[0]));
        renderJson(status ? Ret.ok() : Ret.fail());
    }
}
