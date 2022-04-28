package com.github.md.web.upload.asocooss;

import cn.com.asoco.http.HttpResult;
import cn.com.asoco.util.AssertUtil;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSONObject;
import com.github.md.analysis.meta.IMetaField;
import com.github.md.web.upload.FileUploadException;
import com.github.md.web.upload.UploadFile;
import com.github.md.web.upload.UploadFileResolve;
import com.github.md.web.upload.UploadService;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
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
    private AsocoOssProperties asocoOssProperties;

    public AsocoOssUploadService(AsocoOssProperties properties) {
        this.asocoOssProperties = properties;
    }

    @Override
    public String upload(IMetaField metaField, MultipartFile file) {
        return upload(file);
    }

    @Override
    public String upload(MultipartFile file) {
        try {
            Map<String, Object> paramsMap = new HashMap<>();

            String tmpdir = System.getProperty("java.io.tmpdir");
            File tempFile = Paths.get(tmpdir, getFileNameWithAffix(file)).toFile();
            file.transferTo(tempFile);

            paramsMap.put("file", tempFile);
            paramsMap.put("Content-Type", file.getContentType());
            paramsMap.put("bucketName", this.asocoOssProperties.getBucketName());

            String resultStr = HttpUtil.post(this.asocoOssProperties.getBasicServiceUrl() + this.asocoOssProperties.getUploadPath(), paramsMap);
            HttpResult result = JSONObject.parseObject(resultStr, HttpResult.class);

            AssertUtil.isTrue(result.getCode() == 0, result.getMessage());
            return this.asocoOssProperties.getBasicServiceUrl() + result.getData();

        } catch (Exception e) {
            throw new FileUploadException(e.getMessage());
        }
    }

    @Override
    public File getFile(String filePath) {
        String tmpdir = System.getProperty("java.io.tmpdir");
        File file = Paths.get(tmpdir, filePath).toFile();
        HttpUtil.downloadFile(filePath, file);
        return file;
    }

    @Override
    public List<File> getFile(IMetaField metaField, String primaryValue, String fieldValue) {
        List<File> files = Lists.newArrayList();
        UploadFileResolve uploadFileResolve = getFileResolver(metaField, fieldValue);
        if (uploadFileResolve.hasFile()) {
            for (UploadFile file : uploadFileResolve.getFiles()) {
                files.add(getFile(file.getUrl()));
            }
        }

        return files;
    }
}
