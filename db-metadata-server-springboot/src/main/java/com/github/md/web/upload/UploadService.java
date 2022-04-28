package com.github.md.web.upload;

import com.github.md.analysis.meta.IMetaField;
import com.github.md.web.kit.DateKit;
import com.google.common.io.Files;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.Date;
import java.util.List;

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
     * @param metaField 元字段。表明文件所属的元字段
     * @param file      文件
     * @return 返回文件地址, 可以是相对路径，也可以是绝对路径(或者http://xxx)。只要 {@link #getFile(String)} 能获取到即可。
     */
    String upload(IMetaField metaField, MultipartFile file);

    /**
     * 上传文件。
     *
     * @param file 文件
     * @return 返回文件地址, 可以是相对路径，也可以是绝对路径(或者http://xxx)。只要 {@link #getFile(String)} 能获取到即可。
     */
    String upload(MultipartFile file);

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

    /**
     * 获取文件。获取指定资源的文件。
     *
     * @param filePath 此入参值就是 {@link #upload(IMetaField, MultipartFile)} 的返回值， 它可能是一个本地相对路径，也可能是一个绝对路径(http://...)
     * @return
     */
    File getFile(String filePath);

    /**
     * 获取文件。 用于获取指定字段值中存储的文件。
     *
     * @param metaField    元字段。 指定字段对应的元字段
     * @param primaryValue 文件关联记录的主键值。值对应的记录主键。
     * @param fieldValue   该字段该记录的值。字段值，其中存储了文件地址信息。当fieldValue为json数组字符串时，可能存储多个文件内容。需要内部去判断。
     *                     当metaField字段类型为json时，fieldValue为json数组。
     * @return
     */
    List<File> getFile(IMetaField metaField, String primaryValue, String fieldValue);

    /**
     * 文件解析器
     *
     * @param metaField  元字段
     * @param fieldValue 数据库里存储的字段值
     * @return
     */
    default UploadFileResolve getFileResolver(IMetaField metaField, String fieldValue) {
        if (metaField.dbType().isJson()) {
            return new JsonUploadFileResolve(fieldValue);
        } else {
            return new StrUploadFileResolve(fieldValue);
        }
    }
}
