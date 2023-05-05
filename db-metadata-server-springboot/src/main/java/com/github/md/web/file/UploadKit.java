package com.github.md.web.file;

import com.github.md.web.query.QueryUrlBuilder;

/**
 * <p> @Date : 2019/12/5 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
public class UploadKit {

    /**
     * 组装上传控件的上传url
     *
     * @param objectCode
     * @param fieldCode
     * @return
     */
    public static String uploadUrl(String objectCode, String fieldCode) {
        QueryUrlBuilder queryUrlBuilder = new QueryUrlBuilder();
        String params = queryUrlBuilder.param("objectCode", objectCode).param("fieldCode", fieldCode).toQueryString(true);
        return "/file/upload" + params;
    }

    /**
     * 组装上传控件的下载url。todo 只是目前上传控件还没有提供下载交互功能，所以这个暂时没用
     *
     * @param objectCode
     * @param fieldCode
     * @param id
     * @return
     */
    public static String downloadUrl(String objectCode, String fieldCode, String id) {
        QueryUrlBuilder queryUrlBuilder = new QueryUrlBuilder();
        String params = queryUrlBuilder.param("objectCode", objectCode).param("fieldCode", fieldCode).param("id", id).toQueryString(true);
        return "/file/down" + params;
    }
}
