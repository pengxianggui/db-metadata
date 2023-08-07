package com.github.md.web.file.oss;

import cn.hutool.http.HttpUtil;
import com.github.md.web.file.DownloadService;
import com.github.md.web.file.UploadService;
import com.google.common.base.Joiner;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;

/**
 * 阿里云oss文件服务, 上传/下载。使用配置: md.server.upload.aliyunoss
 */
public class AliyunOssFileService implements UploadService, DownloadService {
    private static final String FILE_SEPARATOR = "/"; // 阿里云oss里分隔符肯定是/
    private static final String MODE = "aliyunoss";

    private final AliyunOssUtil ossUtil;

    public AliyunOssFileService(AliyunOssUtil ossUtil) {
        this.ossUtil = ossUtil;
    }

    /**
     * 下载文件并返回
     *
     * @param fileUrl {@link #upload(MultipartFile, String...)}决定了此值为固定的oss中的targetName(即bucket中的完整路径+文件名)，但这个
     *                地址浏览器不可直接访问。需要调用aliyun oss sdk获取资源的临时访问地址，因为资源可能不是public的，访问可能受到阿里云控制。
     * @return
     */
    @Override
    public File getFile(String fileUrl) {
        try {
            String decodeFileUrl = URLDecoder.decode(fileUrl, StandardCharsets.UTF_8.name());
            String tmpdir = System.getProperty("java.io.tmpdir");
            File file = Paths.get(tmpdir, decodeFileUrl).toFile();
            if (file.exists()) { // 缓存，避免频繁获取阿里云对象
                return file;
            }

            URL url = ossUtil.getFileUrl(decodeFileUrl);
            HttpUtil.downloadFile(url.toString(), file);
            file.deleteOnExit(); // TODO: 优化为除了deleteOnExit, 还可以提供一个值自动过期，比如1天, 以避免临时文件太多。
            return file;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String getMode() {
        return MODE;
    }

    /**
     * 返回代理后的接口地址。注意，地址中的path地址也并不是可访问的资源路径，而是需要通过{@link #getFile(String)}进行转行，以避免bucket非public
     * 时，资源链接的过期问题。
     *
     * @param file         文件
     * @param splitMarkers 分隔标记，分隔目录。你可以依据用于文件存放管理
     * @return
     */
    @Override
    public String upload(MultipartFile file, String... splitMarkers) {
        String destPath = Joiner.on(FILE_SEPARATOR).skipNulls().join(splitMarkers);
        String ossTargetName = destPath + FILE_SEPARATOR + getFileNameWithAffix(file);
        try {
            String ossFileKey = ossUtil.uploadInputStream(file.getInputStream(), ossTargetName);
            // important! 这里必须返回/file/preview代理后的接口路径。因为oss如果不是公开访问的，必须要走后端重新获取临时地址才行，
            //  否则资源访问地址是临时性的，会过期的。数据库里只能存这个地址，然后由/file/preview再导向此类的getFile, 从而每次访问
            //  oss资源时，临时生成完整路径去请求资源，确保不会过期。
            return buildFileUrl(ossFileKey).toString();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
