package com.hthjsj.web.file;

import java.io.File;

/**
 * <p> @Date : 2019/12/4 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
public interface FileService {

    File upload(File file, String... splitMarkers);

    String getBasePath();

    File getFile(String filePath);
}
