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
public class StrUploadFileResolve implements UploadFileResolve {

    private List<UploadFile> files;

    /**
     * 传入字段值，解析文件
     *
     * @param value
     */
    public StrUploadFileResolve(String value) {
        this.files = new ArrayList<>();
        if (StrKit.notBlank(value)) {
            UploadFile file = new UploadFile();
            file.setName("");
            file.setSeat("");
            file.setUrl(value);
            this.files.add(file);
        }
    }

    @Override
    public boolean hasFile() {
        return !this.files.isEmpty();
    }

    @Override
    public boolean onlyOne() {
        return this.files.size() == 1;
    }

    @Override
    public List<UploadFile> getFiles() {
        return files;
    }
}
