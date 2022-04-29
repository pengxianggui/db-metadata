package com.github.md.web.file;


/**
 * 文件服务配置器。为指定mode配置文件上传服务和下载服务
 *
 * @author pengxg
 * @date 2021/4/29 10:09 上午
 */
public interface FileConfigurer {

    /**
     * 上传配置
     *
     * @param fileRegistry
     */
    default void configFileService(FileRegistry fileRegistry) {
    }
}
