package com.hthjsj.web.controller;

import com.hthjsj.analysis.meta.FieldConfigWrapper;
import com.hthjsj.analysis.meta.IMetaField;
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

        FieldConfigWrapper fieldConfigWrapper = new FieldConfigWrapper(metaField.config());

        if (!fieldConfigWrapper.hasTranslation()) {
            throw new WebException("[%s]元对象的[%s]元字段未配置转义逻辑", objectCode, fieldCode);
        }
        if (fieldConfigWrapper.isSql()) {
            List<Kv> options = OptionsKit.transKeyValueBySql(fieldConfigWrapper.sourceSql());
            renderJson(Ret.ok("data", options));
            return;
        }
        if (fieldConfigWrapper.isOptions()) {
            renderJson(Ret.ok("data", fieldConfigWrapper.options()));
            return;
        }

        renderJson(Ret.ok());
    }
}
