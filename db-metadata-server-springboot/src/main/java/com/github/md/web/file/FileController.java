package com.github.md.web.file;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ZipUtil;
import com.github.md.analysis.AnalysisSpringUtil;
import com.github.md.analysis.kit.Kv;
import com.github.md.analysis.kit.Ret;
import com.github.md.analysis.meta.IMetaField;
import com.github.md.web.WebException;
import com.github.md.web.component.form.RichTextBox;
import com.github.md.web.controller.ControllerAdapter;
import com.github.md.web.file.local.LocalFileService;
import com.github.md.web.kit.AssertKit;
import com.github.md.web.query.QueryHelper;
import com.github.md.web.user.auth.annotations.ApiType;
import com.github.md.web.user.auth.annotations.Type;
import com.google.common.base.Preconditions;
import com.jfinal.kit.StrKit;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.MediaTypeFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartRequest;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 文件上传/下载
 *
 * <p> @Date : 2019/12/4 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
@Slf4j
@RestController
@RequestMapping("file")
public class FileController extends ControllerAdapter {

    /**
     * 兼容普通上传和dbmeta内置上传组件的上传。Content-Type: multipart/form-data; 文件上传key为file。响应数据格式为{"name":name, "value":path, "url":url}
     * param objectCode
     * param fieldCode
     * param file
     */
    @ApiType(value = Type.API)
    @PostMapping(value = "upload", consumes = {"multipart/form-data"})
    public Ret index(MultipartRequest request) {

        List<MultipartFile> uploadFiles = new ArrayList<>();
        request.getFileMap().forEach((k, v) -> uploadFiles.add(v));

        if (uploadFiles.size() == 0) {
            throw new WebException("未发现文件资源");
        }

        if (uploadFiles.size() > 1) {
            throw new WebException("暂不支持多文件上传, 请一个个上传");
        }
        MultipartFile file = uploadFiles.get(0);

        UploadService uploadService = FileManager.me().getUploadService(); // 默认上传模式
        QueryHelper queryHelper = queryHelper();
        String objectCode = queryHelper.getObjectCode();
        String fieldCode = queryHelper.getFieldCode();

        if (StrKit.notBlank(objectCode, fieldCode)) { // 兼容dbmeta上传控件，和通用上传
            IMetaField metaField = metaService().findFieldByCode(objectCode, fieldCode);
            if (!metaField.configParser().isFile()) {
                log.error("当前字段类型非文件类型, 请在元字段配置中，勾选'是否文件'选项");
                throw new WebException("当前字段类型非文件类型");
            }
        }

        String url = uploadService.upload(file, StrKit.defaultIfBlank(objectCode, "anonymous"), StrKit.defaultIfBlank(fieldCode, "anonymous"));

        Kv result = Kv.by("name", file.getOriginalFilename());
        result.set("value", url);
        result.set("url", url);
        return Ret.ok("data", result);
    }

    /**
     * 富文本中的图片上传。
     * dbmeta内置组件富文本中的文件上传略有区别，响应数据需要包装成json
     */
    @ApiType(value = Type.API)
    @PostMapping("upload/rich-text")
    public Kv richText(MultipartFile file) {
        QueryHelper queryHelper = queryHelper();
        String objectCode = queryHelper.getObjectCode();
        String fieldCode = queryHelper.getFieldCode();

        // 默认的上传模式
        String url = FileManager.me().getUploadService().upload(file, StrKit.defaultIfBlank(objectCode, "anonymous"), StrKit.defaultIfBlank(fieldCode, "anonymous"));

        // richText使用的Tinymce, 它要求返回的数据格式为: { "location": "http://xxxx" }
        return Kv.by(RichTextBox.IMAGE_UPLOAD_RETURN_KEY, url);
    }

    /**
     * 基于元数据精准定位的文件下载，只适用于本地文件存储服务。仅适用于dbmeta文件(/图片)控件的下载
     * param objectCode
     * param fieldCode
     * param 业务记录 id
     */
    @ApiType(value = Type.API)
    @GetMapping("down")
    public ResponseEntity<FileSystemResource> down() {
        String id = parameterHelper().get("id");
        Preconditions.checkNotNull(id, "必须指定业务记录id");

        QueryHelper queryHelper = queryHelper();
        String objectCode = queryHelper.getObjectCode();
        String fieldCode = queryHelper.getFieldCode();

        Preconditions.checkArgument(StrKit.notBlank(objectCode, fieldCode, id), "必传参数条件不满足:objectCode=%s,fieldCode=%s,id=%s", objectCode, fieldCode, id);

        IMetaField metaField = metaService().findFieldByCode(objectCode, fieldCode);
        String fieldValue = metaService().findDataFieldById(metaField.getParent(), metaField, id);

        Preconditions.checkNotNull(fieldValue, "未找到可下载的文件地址");

        UploadFileResolve uploadFileResolve = getFileResolver(metaField, fieldValue);
        List<File> files = uploadFileResolve.getFiles().stream()
                .filter(uf -> PreviewUrl.legal(uf.getUrl())) // TODO: 不排除可能有合法的绝对地址url, 如http://开头的。也需要下载的
                .map(uf -> {
                    PreviewUrl previewUrl = PreviewUrl.parse(uf.getUrl());
                    String decodeUrl;
                    try {
                        decodeUrl = URLDecoder.decode(previewUrl.getPath(), StandardCharsets.UTF_8.name());
                    } catch (UnsupportedEncodingException e) {
                        decodeUrl = URLDecoder.decode(previewUrl.getPath());
                    }
                    return FileManager.me().getDownloadService(previewUrl.getMode()).getFile(decodeUrl);
                }).collect(Collectors.toList());

        File targetFile;
        AssertKit.isTrue(CollectionUtil.isNotEmpty(files), "文件资源未找到！");
        if (files.size() == 1) {
            targetFile = files.get(0);
        } else {  // 当有多个文件时，应当一并压缩打包下载
            String tmpdir = System.getProperty("java.io.tmpdir");
            targetFile = Paths.get(tmpdir, fieldCode + "_" + id + ".zip").toFile();
            ZipUtil.zip(targetFile, false, files.toArray(new File[files.size()]));
        }

        return responseFile(targetFile);
    }

