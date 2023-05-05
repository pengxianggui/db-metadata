package com.github.md.web.file;

import lombok.Getter;

import java.util.List;

/**
 * 上传文件的解析器，它用于解析数据库表中文件字段，这些存储文件的字段一般有以下两种类型：
 * <p>
 * 1. 字段直接存储url。参见实现类: {@link StrUploadFileResolve}
 * <p>
 * 2. 字段存储json数组，参见实现类: {@link JsonUploadFileResolve}， 这种方式优于第1种，不仅可以存储url，还能保留文件名等信息，而且支持多个文件。
 *
 * @author pengxg
 * @date 2022/4/22 2:28 下午
 */
public abstract class UploadFileResolve {

    @Getter
    protected List<UploadFile> files;

    public UploadFileResolve(String columnValue) {
        this.files = parseToFiles(columnValue);
    }

    /**
     * 将数据库字段原始值解析为文件类型
     *
     * @param columnValue
     * @return
     */
    protected abstract List<UploadFile> parseToFiles(String columnValue);

    public boolean hasFile() {
        return files != null && !files.isEmpty();
    }

    public boolean onlyOne() {
        return files.size() == 1;
    }
}
