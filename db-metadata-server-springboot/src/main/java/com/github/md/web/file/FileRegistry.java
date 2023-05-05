package com.github.md.web.file;

import com.github.md.web.kit.AssertKit;
import com.jfinal.kit.StrKit;
import lombok.AccessLevel;
import lombok.Getter;
import org.springframework.util.StringUtils;

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
     * 配置指定默认的上传服务模式。
     *
     * @param mode
     * @param uploadService
     */
    public FileRegistry config(String mode, UploadService uploadService) {
        AssertKit.isTrue(StrKit.notBlank(mode), "文件服务模式为空, mode:%s", mode);
        AssertKit.isTrue(uploadService != null, "上传服务对象为空, mode:%s", mode);
        registeredUploadService.put(mode, uploadService);
        return this;
    }

    /**
     * 配置指定默认的下载服务模式。
     *
     * @param mode
     * @param downloadService
     * @return
     */
    public FileRegistry config(String mode, DownloadService downloadService) {
        AssertKit.isTrue(StrKit.notBlank(mode), "文件服务模式为空, mode:%s", mode);
        AssertKit.isTrue(downloadService != null, "下载服务对象为空, mode:%s", mode);
        registeredDownloadService.put(mode, downloadService);
        return this;
    }

    /**
     * 配置指定默认的文件服务模式。
     *
     * @param mode
     * @param uploadService
     * @param downloadService
     */
    public FileRegistry config(String mode, UploadService uploadService, DownloadService downloadService) {
        AssertKit.isTrue(StrKit.notBlank(mode), "文件服务模式为空, mode:%s", mode);
        AssertKit.isTrue(uploadService != null, "上传服务对象为空, mode:%s", mode);
        AssertKit.isTrue(downloadService != null, "下载服务对象为空, mode:%s", mode);

        registeredUploadService.put(mode, uploadService);
        registeredDownloadService.put(mode, downloadService);
        return this;
    }
}
