package com.github.md.web.file;

import com.jfinal.kit.StrKit;

import java.util.ArrayList;
import java.util.List;

/**
 * 直接将数据库字段值作为url，解析文件
 *
 * @author pengxg
 * @date 2022/4/22 2:36 下午
 */
public class StrUploadFileResolve extends UploadFileResolve {

    /**
     * 传入字段值，解析文件
     *
     * @param columnValue
     */
    public StrUploadFileResolve(String columnValue) {
        super(columnValue);
    }

    @Override
    protected List<UploadFile> parseToFiles(String columnValue) {
        List<UploadFile> files = new ArrayList<>();
        if (StrKit.notBlank(columnValue)) {
            UploadFile file = new UploadFile();
            file.setName("");
            file.setSeat("");
            file.setUrl(columnValue);
            files.add(file);
        }
        return files;
    }
}
