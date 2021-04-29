package com.hthjsj.web.upload;

import java.io.File;

/**
 * <p> @Date : 2019/12/4 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
public interface UploadService {

    /**
     * 上传文件
     *
     * @param file         文件
     * @param splitMarkers 分隔标记，分隔目录
     * @return 返回文件地址, 可以是相对路径，也可以是绝对路径(或者http://xxx)。只要 {@link #getFile(String)} 能获取到即可。
     */
    String upload(File file, String... splitMarkers);

    /**
     * 获取文件。
     *
     * @param filePath 此入参值就是 {@link #upload(File, String...)} 的返回值， 它可能是一个本地相对路径，也可能是一个绝对路径(http://...)
     * @return
     */
    File getFile(String filePath);
}
