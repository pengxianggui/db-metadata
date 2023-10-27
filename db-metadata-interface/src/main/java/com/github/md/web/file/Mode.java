package com.github.md.web.file;

/**
 * 上传/下载的 模式。一种模式通常对应一组文件上传/下载 的逻辑。比如：
 * <p>
 * 内置的：
 * local: 对应本地上传，和本地下载。
 * <p>
 * 你可以继承{@link UploadService}和{@link DownloadService}类实现自定义的上传和下载，例如上传到阿里云oss, 七牛等。但通常，下载服务可能是不需要的。
 * <p>
 * model的作用是为了配对一个上传，和一个下载为一组，同时区分不同组。
 *
 * @author pengxg
 * @date 2023/4/27 16:37
 */
public interface Mode {

    /**
     * 返回一个字符串，标识模式编码。注意，全局应当唯一。如果冲突，dbmeta会报错。
     *
     * @return 返回模式编码，此编码必须与配置中的 md.server.upload.mode 保持一致(如果启用此模式的话)
     */
    String getMode();
}
