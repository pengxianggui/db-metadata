package com.github.md.web.upload;


/**
 * @author pengxg
 * @date 2021/4/29 10:09 上午
 */
public interface UploadConfigurer {

    /**
     * 上传配置
     *
     * @param uploadRegistry
     */
    default void configUploadService(UploadRegistry uploadRegistry) {
    }
}
