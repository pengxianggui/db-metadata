package com.github.md.web.upload;

import cn.com.asoco.util.AssertUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ZipUtil;
import com.github.md.web.user.auth.annotations.Type;
import com.github.md.web.user.auth.annotations.MetaAccess;
import com.github.md.web.ServiceManager;
import com.github.md.web.WebException;
import com.google.common.base.Preconditions;
import com.github.md.analysis.meta.IMetaField;
import com.github.md.web.component.form.RichTextBox;
import com.github.md.web.controller.ControllerAdapter;
import com.github.md.web.query.QueryHelper;
import com.github.md.analysis.kit.Kv;
import com.github.md.analysis.kit.Ret;
import com.jfinal.kit.StrKit;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaTypeFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartRequest;

import java.io.File;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * 文件上传/下载
 * UploadController与FileController 共用/file 路径
 * /file/upload -> index()
 * /file/down -> down()
 *
 * <p> @Date : 2019/12/4 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
@Slf4j
@RestController
@RequestMapping("file")
public class UploadController extends ControllerAdapter {

    /**
     * /**
     * param objectCode
     * param fieldCode
     * param file
     */
    @MetaAccess(value = Type.API)
    @PostMapping("upload")
    public Ret index(MultipartRequest request) {
        QueryHelper queryHelper = queryHelper();
        String objectCode = queryHelper.getObjectCode();
        String fieldCode = queryHelper.getFieldCode();

        IMetaField metaField = ServiceManager.metaService().findFieldByCode(objectCode, fieldCode);

        Preconditions.checkArgument(metaField.configParser().isFile(), "对象{}-字段{}未配置文件属性不正确: 请配置元字段类型为文件", objectCode, fieldCode);
        Preconditions.checkArgument(request.getFileMap().size() > 0 && request.getFileMap().size() == 1, "该接口仅作为单文件上传用,对象{}-字段{}", objectCode, fieldCode);

        List<MultipartFile> uploadFiles = new ArrayList<>();
        request.getFileMap().forEach((k, v) -> {
            uploadFiles.add(v);
        });

        String tmpdir = System.getProperty("java.io.tmpdir");
        File destFile = Paths.get(tmpdir, uploadFiles.get(0).getOriginalFilename()).toFile();

        try {
            uploadFiles.get(0).transferTo(destFile);
        } catch (Exception e) {
            throw new WebException(e.getMessage());
        }

        String url = uploadService().upload(metaField, destFile);
        Kv result = Kv.by("name", destFile.getName());
        result.set("value", url);
        result.set("url", UploadKit.previewUrl(url));
        return Ret.ok("data", result);
    }

    /**
     * 富文本中的图片上传
     */
    @MetaAccess(value = Type.API)
    @PostMapping(RichTextBox.UPLOAD_API_PATH)
    public void richText(MultipartFile uploadFile) {
        //        UploadService uploadService = ServiceManager.fileService();
        //
        //        String url = uploadService.upload(file.getFile());
        //        Kv result = Kv.by(RichTextBox.IMAGE_UPLOAD_RETURN_KEY, UploadKit.previewUrl(url));
        //        renderJson(result); // richText使用的Tinymce, 它要求返回的数据格式为: { "location": "http://xxxx" }
    }

    /**
     * param objectCode
     * param fieldCode
     * param 业务记录 id
     */
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

        UploadService uploadService = uploadService();
        List<File> files = uploadService.getFile(metaField, id, fieldValue);

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
    @MetaAccess(value = Type.API)
    @GetMapping("preview")
    public ResponseEntity<FileSystemResource> tmpPre() {
        String path = parameterHelper().getPara("path", "");

        File targetFile = uploadService().getFile(Paths.get(uploadService().getBasePath(), path).toString());

        return ResponseEntity.ok().contentType(MediaTypeFactory.getMediaType(targetFile.getName()).get()).body(new FileSystemResource(targetFile));
    }
}
