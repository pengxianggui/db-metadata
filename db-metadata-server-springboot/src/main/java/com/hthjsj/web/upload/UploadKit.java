package com.hthjsj.web.upload;

import com.hthjsj.web.query.QueryBuilder;

/**
 * <p> @Date : 2019/12/5 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
public class UploadKit {

    public static String uploadUrl(String objectCode, String fieldCode) {
        QueryBuilder queryBuilder = new QueryBuilder();
        String params = queryBuilder.builder("objectCode", objectCode).builder("fieldCode", fieldCode).buildQueryString(true);
        return "/file/upload" + params;
    }

    public static String downloadUrl(String objectCode, String fieldCode, String id) {
        QueryBuilder queryBuilder = new QueryBuilder();
        String params = queryBuilder.builder("objectCode", objectCode).builder("fieldCode", fieldCode).builder("id", id).buildQueryString(true);
        return "/file/down" + params;
    }

    public static String previewUrl(String url) {
        return "/file/preview?path=" + url;
    }
}
