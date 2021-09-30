package com.github.md.web.upload;

import com.github.md.web.query.QueryUrlBuilder;
import org.springframework.boot.system.ApplicationHome;

import java.io.File;
import java.nio.file.Paths;

/**
 * <p> @Date : 2019/12/5 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
public class UploadKit {

    public static File getUploadDir() {
        ApplicationHome applicationHome = new ApplicationHome();
        File uploadDir = Paths.get(applicationHome.getDir().toString(), "upload").toFile();
        if (uploadDir.exists()) {
            return uploadDir;
        }
        uploadDir.mkdir();
        return uploadDir;
    }

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

    public static String previewUrl(String url) {
        return "/file/preview?path=" + url;
    }
}
