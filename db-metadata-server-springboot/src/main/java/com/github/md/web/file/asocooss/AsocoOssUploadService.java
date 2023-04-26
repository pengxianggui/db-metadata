package com.github.md.web.file.asocooss;

import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSONObject;
import com.github.md.web.file.FileUploadException;
import com.github.md.web.file.UploadService;
import com.github.md.web.kit.AssertKit;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

/**
 * TODO 2.3 内置只保留一个本地上传。其他实现全部通过额外jar包依赖。那么需要做两件事情：
 * 1. 将UploadService接口，以及相关接口(如IMetaField)全部移动到一个单独的模块, 如 interface，方便第三方依赖开发;
 * 2. 另外创建一个db-metadata的gitlab组，用来托管类似的扩展插件。
 * Minio Oss上传
 *
 * @author pengxg
 * @date 2022/4/28 10:10 上午
 */
@Slf4j
public class AsocoOssUploadService implements UploadService {
    public static final String modeName = "asoco-oss";
    private AsocoOssProperties asocoOssProperties;

    public AsocoOssUploadService(AsocoOssProperties properties) {
        this.asocoOssProperties = properties;
    }

    @Override
    public String upload(MultipartFile file, String... splitMarkers) {
        try {
            Map<String, Object> paramsMap = new HashMap<>();

            String tmpdir = System.getProperty("java.io.tmpdir");
            File tempFile = Paths.get(tmpdir, getFileNameWithAffix(file)).toFile();
            file.transferTo(tempFile);

            paramsMap.put("file", tempFile);
            paramsMap.put("Content-Type", file.getContentType());
            paramsMap.put("bucketName", this.asocoOssProperties.getBucketName());

            String resultStr = HttpUtil.post(this.asocoOssProperties.getBasicServiceUrl() + this.asocoOssProperties.getUploadPath(), paramsMap);
            JSONObject result = JSONObject.parseObject(resultStr);

            AssertKit.isTrue(result.getInteger("code") == 0, result.getString("message"));
            return this.asocoOssProperties.getBasicServiceUrl() + result.getString("data");

        } catch (Exception e) {
            throw new FileUploadException(file, e);
        }
    }
}
