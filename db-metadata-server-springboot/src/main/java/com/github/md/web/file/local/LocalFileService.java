package com.github.md.web.file.local;

import com.github.md.web.WebException;
import com.github.md.web.file.DownloadService;
import com.github.md.web.file.UploadService;
import com.google.common.base.Joiner;
import com.jfinal.kit.StrKit;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Paths;

/**
 * 本地文件上传服务。上传文件位置：${baseUploadPath}/${objectCode}/${fieldCode}/${fileName}
 * <p>
 * 其中baseUploadPath为服务器本地路径，可通过md.server.upload.base-upload-path进行配置
 * <p> @Date : 2019/12/4 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
@Slf4j
public class LocalFileService implements UploadService, DownloadService {
    public static final String modeName = "local"; // 本地上传/下载的模式编码

    private final LocalProperties properties;

    public LocalFileService(LocalProperties properties) {
        this.properties = properties;
    }

    @Override
    public String getMode() {
        return modeName;
    }

    @Override
    public String upload(MultipartFile file, String... splitMarkers) {
        String destPath = Joiner.on("/").skipNulls().join(splitMarkers);
        File targetFile = toTargetFile(getBasePath() + destPath, file);
        String url = targetFile.getPath().replaceFirst(getBasePath(), "");
        return previewUrl(url);
    }

    @Override
    public File getFile(String fileUrl) {
        String path = Paths.get(getBasePath(), fileUrl).toString();
        return new File(path);
    }

    private File toTargetFile(String dirPath, MultipartFile file) {
        if (!dirPath.endsWith("/")) {
            dirPath += "/";
        }

        String fileName = getFileNameWithAffix(file);
        log.info("new file name :{}", fileName);
        File targetFile = new File(dirPath + fileName);

        try {
            if (!targetFile.getParentFile().exists()) {
                targetFile.getParentFile().mkdirs();
            }
            file.transferTo(targetFile);
        } catch (Exception e) {
            throw new WebException(e.getMessage());
        }

        log.debug("destFile.getPath : {}", targetFile.getPath());
        return targetFile;
    }

    private String getBasePath() {
        String basePath = StrKit.defaultIfBlank(properties.getBaseUploadPath(), "/opt/www/db-meta-serve");
        if (!basePath.endsWith("/")) {
            basePath += "/";
        }
        return basePath;
    }

    private String previewUrl(String url) {
        return "/file/preview?path=" + url;
    }
}
