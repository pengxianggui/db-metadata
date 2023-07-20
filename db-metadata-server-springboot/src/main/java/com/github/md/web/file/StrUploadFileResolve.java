package com.github.md.web.file;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.net.url.UrlQuery;
import com.jfinal.kit.StrKit;

import java.nio.charset.Charset;
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
            String fileName;
            try {
                fileName = getNameFromUrl(columnValue);
            } catch (Exception e) {
                fileName = columnValue;
            }
            file.setName(fileName);
            file.setUrl(columnValue);
            file.setSeat("[]");
            files.add(file);
        }
        return files;
    }

    /**
     * 从url中提取文件名。url可能是：
     * <p>
     * 1. 以 /file 开头，则文件名存在于参数path值当中。此时url格式类似为: /file/**?path=xx%5Cyy%5Czz%5Cfile_name.pdf
     * <p>
     * 2. 其他情况，则文件名存在于url路径末尾。
     *
     * @param url
     * @return
     */
    private String getNameFromUrl(String url) {
        if (StrKit.isBlank(url)) {
            return "";
        }

        if (url.startsWith("/file")) {
            UrlQuery urlQuery = UrlQuery.of(url, Charset.defaultCharset());
            return FileUtil.getName((String) urlQuery.get("path"));
        }

        return FileUtil.getName(url);
    }


}
