package com.hthjsj.web.upload;

import com.google.common.base.Joiner;
import com.google.common.io.Files;
import com.hthjsj.AnalysisConfig;
import com.jfinal.ext.kit.DateKit;
import lombok.extern.slf4j.Slf4j;

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
@Slf4j
public class LocalUploadService implements UploadService {

    private final static UploadService me = new LocalUploadService();

    public static UploadService me() {
        return me;
    }

    @Override
    public File upload(File file, String... splitMarkers) {
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
        return destFile;
    }

    @Override
    public String getBasePath() {
        return AnalysisConfig.me().getProp().get("upload.dir");
    }

    @Override
    public File getFile(String filePath) {
        return new File(getBasePath() + filePath);
    }

}
