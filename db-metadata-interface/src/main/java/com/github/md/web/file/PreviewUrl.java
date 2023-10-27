package com.github.md.web.file;

import cn.hutool.core.net.url.UrlBuilder;
import cn.hutool.core.net.url.UrlQuery;

import java.net.URLEncoder;

/**
 * 文件预览地址封装类。上传后返回的文件地址一定是PreviewUrl，以保证文件由/file/preview接口接管，从而使DownloadService生效。
 */
public class PreviewUrl {
    private static final String REST_PATH = "/file/preview";

    private String path;
    private String mode;

    public static PreviewUrl parse(String url) {
        if (!legal(url)) {
            throw new RuntimeException("文件地址url格式不正确, 必须是/file/preview开头。url: " + url);
        }

        UrlQuery uq = UrlBuilder.of("http://localhost" + url).getQuery(); // 取巧借助http://localhost, 以便利用UrlBuilder解析
        return new PreviewUrl((String) uq.get("path"), (String) uq.get("mode"));
    }

    /**
     * 验证是否合法。合法的url必须路径是: /file/preview，能匹配此文件预览接口
     *
     * @param url 接口path
     * @return 返回此url是否合法
     */
    public static boolean legal(String url) {
        if (!url.startsWith(REST_PATH)) {
            return false;
        }

        UrlBuilder ub = UrlBuilder.of("http://localhost" + url); // 取巧借助http://localhost, 以便利用UrlBuilder解析
        if (!REST_PATH.equals(ub.getPath().toString())) {
            return false;
        }

        return true;
    }

    public PreviewUrl() {
    }

    public PreviewUrl(String path, String mode) {
        this.path = path;
        this.mode = mode;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    @Override
    public String toString() {
        return REST_PATH + "?path=" +
                URLEncoder.encode(this.path != null ? this.path : "") +
                "&mode=" +
                URLEncoder.encode(this.mode != null ? this.mode : "");
    }
}
