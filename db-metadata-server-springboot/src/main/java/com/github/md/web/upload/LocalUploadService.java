package com.github.md.web.upload;

import com.github.md.analysis.AnalysisSpringUtil;
import com.github.md.analysis.meta.IMetaField;
import com.google.common.base.Joiner;
import com.google.common.io.Files;
import com.github.md.web.config.QuickJudge;
import com.github.md.web.kit.DateKit;
import com.jfinal.kit.StrKit;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
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
@Service
@Slf4j
public class LocalUploadService implements UploadService {

    @Override
    public String upload(IMetaField metaField, File file) {
        String objectCode = metaField.objectCode();
        String fieldCode = metaField.fieldCode();

        String basePath = getBasePath();
        String destPath = Joiner.on("/").skipNulls().join(new String[]{objectCode, fieldCode});
        log.info("Joiner filePath : {}", destPath);
        if (!basePath.endsWith("/")) {
            basePath += "/";
        }
        if (!destPath.endsWith("/")) {
            destPath += "/";
        }

        //filename  abcd.txt
        //newname   abcd_yyyy-MM-dd_HH:mm:ssSSS.txt
        String fileName = Files.getNameWithoutExtension(file.getName()) + "_" + DateKit.toStr(new Date(), "yyyyMMdd_HH_mm_ss_SSS") + "."
                + Files.getFileExtension(file.getName());
        log.info("new file name :{}", fileName);
        File destFile = new File(basePath + destPath + fileName);

        try {
            if (!destFile.getParentFile().exists()) {
                destFile.getParentFile().mkdirs();
            }
            Files.copy(file, destFile);
        } catch (IOException e) {
            log.error("File upload failed , The file original name is {}, new name is", file.getName(), destFile.getName());
            log.error(e.getMessage(), e);
        }

        log.info("destFile.getPath : {}", destFile.getPath());
        return destFile.getPath().replaceFirst(getBasePath(), "");
    }

    @Override
    public String getBasePath() {
        QuickJudge quickJudge = AnalysisSpringUtil.getBean(QuickJudge.class);
        return StrKit.isBlank(quickJudge.baseUploadPath()) ? UploadKit.getUploadDir().toString() : quickJudge.baseUploadPath();
    }

    @Override
    public List<File> getFile(IMetaField metaField, String primaryValue, String fieldValue) {
        UploadFileResolve uploadFileResolve = getFileResovler(metaField, fieldValue);
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
        return new File(filePath);
    }

    @Override
    public UploadFileResolve getFileResovler(IMetaField metaField, String fieldValue) {
        if (metaField.dbType().isJson()) {
            return new JsonUploadFileResolve(fieldValue);
        } else {
            return new StrUploadFileResolve(fieldValue);
        }
    }
}
