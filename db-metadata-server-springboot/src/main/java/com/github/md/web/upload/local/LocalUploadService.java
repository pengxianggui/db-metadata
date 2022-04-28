package com.github.md.web.upload.local;

import com.github.md.analysis.meta.IMetaField;
import com.github.md.web.WebException;
import com.github.md.web.upload.UploadFileResolve;
import com.github.md.web.upload.UploadService;
import com.google.common.base.Joiner;
import com.jfinal.kit.StrKit;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
public class LocalUploadService implements UploadService {

    private final LocalProperties properties;

    public LocalUploadService(LocalProperties properties) {
        this.properties = properties;
    }

    @Override
    public String upload(IMetaField metaField, MultipartFile file) {
        String objectCode = metaField.objectCode();
        String fieldCode = metaField.fieldCode();
        String destPath = Joiner.on("/").skipNulls().join(new String[]{objectCode, fieldCode});
        File targetFile = toTargetFile(getBasePath() + destPath, file);
        String url = targetFile.getPath().replaceFirst(getBasePath(), "");
        return previewUrl(url);
    }

    @Override
    public String upload(MultipartFile file) {
        String destPath = Joiner.on("/").skipNulls().join(new String[]{"anonymous", "anonymous"});
        File targetFile = toTargetFile(getBasePath() + destPath, file);
        String url = targetFile.getPath().replaceFirst(getBasePath(), "");
        return previewUrl(url);
    }

    @Override
    public List<File> getFile(IMetaField metaField, String primaryValue, String fieldValue) {
        UploadFileResolve uploadFileResolve = getFileResolver(metaField, fieldValue);
        String objectCode = metaField.objectCode(),
                fieldCode = metaField.fieldCode();
        if (uploadFileResolve.hasFile()) {
            return uploadFileResolve.getFiles().stream()
                    .map(f -> getFile(Paths.get(getBasePath(), objectCode, fieldCode, f.getUploadedName()).toString()))
                    .collect(Collectors.toList());
        }
        return new ArrayList<>();
    }

    @Override
    public File getFile(String filePath) {
        String path = Paths.get(getBasePath(), filePath).toString();
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
