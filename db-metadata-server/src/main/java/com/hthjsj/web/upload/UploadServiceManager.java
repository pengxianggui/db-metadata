package com.hthjsj.web.upload;

import lombok.Getter;
import lombok.Setter;

/**
 * @author pengxg
 * @date 2021/4/29 10:09 上午
 */
public class UploadServiceManager {
    private static final UploadServiceManager me = new UploadServiceManager();
    @Getter
    @Setter
    private UploadService uploadService;

    private UploadServiceManager() {
    }

    public static UploadServiceManager me() {
        if (me.uploadService == null) {
            me.uploadService = LocalUploadService.me();
        }
        return me;
    }
}
