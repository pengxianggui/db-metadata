package com.hthjsj.web.upload;

import com.hthjsj.web.query.QueryHelper;

/**
 * <p> @Date : 2019/12/5 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
public class UploadKit {

    public static String uploadUrl(String objectCode, String fieldCode) {
        QueryHelper queryHelper = QueryHelper.queryBuilder();
        String params = queryHelper.builder("objectCode", objectCode).builder("fieldCode", fieldCode).buildQueryString(true);
        return "/file/upload" + params;
    }

    public static String downloadUrl(String objectCode, String fieldCode, String id) {
        QueryHelper queryHelper = QueryHelper.queryBuilder();
        String params = queryHelper.builder("objectCode", objectCode).builder("fieldCode", fieldCode).builder("id", id).buildQueryString(true);
        return "/file/down" + params;
    }

    public static String previewUrl(String url) {
        return "/file/preview?path=" + url;
    }
}
