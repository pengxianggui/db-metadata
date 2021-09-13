package com.github.md.web.upload;

import com.alibaba.fastjson.JSON;
import lombok.Getter;

import java.util.List;

/**
 * <p> @Date : 2021/9/13 </p>
 * <p> @Project : db-metadata-server-springboot</p>
 *
 * <p> @author konbluesky </p>
 */
public class UploadFileResolve {

    @Getter
    private final List<UploadFile> files;

    public UploadFileResolve(String fileJsonData) {
        this.files = JSON.parseArray(fileJsonData, UploadFile.class);
    }

    public boolean hasFile() {
        return files != null && !files.isEmpty();
    }

    public boolean onlyOne() {
        return files.size() == 1;
    }

    public UploadFile getDefaultSeat() {
        return files.stream().filter(f -> f.getSeat().equalsIgnoreCase("") || f.getSeat().equalsIgnoreCase("default")).findFirst().get();
    }

}
