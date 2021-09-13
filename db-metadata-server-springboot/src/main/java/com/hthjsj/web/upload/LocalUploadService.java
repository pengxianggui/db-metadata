package com.hthjsj.web.upload;

import com.google.common.base.Joiner;
import com.google.common.io.Files;
import com.hthjsj.AnalysisSpringUtil;
import com.hthjsj.web.config.QuickJudge;
import com.jfinal.ext.kit.DateKit;
import com.jfinal.kit.StrKit;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.Date;

/**
 * 本地文件上传服务
 * <p> @Date : 2019/12/4 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
@Service
@Slf4j
public class LocalUploadService implements UploadService {

    @Override
    public String upload(File file, String... splitMarkers) {
        String basePath = getBasePath();
        String destPath = Joiner.on("/").skipNulls().join(splitMarkers);
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
    public File getFile(String filePath) {
        return new File(filePath);
    }

    @Override
    public UploadFileResolve getFileResovler(String fileJsonData) {
        return new UploadFileResolve(fileJsonData);
    }
}
