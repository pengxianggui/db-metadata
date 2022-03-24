package com.github.md.web.feature.tree;

import com.github.md.web.query.QueryUrlBuilder;

/**
 * <p> @Date : 2021/9/30 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
public class FeatureTreeUrlBuilder {

    public static String tableDataUrl(String featureCode) {
        QueryUrlBuilder queryUrlBuilder = new QueryUrlBuilder();
        queryUrlBuilder.base("/feature/treeAndTable/tableList");
        queryUrlBuilder.param("featureCode", featureCode);
        return queryUrlBuilder.toQueryString(true);
    }

    public static String treeDataUrl(String featureCode, String objectCode) {
        QueryUrlBuilder queryUrlBuilder = new QueryUrlBuilder();
        queryUrlBuilder.base("/feature/tree");
        queryUrlBuilder.param("featureCode", featureCode);
        queryUrlBuilder.param("objectCode", objectCode);
        return queryUrlBuilder.toQueryString(true);
    }
}
