package com.github.md.web.file;

import com.github.md.web.kit.DateKit;
import com.google.common.io.Files;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

/**
 * <p> @Date : 2019/12/4 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
public interface UploadService {

    /**
     * 上传文件。当使用DbMeta基于元数据生成的文件上传控件时会调用此方法进行文件保存
     *
     * @param file         文件
     * @param splitMarkers 分隔标记，分隔目录。你可以依据用于文件存放管理
     * @return 返回文件地址, 可以是相对路径，也可以是绝对路径(或者http://xxx)。
     */
    String upload(MultipartFile file, String... splitMarkers);

    /**
     * 文件名转换。提供上传的原始文件，返回存储的文件名，为了防止文件重复。采用添加后缀：yyyyMMdd_HH_mm_ss_SSS。如:
     *
     * <pre>
     * filename  abcd.txt
     * newname   abcd_yyyy-MM-dd_HH:mm:ssSSS.txt
     * </pre>
     *
     * @param file 上传的原始文件
     * @return 带有时间格式后缀的文件名
     */
    default String getFileNameWithAffix(MultipartFile file) {
        return Files.getNameWithoutExtension(file.getOriginalFilename()) + "_" + DateKit.toStr(new Date(), "yyyyMMdd_HH_mm_ss_SSS") + "."
                + Files.getFileExtension(file.getOriginalFilename());
    }
}