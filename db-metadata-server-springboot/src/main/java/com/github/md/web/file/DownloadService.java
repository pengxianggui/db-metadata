package com.github.md.web.file;

import com.github.md.analysis.meta.IMetaField;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author pengxg
 * @date 2022/4/29 12:38 下午
 */
public interface DownloadService {

    /**
     * 获取文件。获取指定资源的文件。
     *
     * @param filePath 当此文件路径对应的文件是通过{@link UploadService#upload(MultipartFile, String...)}上传的，那么此值通常为后者的返回值， 它可能是一个本地相对路径，也可能是一个绝对路径(http://...), 全由继承类去决定。
     * @return
     */
    File getFile(String filePath);

    /**
     * 根据元字段获取文件。 用于获取指定字段值中存储的所有文件，当需要通过元字段进行下载时会有用。
     *
     * @param metaField    元字段。 指定字段对应的元字段
     * @param primaryValue 文件关联记录的主键值。值对应的记录主键。
     * @param fieldValue   该字段该记录的值。字段值，其中存储了文件地址信息。当fieldValue为json数组字符串时，可能存储多个文件内容。需要内部去判断。
     *                     当metaField字段类型为json时，fieldValue为json数组。
     * @return
     */
    default List<File> getFile(IMetaField metaField, String primaryValue, String fieldValue) {
        UploadFileResolve uploadFileResolve = getFileResolver(metaField, fieldValue);
        String objectCode = metaField.objectCode(),
                fieldCode = metaField.fieldCode();
        if (uploadFileResolve.hasFile()) {
            return uploadFileResolve.getFiles().stream()
                    .map(f -> getFile(Paths.get(objectCode, fieldCode, f.getUploadedName()).toString()))
                    .collect(Collectors.toList());
        }
        return new ArrayList<>();
    }

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
