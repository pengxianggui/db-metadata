package com.github.md.web.file;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;

/**
 * @author pengxg
 * @date 2022/4/29 12:38 下午
 */
public interface DownloadService extends Mode {

    /**
     * 获取文件。获取指定资源的文件。
     *
     * @param fileUrl 当此文件是通过{@link UploadService#upload(MultipartFile, String...)}上传的，那么此值通常为后者的返回值， 它可能是一个本地相对路径，也可能是一个绝对路径(http://...), 全由继承类去决定。
     * @return 返回文件对象
     */
    File getFile(String fileUrl);

}
