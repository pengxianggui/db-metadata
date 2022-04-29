package com.github.md.web.file;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * 将数据库字段值作为JSON数组，解析文件
 * <p> @Date : 2021/9/13 </p>
 * <p> @Project : db-metadata-server-springboot</p>
 *
 * <p> @author konbluesky </p>
 */
@Slf4j
public class JsonUploadFileResolve implements UploadFileResolve {

    @Getter
    private List<UploadFile> files;

    public JsonUploadFileResolve(String fileJsonData) {
        try {
            this.files = JSON.parseArray(fileJsonData, UploadFile.class);
        } catch (Exception e) {
            this.files = Lists.newArrayList();
            log.error(e.getMessage(), e.getCause());
        }
    }

    @Override
    public boolean hasFile() {
        return files != null && !files.isEmpty();
    }

    public boolean onlyOne() {
        return files.size() == 1;
    }

    @Deprecated
    public UploadFile getDefaultSeat() {
        return files.stream().filter(f -> f.getSeat().equalsIgnoreCase("") || f.getSeat().equalsIgnoreCase("default")).findFirst().get();
    }

}
