package com.github.md.web.upload;

import cn.com.asoco.util.AssertUtil;
import com.github.md.analysis.AnalysisSpringUtil;
import com.github.md.analysis.meta.IMetaObject;
import com.github.md.web.DbMetaConfigException;
import com.github.md.web.DbMetaConfigurer;
import com.github.md.web.ServiceManager;
import com.github.md.web.config.MetaProperties;
import com.github.md.web.config.NotFinishException;

import java.util.HashMap;
import java.util.Map;

/**
 * @author pengxg
 * @date 2022/4/25 9:41 上午
 */
public class UploadManager {
    private static final UploadManager me = new UploadManager();

    private final Map<String, UploadService> uploadServiceMapping = new HashMap<>();

    public static UploadManager me() {
        return me;
    }

    private UploadManager() {
        UploadConfigurer configurer = AnalysisSpringUtil.getBean(DbMetaConfigurer.class);
        UploadRegistry uploadRegistry = new UploadRegistry();
        configurer.configUploadService(uploadRegistry);
        for (Map.Entry<String, UploadService> entry : uploadRegistry.getUploadServiceMapping().entrySet()) {
            config(entry.getKey(), entry.getValue());
        }
    }

    public void config(String mode, UploadService uploadService) {
        AssertUtil.isTrue(!uploadServiceMapping.keySet().contains(mode),
                new DbMetaConfigException(String.format("已经存在此模式(%s)的上传服务, 请换一个模式名", mode)));
        uploadServiceMapping.put(mode, uploadService);
    }

    /**
     * 依据系统启用的上传模式，获取上传服务
     *
     * @return
     */
    public UploadService getUploadService() {
        MetaProperties metaProperties = ServiceManager.getAppProperties();
        String mode = metaProperties.getServer().getUpload().getMode();
        return uploadServiceMapping.get(mode);
    }

    /**
     * 获取指定模式的上传服务
     *
     * @param mode
     * @return
     */
    public UploadService getUploadService(String mode) {
        return uploadServiceMapping.get(mode);
    }

    /**
     * 不同元对象可单独指定上传模式。 需要在元对象配置界面中可配
     *
     * @param metaObject
     * @return
     */
    public UploadService getUploadService(IMetaObject metaObject) {
        // TODO
        throw new NotFinishException("未完成！");
    }
}
