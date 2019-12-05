package com.hthjsj.web.upload;

import javax.servlet.http.HttpServletRequest;
import java.io.File;

/**
 * <p> @Date : 2019/12/4 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
public interface UploadService {

    File upload(File file, String... splitMarkers);

    String getBasePath();

    File getFile(String filePath);

    String downloadUrl(HttpServletRequest request, String objectCode, String fieldCode);

    String uploadUrl(HttpServletRequest request, String objectCode, String fieldCode);
}
