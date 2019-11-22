package com.hthjsj.web.controller;

import com.hthjsj.analysis.meta.IMetaField;
import com.hthjsj.analysis.meta.MetaFieldConfigWrapper;
import com.hthjsj.web.ServiceManager;
import com.hthjsj.web.WebException;
import com.hthjsj.web.query.QueryHelper;
import com.hthjsj.web.ui.OptionsKit;
import com.jfinal.core.Controller;
import com.jfinal.kit.Kv;
import com.jfinal.kit.Ret;

import java.util.List;

/**
 * <p> @Date : 2019/11/18 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
public class OptionsController extends Controller {

    public void index() {
        QueryHelper queryHelper = new QueryHelper(this);
        String objectCode = queryHelper.getObjectCode();
        String fieldCode = queryHelper.getFieldCode();

        IMetaField metaField = ServiceManager.metaService().findFieldByCode(objectCode, fieldCode);

        MetaFieldConfigWrapper metaFieldConfigWrapper = new MetaFieldConfigWrapper(metaField.config());

        if (!metaFieldConfigWrapper.hasTranslation()) {
            throw new WebException("[%s]元对象的[%s]元字段未配置转义逻辑", objectCode, fieldCode);
        }
        if (metaFieldConfigWrapper.isSql()) {
            List<Kv> options = OptionsKit.transKeyValueBySql(metaFieldConfigWrapper.scopeSql());
            renderJson(Ret.ok("data", options));
            return;
        }
        if (metaFieldConfigWrapper.isOptions()) {
            renderJson(Ret.ok("data", metaFieldConfigWrapper.options()));
            return;
        }

        renderJson(Ret.ok());
    }
}
