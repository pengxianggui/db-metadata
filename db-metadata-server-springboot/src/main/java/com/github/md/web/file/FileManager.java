package com.github.md.web.file;

import com.github.md.analysis.AnalysisSpringUtil;
import com.github.md.web.DbMetaConfigException;
import com.github.md.web.DbMetaConfigurer;
import com.github.md.web.ServiceManager;
import com.github.md.web.config.MetaProperties;
import com.github.md.web.file.local.LocalFileService;
import com.github.md.web.kit.AssertKit;
import com.jfinal.kit.StrKit;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

/**
 * @author pengxg
 * @date 2022/4/25 9:41 上午
 */
@Slf4j
public class FileManager {
    private static FileManager me = null;

    private final Map<String, UploadService> registeredUploadService = new HashMap<>();
    private final Map<String, DownloadService> registeredDownloadService = new HashMap<>();
    private final String defaultMode;

    public static FileManager me() {
        if (me == null) {
            synchronized (FileManager.class) {
                if (me == null) {
                    FileConfigurer configurer = AnalysisSpringUtil.getBean(FileConfigurer.class);
                    config(configurer);
                }
            }
        }
        return me;
    }

    public static void config(FileConfigurer configurer) {
        me = new FileManager(configurer);
    }

    private FileManager(FileConfigurer configurer) {
        MetaProperties metaProperties = ServiceManager.getAppProperties();
        defaultMode = StrKit.defaultIfBlank(metaProperties.getServer().getUpload().getMode(), LocalFileService.modeName); // 未配置，就采用local——本地上传模式

        configDefault(metaProperties.getServer().getUpload());

        FileRegistry fileRegistry = new FileRegistry();
        configurer.configFileService(fileRegistry);

        for (Map.Entry<String, UploadService> entry : fileRegistry.getRegisteredUploadService().entrySet()) {
            config(entry.getKey(), entry.getValue());
        }
        for (Map.Entry<String, DownloadService> entry : fileRegistry.getRegisteredDownloadService().entrySet()) {
            config(entry.getKey(), entry.getValue());
        }
    }

    /**
     * 配置默认的上传/下载模式——本地上传下载模式。详见{@link LocalFileService}
     *
     * @param properties
     */
    private void configDefault(MetaProperties.UploadProperties properties) {
        // 配置内置的文件服务
        LocalFileService localFileService = new LocalFileService(properties.getLocal());
        config(LocalFileService.modeName, (UploadService) localFileService);
        config(LocalFileService.modeName, (DownloadService) localFileService);
    }

    private void config(String mode, UploadService uploadService) {
        AssertKit.isTrue(StrKit.notBlank(mode), "请指定mode！");
        AssertKit.isTrue(uploadService != null, "uploadService: 不能为null! mode: %s", mode);

        if (registeredUploadService.containsKey(mode)) {
            log.warn("上传模式编码重复！已经存在mode为%s的上传服务:{}! 当前注册: {}。将覆盖之前的上传服务",
                    registeredUploadService.get(mode).getClass().getName(), uploadService.getClass().getName());
            return;
        }

        registeredUploadService.put(mode, uploadService);
        log.debug("文件上传服务配置成功, mode={}, uploadService={}", mode, uploadService.getClass().getTypeName());
    }

    private void config(String mode, DownloadService downloadService) {
        AssertKit.isTrue(StrKit.notBlank(mode), "请指定mode！");
        AssertKit.isTrue(downloadService != null, "uploadService不能为null! mode:%s", mode);

        if (registeredDownloadService.containsKey(mode)) {
            log.warn("上传模式编码重复！已经存在mode为%s的下载服务:{}! 当前注册: {}。将覆盖之前的下载服务",
                    registeredDownloadService.get(mode).getClass().getName(), downloadService.getClass().getName());
        }

        registeredDownloadService.put(mode, downloadService);
        log.debug("文件下载服务配置成功, mode={}, uploadService={}", mode, downloadService.getClass().getTypeName());
    }

    /**
     * 依据系统启用的默认上传模式，获取上传服务
     *
     * @return
     */
    public UploadService getUploadService() {
        return getUploadService(defaultMode);
    }

    /**
     * 获取指定模式的上传服务
     *
     * @param mode
     * @return
     */
    public UploadService getUploadService(String mode) {
        AssertKit.isTrue(registeredUploadService.containsKey(mode), new DbMetaConfigException(String.format("未配置model为%s的上传服务！", mode)));
        return registeredUploadService.get(mode);
    }

    /**
     * 获取默认模式的下载服务
     *
     * @return
     */
    public DownloadService getDownloadService() {
        return getDownloadService(defaultMode);
    }

    /**
     * 获取指定模式的下载服务
     *
     * @return
     */
    public DownloadService getDownloadService(String mode) {
        AssertKit.isTrue(registeredDownloadService.containsKey(mode), new DbMetaConfigException(String.format("未配置mode为%s的下载服务!", mode)));
        return registeredDownloadService.get(mode);
    }

//    /**
//     * TODO 不同元对象可单独指定上传模式。 需要在元对象配置界面中可配
//     *
//     * @param metaObject
//     * @return
//     */
//    public UploadService getUploadService(IMetaObject metaObject) {
//        throw new NotFinishException("未完成！");
//    }
}
