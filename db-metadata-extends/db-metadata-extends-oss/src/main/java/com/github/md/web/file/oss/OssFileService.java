package com.github.md.web.file.oss;

import com.github.md.web.file.DownloadService;
import com.github.md.web.file.UploadService;

import java.io.File;

public class OssFileService implements UploadService, DownloadService {

    @Override
    public File getFile(String fileUrl) {
        return null;
    }

    @Override
    public String getMode() {
        return null;
    }

    @Override
    public String upload(org.springframework.web.multipart.MultipartFile file, String... splitMarkers) {
        return null;
    }
}
