package com.github.md.web.file;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

/**
 * 将数据库字段值作为JSON数组，解析文件。json格式为对象数组，其中的对象应当满足 {@link UploadFile} 格式
 * <p> @Date : 2021/9/13 </p>
 * <p> @Project : db-metadata-server-springboot</p>
 *
 * <p> @author konbluesky </p>
 */
@Slf4j
public class JsonUploadFileResolve extends UploadFileResolve {

    public JsonUploadFileResolve(String fileJsonData) {
        super(fileJsonData);
    }

    @Override
    protected List<UploadFile> parseToFiles(String columnValue) {
        try {
            return JSON.parseArray(columnValue, UploadFile.class);
        } catch (Exception e) {
            log.error(e.getMessage(), e.getCause());
            return new ArrayList<>();
        }
    }

    @Deprecated
    public UploadFile getDefaultSeat() {
        return files.stream().filter(f -> f.getSeat().equalsIgnoreCase("") || f.getSeat().equalsIgnoreCase("default")).findFirst().get();
    }

}
