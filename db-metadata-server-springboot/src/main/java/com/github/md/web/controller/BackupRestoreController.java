package com.github.md.web.controller;

import com.github.md.analysis.kit.Ret;
import com.github.md.web.backup_restore.MetaDataExport;
import com.github.md.web.backup_restore.MetaDataImport;
import com.github.md.web.user.auth.annotations.MetaAccess;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaTypeFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

/**
 * @author pengxg
 * @date 2022/10/9 3:18 下午
 */
@Slf4j
@RestController
@RequestMapping("meta")
public class BackupRestoreController {

    @MetaAccess
    @PostMapping("backup")
    public ResponseEntity<FileSystemResource> exportZip() {
        try {
            File zipFile = new MetaDataExport().exportZip();
            HttpHeaders headers = new HttpHeaders();
            headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
            headers.add("Content-Disposition", String.format("attachment;filename=\"%s\"", zipFile.getName()));
            headers.add("Pragma", "no-cache");
            headers.add("Expires", "0");

            return ResponseEntity.ok().headers(headers).contentType(MediaTypeFactory.getMediaType(zipFile.getName()).get()).body(new FileSystemResource(zipFile));
        } catch (IOException e) {
            log.debug(e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @MetaAccess
    @PostMapping("restore")
    public Ret importZip(MultipartFile file) {
        try {
            File tempFile = Paths.get(System.getProperty("java.io.tmpdir"), file.getOriginalFilename()).toFile();
            file.transferTo(tempFile);
            new MetaDataImport().importZip(tempFile);
            return Ret.ok();
        } catch (IOException e) {
            log.error(e.getMessage(), e);
            return Ret.fail("message", "上传解析失败");
        }
    }
}
