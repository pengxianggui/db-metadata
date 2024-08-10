package com.github.md.web.controller;

import cn.hutool.core.net.url.UrlBuilder;
import cn.hutool.core.net.url.UrlPath;
import cn.hutool.core.net.url.UrlQuery;
import com.github.md.analysis.kit.Kv;
import com.github.md.analysis.meta.IMetaObject;
import com.github.md.analysis.meta.MetaFieldConfigParse;
import com.github.md.web.component.TableView;
import com.github.md.web.query.QueryUrlBuilder;
import com.jfinal.kit.StrKit;

import java.nio.charset.Charset;

/**
 * System core URL description
 * <pre>
 * add("/db", DBController.class);
 * add("/meta", MetaController.class);
 * add("/component", ComponentController.class);
 * add("/component/options", OptionsController.class);
 * add("/form", FormController.class);
 * add("/dict", DictController.class);
 * add("/check", ValidatorController.class);
 * add("/table", TableController.class);
 * add("/find", FindBoxController.class);
 * </pre>
 * <p> @Date : 2021/9/30 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
public class CoreUrlBuilder {

    /**
     * example : /find/meta/?objectCode=aaa&fieldCode=111
     *
     * @param objectCode
     * @param fieldCode
     *
     * @return
     */
    @Deprecated
    public static String findBoxMetaUrl(String objectCode, String fieldCode) {
        QueryUrlBuilder queryUrlBuilder = new QueryUrlBuilder();
        queryUrlBuilder.base("/find/list");
        queryUrlBuilder.param("objectCode", objectCode);
        queryUrlBuilder.param("fieldCode", fieldCode);
        return queryUrlBuilder.toQueryString(true);
    }

    /**
     * 使用scopeMeta信息更新metaObject中的table_url
     * @param scopeMeta 参考 {@link MetaFieldConfigParse#scopeMeta()}
     * @return
     */
    public static String findBoxMetaUrl(Kv scopeMeta) {
        String objectCode = scopeMeta.getStr("objectCode");
        String fs = scopeMeta.getStr("fs");
        String where = scopeMeta.getStr("where");
        QueryUrlBuilder queryUrlBuilder = new QueryUrlBuilder();
        queryUrlBuilder.base("/table/list");
        queryUrlBuilder.param("objectCode", objectCode);
        if (StrKit.notBlank(fs)) {
            queryUrlBuilder.param("fs", fs);
        }
        if (StrKit.notBlank(where)) {
            return queryUrlBuilder.toQueryString(true) + "&" + where;
        }
        return queryUrlBuilder.toQueryString(true);
    }
}