    /**
     * 文件下载。
     * 通用的文件下载，将直接调用配置的{@link DownloadService#getFile(String)}下载文件，具体调用哪个实现类，取决于配置的文件存储服务(md.server.upload.mode)
     * <p>
     * param: path。
     *
     * @return 返回下载的文件
     */
    @ApiType(value = Type.API)
    @GetMapping("download")
    public ResponseEntity<FileSystemResource> download() {
        String url = StrKit.defaultIfBlank(parameterHelper().get("path"), parameterHelper().get("url"));
        String mode = StrKit.defaultIfBlank(parameterHelper().get("mode"), LocalFileService.modeName);

        String decodeUrl;
        try {
            decodeUrl = URLDecoder.decode(url, StandardCharsets.UTF_8.name());
        } catch (UnsupportedEncodingException e) {
            decodeUrl = URLDecoder.decode(url);
        }
        File targetFile = FileManager.me().getDownloadService(mode).getFile(decodeUrl);
        return responseFile(targetFile);
    }

    /**
     * 图片、视频预览/文件下载。先通过{@link DownloadService}获取文件(默认的local模式，直接本地定位文件位置；若采用云存储模式，
     * 则应当在{@link DownloadService#getFile(String)}中实现下载)。然后借助ResourceHttpRequestHandler返回本地资源。
     * 可在预览视频文件时支持range分段返回。
     * <p>
     */
    @ApiType(value = Type.API)
    @GetMapping("preview")
    public void preview() {
        String path = parameterHelper().getPara("path", "");
        String mode = parameterHelper().getPara("mode", LocalFileService.modeName);

        HttpServletRequest request = getRequest();
        HttpServletResponse response = getResponse();

        String decodeUrl;
        try {
            decodeUrl = URLDecoder.decode(path, StandardCharsets.UTF_8.name());
        } catch (UnsupportedEncodingException e) {
            decodeUrl = URLDecoder.decode(path);
        }
        File targetFile = FileManager.me().getDownloadService(mode).getFile(decodeUrl);
        if (!targetFile.exists()) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            response.setCharacterEncoding(StandardCharsets.UTF_8.toString());
            return;
        }

        try {
            String fileName = URLEncoder.encode(targetFile.getName(), Charset.defaultCharset().toString());
            Optional<MediaType> optional = MediaTypeFactory.getMediaType(fileName);
            response.setContentType(optional.orElse(MediaType.APPLICATION_OCTET_STREAM).getType());
            response.setHeader("Connection", "close");
            response.setHeader("Content-Disposition", String.format("attachment; filename=\"%s\"", fileName));

            FileResourceHttpRequestHandler fileResourceHttpRequestHandler = AnalysisSpringUtil.getBean(FileResourceHttpRequestHandler.class);
            request.setAttribute(FileResourceHttpRequestHandler.FILE_PATH, targetFile.getAbsolutePath());
            fileResourceHttpRequestHandler.handleRequest(request, response);
        } catch (IOException | ServletException e) {
            log.error(e.getMessage());
        }
    }

    /**
     * 响应文件
     *
     * @param targetFile 待响应的文件
     * @return 返回响应体
     */
    private ResponseEntity<FileSystemResource> responseFile(File targetFile) {
        AssertKit.isTrue(targetFile.exists(), new WebException("文件未找到, %s", targetFile.getPath()));

        HttpHeaders headers = new HttpHeaders();
        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.add("Content-Disposition", String.format("attachment; filename=\"%s\"", targetFile.getName()));
        headers.add("Pragma", "no-cache");
        headers.add("Expires", "0");

        MediaType mediaType = MediaTypeFactory.getMediaType(targetFile.getName()).orElse(MediaType.APPLICATION_OCTET_STREAM);
        return ResponseEntity.ok().headers(headers).contentType(mediaType).body(new FileSystemResource(targetFile));
    }

    /**
     * 文件解析器
     *
     * @param metaField  元字段
     * @param fieldValue 数据库里存储的字段值
     * @return 返回上传文件解析器
     */
    private UploadFileResolve getFileResolver(IMetaField metaField, String fieldValue) {
        if (metaField.dbType().isJson()) {
            return new JsonUploadFileResolve(fieldValue);
        } else {
            return new StrUploadFileResolve(fieldValue);
        }
    }
}
