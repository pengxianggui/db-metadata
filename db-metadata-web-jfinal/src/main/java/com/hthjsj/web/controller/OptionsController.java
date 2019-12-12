package com.hthjsj.web.controller;

import com.hthjsj.analysis.meta.IMetaField;
import com.hthjsj.analysis.meta.IMetaObject;
import com.hthjsj.analysis.meta.MetaFieldConfigParse;
import com.hthjsj.web.ServiceManager;
import com.hthjsj.web.WebException;
import com.hthjsj.web.query.QueryHelper;
import com.hthjsj.web.ui.OptionsKit;
import com.jfinal.core.Controller;
import com.jfinal.kit.Kv;
import com.jfinal.kit.Ret;
import com.jfinal.kit.StrKit;

import java.util.List;

/**
 * <p> @Date : 2019/11/18 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
public class OptionsController extends Controller {

    /**
     * URL: /component/options/meta_object?f=field_code_abc
     */
    public void index() {
        QueryHelper queryHelper = new QueryHelper(this);
        String objectCode = queryHelper.getObjectCode();
        String fieldCode = queryHelper.getFieldCode();

        IMetaField metaField = ServiceManager.metaService().findFieldByCode(objectCode, fieldCode);

        MetaFieldConfigParse metaFieldConfigParse = metaField.configParser();

        if (!metaFieldConfigParse.hasTranslation()) {
            throw new WebException("[%s]元对象的[%s]元字段未配置转义逻辑", objectCode, fieldCode);
        }
        if (metaFieldConfigParse.isOptions()) {
            renderJson(Ret.ok("data", metaFieldConfigParse.options()));
            return;
        }
        if (metaFieldConfigParse.isSql()) {
            IMetaObject metaObject = metaField.getParent();
            String dbConfig = StrKit.defaultIfBlank(metaFieldConfigParse.dbConfig(), metaObject.schemaName());
            List<Kv> options = OptionsKit.transKeyValueBySql(metaFieldConfigParse.scopeSql(), dbConfig);
            renderJson(Ret.ok("data", options));
            return;
        }

        renderJson(Ret.ok());
    }
}
