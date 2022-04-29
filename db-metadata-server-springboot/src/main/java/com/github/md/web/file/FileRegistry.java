package com.github.md.web.file;

import lombok.AccessLevel;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

/**
 * @author pengxg
 * @date 2022/4/25 9:57 上午
 */
public final class FileRegistry {

    @Getter(AccessLevel.MODULE)
    private final Map<String, UploadService> registeredUploadService = new HashMap<>();

    @Getter(AccessLevel.MODULE)
    private final Map<String, DownloadService> registeredDownloadService = new HashMap<>();

    FileRegistry() {
    }

    /**
     * 配置指定默认的文件服务模式。
     *
     * @param mode
     * @param uploadService
     */
    public FileRegistry config(String mode, UploadService uploadService) {
        return config(mode, uploadService, null);
    }

    /**
     * 配置指定默认的文件服务模式。
     *
     * @param mode
     * @param uploadService
     * @param downloadService
     */
    public FileRegistry config(String mode, UploadService uploadService, DownloadService downloadService) {
        registeredUploadService.put(mode, uploadService);
        registeredDownloadService.put(mode, downloadService);
        return this;
    }
}
