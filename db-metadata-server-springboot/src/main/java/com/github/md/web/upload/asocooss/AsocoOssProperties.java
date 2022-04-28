package com.github.md.web.upload.asocooss;

import lombok.Getter;
import lombok.Setter;

/**
 * @author pengxg
 * @date 2022/4/28 10:23 上午
 */
@Getter
@Setter
public class AsocoOssProperties {
    private String basicServiceUrl;
    private String uploadPath = "/oss/upload";
    private String bucketName;
}
