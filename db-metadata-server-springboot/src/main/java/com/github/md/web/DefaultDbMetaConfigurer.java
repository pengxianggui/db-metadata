package com.github.md.web;

import com.github.md.web.event.ExtensibleListenerRegistry;
import com.github.md.web.file.DownloadService;
import com.github.md.web.file.FileRegistry;
import com.github.md.web.file.UploadService;
import com.github.md.web.user.AuthenticationRegistry;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.util.CollectionUtils;

import java.util.Map;

/**
 * 默认的dbmeta配置器，建议继承此方法，实现业务系统的自定义配置。
 * <p>
 * 可通过此配置类实现如下自定义配置：
 * <pre>
 *     1. 认证与鉴权配置；包括，登录、鉴权服务，角色、权限服务，以及用户服务类的自定义。详见 {@link AuthenticationRegistry}
 *     2. 文件服务配置；配置文件的上传与下载。详见{@link FileRegistry}
 *     3. 事件监听扩展配置；可实现dbmeta内部动作的扩展监听。详见{@link ExtensibleListenerRegistry}
 * </pre>
 * <p>
 * 此配置器，会默认装配spring容器中所有的文件服务类。
 *
 * @author pengxg
 * @date 2023/4/27 16:34
 */
public class DefaultDbMetaConfigurer extends DbMetaConfigurer implements ApplicationContextAware {
    private ApplicationContext applicationContext;

    /**
     * 配置文件服务。此方法会装配spring容器中所有的文件服务(上传/下载)类，可按需继承并覆盖此方法。
     *
     * @param fileRegistry
     */
    @Override
    public void configFileService(FileRegistry fileRegistry) {
        Map<String, DownloadService> downloadServiceMap = applicationContext.getBeansOfType(DownloadService.class);
        if (!CollectionUtils.isEmpty(downloadServiceMap)) {
            for (DownloadService service : downloadServiceMap.values()) {
                fileRegistry.config(service.getMode(), service);
            }
        }

        Map<String, UploadService> uploadServiceMap = applicationContext.getBeansOfType(UploadService.class);
        if (!CollectionUtils.isEmpty(uploadServiceMap)) {
            for (UploadService service : uploadServiceMap.values()) {
                fileRegistry.config(service.getMode(), service);
            }
        }
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
