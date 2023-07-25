package com.github.md.web.file.oss;

import cn.hutool.core.net.url.UrlBuilder;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.AccessControlList;
import com.aliyun.oss.model.CannedAccessControlList;
import com.google.common.base.Strings;

import java.io.InputStream;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Date;

public class AliyunOssUtil {
    private final AliyunOssProperties aliyunOssProperties;

    public AliyunOssUtil(AliyunOssProperties properties) {
        this.aliyunOssProperties = properties;
    }

    /**
     * 上传文件流到oss
     *
     * @param inputStream 文件流
     * @param targetName
     * @return 返回文件在oss中存储的绝对路径(含文件名)。注意，这个地址浏览器不可直接访问，
     * 需要传入{@link AliyunOssFileService#getFile(String)}才能获取。
     */
    public String uploadInputStream(InputStream inputStream, String targetName) {
        OSS ossClient = initOss();
        try {
            String bucketName = aliyunOssProperties.getBucket();
            String dir = aliyunOssProperties.getDir();

            String key;
            if (!Strings.isNullOrEmpty(dir)) {
                key = dir + "/" + targetName;
            } else {
                key = targetName;
            }

            ossClient.putObject(bucketName, key, inputStream);
            return key;
        } finally {
            //释放资源
            ossClient.shutdown();
        }
    }


    /**
     * 获取文件的链接地址
     *
     * @param targetName 文件名(携带路径——bucket中的完整路径)
     * @return 直接可读的文件地址。如果bucket不是公开的，这个地址可能有有效期(默认是1h)
     */
    public URL getFileUrl(String targetName) {
        OSS ossClient = initOss();

        try {
            // 默认链接过期时间为一个小时
            final String bucketName = aliyunOssProperties.getBucket();
            long expirationTimestamp = System.currentTimeMillis() + 3600 * 1000;
            Date expiration = new Date(expirationTimestamp);

            URL url = ossClient.generatePresignedUrl(bucketName, targetName, expiration);
            if (bucketIsPublicRead(ossClient, bucketName)) {
                // bucket为公共读的情况
                url = UrlBuilder.of(url, Charset.defaultCharset()).setQuery(null).toURL();
            } else {
            }
            return url;
        } finally {
            ossClient.shutdown();
        }
    }


    /**
     * 初始化oss
     *
     * @return
     */
    private OSS initOss() {
        return new OSSClientBuilder().build(aliyunOssProperties.getEndPoint(), aliyunOssProperties.getAccessKey(), aliyunOssProperties.getAccessSecret());
    }

    /**
     * 判断一个bucket可不可以公共读
     *
     * @param ossClient  内部不会关闭
     * @param bucketName
     * @return
     */
    private Boolean bucketIsPublicRead(OSS ossClient, String bucketName) {
        // 判断Bucket是否允许公共读访问
        AccessControlList acl = ossClient.getBucketAcl(bucketName);
        if (acl.getCannedACL().equals(CannedAccessControlList.PublicRead) ||
                acl.getCannedACL().equals(CannedAccessControlList.PublicReadWrite)) {
            // Bucket 是公共读/写的
            return true;
        }
        return false;
    }

}
