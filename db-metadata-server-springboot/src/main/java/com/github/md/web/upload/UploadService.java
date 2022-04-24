package com.github.md.web.upload;

import com.github.md.analysis.meta.IMetaField;

import java.io.File;
import java.util.List;

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
     * @param metaField 元字段。表明文件所属的元字段
     * @param file      文件
     * @return 返回文件地址, 可以是相对路径，也可以是绝对路径(或者http://xxx)。只要 {@link #getFile(String)} 能获取到即可。
     */
    String upload(IMetaField metaField, File file);

    /**
     * 上传文件
     *
     * @param file 文件
     * @return 返回文件地址, 可以是相对路径，也可以是绝对路径(或者http://xxx)。只要 {@link #getFile(String)} 能获取到即可。
     */
    String upload(File file);

    /**
     * 获取文件。获取指定资源的文件。
     *
     * @param filePath 此入参值就是 {@link #upload(IMetaField, File)} 的返回值， 它可能是一个本地相对路径，也可能是一个绝对路径(http://...)
     * @return
     */
    File getFile(String filePath);

    /**
     * 获取文件。 用于获取指定元字段指定名
     *
     * @param metaField    元字段
     * @param primaryValue 文件关联记录的主键值
     * @param fieldValue   该字段该记录的值
     * @return
     */
    List<File> getFile(IMetaField metaField, String primaryValue, String fieldValue);

    /**
     * base路径
     *
     * @return
     */
    String getBasePath();

    /**
     * 文件解析器
     *
     * @param fieldValue 数据库里存储的字段值
     * @return
     */
    UploadFileResolve getFileResovler(IMetaField metaField, String fieldValue);
}
