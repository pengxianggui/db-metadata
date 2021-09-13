package com.hthjsj.web.upload;

import lombok.Getter;
import org.springframework.stereotype.Component;

/**
 * @author pengxg
 * @date 2021/4/29 10:09 上午
 */
@Component
public class UploadServiceManager {

    @Getter
    private UploadService uploadService;

    public UploadServiceManager(UploadService uploadService) {
        this.uploadService = uploadService;
    }
}
