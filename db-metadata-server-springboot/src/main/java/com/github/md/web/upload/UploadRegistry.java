package com.github.md.web.upload;

import cn.com.asoco.util.AssertUtil;
import com.github.md.web.DbMetaConfigException;

import java.util.HashMap;
import java.util.Map;

/**
 * @author pengxg
 * @date 2022/4/25 9:57 上午
 */
public final class UploadRegistry {
    private final Map<String, UploadService> uploadServiceMapping = new HashMap<>();

    UploadRegistry() {
    }

    /**
     * 配置指定上传模式，以及对应的上传服务
     *
     * @param mode
     * @param uploadService
     */
    public void config(String mode, UploadService uploadService) {
        AssertUtil.isTrue(!uploadServiceMapping.keySet().contains(mode),
                new DbMetaConfigException(String.format("已经存在此模式(%s)的上传服务, 请换一个模式名", mode)));
        uploadServiceMapping.put(mode, uploadService);
    }

    protected Map<String, UploadService> getUploadServiceMapping() {
        return uploadServiceMapping;
    }
}
