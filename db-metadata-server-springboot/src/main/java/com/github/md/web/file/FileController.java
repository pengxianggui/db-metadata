package com.github.md.web.file;

import cn.com.asoco.util.AssertUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ZipUtil;
import com.github.md.web.user.auth.annotations.Type;
import com.github.md.web.user.auth.annotations.MetaAccess;
import com.github.md.web.WebException;
import com.google.common.base.Preconditions;
import com.github.md.analysis.meta.IMetaField;
import com.github.md.web.component.form.RichTextBox;
import com.github.md.web.controller.ControllerAdapter;
import com.github.md.web.query.QueryHelper;
import com.github.md.analysis.kit.Kv;
import com.github.md.analysis.kit.Ret;
import com.jfinal.kit.StrKit;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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

import java.io.File;
import java.net.URLEncoder;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * 文件上传/下载
 *
 * <p> @Date : 2019/12/4 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
@Slf4j
@Api(tags = "文件上传/下载")
@RestController
@RequestMapping("file")
public class FileController extends ControllerAdapter {

    /**
     * /**
     * param objectCode
     * param fieldCode
     * param file
     */
    @ApiOperation(value = "文件上传", notes = "兼容普通上传和dbmeta内置上传组件的上传")
    @MetaAccess(value = Type.API)
    @PostMapping("upload")
    public Ret index(MultipartRequest request) {

        List<MultipartFile> uploadFiles = new ArrayList<>();
        request.getFileMap().forEach((k, v) -> {
            uploadFiles.add(v);
        });

        if (uploadFiles.size() == 0) {
            throw new WebException("未发现文件资源");
        }

        if (uploadFiles.size() > 1) {
            throw new WebException("暂不支持多文件上传, 请一个个上传");
        }
        MultipartFile file = uploadFiles.get(0);

        UploadService uploadService = uploadService();
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
     * 富文本中的图片上传
     */
    @ApiOperation(value = "文件上传(富文本中)", notes = "dbmeta内置组件富文本中的文件上传略有区别，响应数据需要包装成json")
    @MetaAccess(value = Type.API)
    @PostMapping("upload/rich-text")
    public Kv richText(MultipartFile file) {
        QueryHelper queryHelper = queryHelper();
        String objectCode = queryHelper.getObjectCode();
        String fieldCode = queryHelper.getFieldCode();

        String url = uploadService().upload(file, StrKit.defaultIfBlank(objectCode, "anonymous"), StrKit.defaultIfBlank(fieldCode, "anonymous"));
        Kv result = Kv.by(RichTextBox.IMAGE_UPLOAD_RETURN_KEY, url);
        return result; // richText使用的Tinymce, 它要求返回的数据格式为: { "location": "http://xxxx" }
    }

    /**
     * param objectCode
     * param fieldCode
     * param 业务记录 id
     */
    @ApiOperation(value = "文件下载", notes = "适用于dbmeta内置的本地文件存储服务(md.server.upload.mode=local)，仅适用于dbmeta文件(/图片)控件的下载")
    @MetaAccess(value = Type.API)
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

        DownloadService downloadService = downloadService();
        List<File> files = downloadService.getFile(metaField, id, fieldValue);

        File targetFile;
        AssertUtil.isTrue(CollectionUtil.isNotEmpty(files), "文件资源未找到！");
        if (files.size() == 1) {
            targetFile = files.get(0);
        } else {  // 当有多个文件时，应当一并压缩打包下载
            String tmpdir = System.getProperty("java.io.tmpdir");
            targetFile = Paths.get(tmpdir, fieldCode + "_" + id + ".zip").toFile();
            ZipUtil.zip(targetFile, false, files.toArray(new File[files.size()]));
        }

        AssertUtil.isTrue(targetFile.exists(), new WebException("文件未找到, %s", targetFile.getPath()));

        HttpHeaders headers = new HttpHeaders();
        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.add("Content-Disposition", String.format("attachment; filename=\"%s\"", targetFile.getName()));
        headers.add("Pragma", "no-cache");
        headers.add("Expires", "0");

        return ResponseEntity.ok().headers(headers).contentType(MediaTypeFactory.getMediaType(targetFile.getName()).get()).body(new FileSystemResource(targetFile));
    }

    /**
     * TODO 问题: 这种方式开辟了,只要有相对路径就能够通过该接口访问上传目录下的文件
     * 这样架空了"file/down" 亦或是增加了一个文件下载的接口?
     * 后面非图片类型的文件是否可以通过这个接口来完成预览?
     */
    @ApiOperation(value = "图片预览/文件下载", notes = "适用于dbmeta内置的本地文件存储服务(md.server.upload.mode=local)")
    @MetaAccess(value = Type.API)
    @GetMapping("preview")
    public ResponseEntity<FileSystemResource> tmpPre() {
        String path = parameterHelper().getPara("path", "");

        File targetFile = downloadService().getFile(path);

        String fileName = URLEncoder.encode(targetFile.getName());
        HttpHeaders headers = new HttpHeaders();
        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.add("Content-Disposition", String.format("attachment; filename=\"%s\"", fileName));
        headers.add("Pragma", "no-cache");
        headers.add("Expires", "0");

        Optional<MediaType> optional = MediaTypeFactory.getMediaType(fileName);
        return ResponseEntity.ok().headers(headers).contentType(optional.orElse(MediaType.APPLICATION_OCTET_STREAM)).body(new FileSystemResource(targetFile));
    }
}
