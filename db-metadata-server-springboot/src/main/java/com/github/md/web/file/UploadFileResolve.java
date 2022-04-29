package com.github.md.web.file;

import java.util.List;

/**
 * @author pengxg
 * @date 2022/4/22 2:28 下午
 */
public interface UploadFileResolve {

    boolean hasFile();

    boolean onlyOne();

    List<UploadFile> getFiles();
}
