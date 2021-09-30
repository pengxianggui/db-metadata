package com.github.md.web.controller;

import com.github.md.web.query.QueryUrlBuilder;

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
    public static String findBoxMetaUrl(String objectCode, String fieldCode) {
        QueryUrlBuilder queryUrlBuilder = new QueryUrlBuilder();
        queryUrlBuilder.base("/find/list");
        queryUrlBuilder.param("objectCode", objectCode);
        queryUrlBuilder.param("fieldCode", fieldCode);
        return queryUrlBuilder.toQueryString(true);
    }
}
