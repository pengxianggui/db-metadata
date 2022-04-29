package com.github.md.web.file;

import com.github.md.web.query.QueryUrlBuilder;

/**
 * <p> @Date : 2019/12/5 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
public class UploadKit {

    public static String uploadUrl(String objectCode, String fieldCode) {
        QueryUrlBuilder queryUrlBuilder = new QueryUrlBuilder();
        String params = queryUrlBuilder.param("objectCode", objectCode).param("fieldCode", fieldCode).toQueryString(true);
        return "/file/upload" + params;
    }

    public static String downloadUrl(String objectCode, String fieldCode, String id) {
        QueryUrlBuilder queryUrlBuilder = new QueryUrlBuilder();
        String params = queryUrlBuilder.param("objectCode", objectCode).param("fieldCode", fieldCode).param("id", id).toQueryString(true);
        return "/file/down" + params;
    }
}
