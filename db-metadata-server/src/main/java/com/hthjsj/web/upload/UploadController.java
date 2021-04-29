package com.hthjsj.web.upload;

import com.google.common.base.Preconditions;
import com.hthjsj.analysis.meta.DbMetaService;
import com.hthjsj.analysis.meta.IMetaField;
import com.hthjsj.web.ServiceManager;
import com.hthjsj.web.component.form.RichTextBox;
import com.hthjsj.web.controller.FrontRestController;
import com.hthjsj.web.query.QueryHelper;
import com.jfinal.core.ActionKey;
import com.jfinal.kit.Kv;
import com.jfinal.kit.Ret;
import com.jfinal.kit.StrKit;
import com.jfinal.upload.UploadFile;
import lombok.extern.slf4j.Slf4j;

import java.io.File;

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
public class UploadController extends FrontRestController {

    /**
     * param objectCode
     * param fieldCode
     * param file
     */
    public void index() {
        QueryHelper queryHelper = new QueryHelper(this);
        String objectCode = queryHelper.getObjectCode();
        String fieldCode = queryHelper.getFieldCode();

        IMetaField metaField = ServiceManager.metaService().findFieldByCode(objectCode, fieldCode);

        Preconditions.checkArgument(metaField.configParser().isFile(), "对象{}-字段{}未配置文件属性不正确: 请配置元字段类型为文件", objectCode, fieldCode);

        UploadFile file = getFile();
        UploadService uploadService = ServiceManager.fileService();

        String url = uploadService.upload(file.getFile(), objectCode, fieldCode);
        Kv result = Kv.by("name", file.getFileName());
        result.set("value", url);
        result.set("url", UploadKit.previewUrl(url));
        renderJson(Ret.ok("data", result));
    }

    /**
     * 富文本中的图片上传
     */
    @ActionKey(RichTextBox.UPLOAD_API_PATH)
    public void richText() {
        UploadFile file = getFile();
        UploadService uploadService = ServiceManager.fileService();

        String url = uploadService.upload(file.getFile());
        Kv result = Kv.by(RichTextBox.IMAGE_UPLOAD_RETURN_KEY, UploadKit.previewUrl(url));
        renderJson(result); // richText使用的Tinymce, 它要求返回的数据格式为: { "location": "http://xxxx" }
    }

    /**
     * param objectCode
     * param fieldCode
     * param 业务记录 id
     */
    @ActionKey("file/down")
    public void down() {
        QueryHelper queryHelper = new QueryHelper(this);
        String objectCode = queryHelper.getObjectCode();
        String fieldCode = queryHelper.getFieldCode();
        String id = getPara("id");
        Preconditions.checkArgument(StrKit.notBlank(objectCode, fieldCode, id), "必传参数条件不满足:objectCode=%s,fieldCode=%s,id=%s", objectCode, fieldCode, id);
        DbMetaService dbMetaService = ServiceManager.metaService();
        IMetaField metaField = dbMetaService.findFieldByCode(objectCode, fieldCode);

        Preconditions.checkNotNull(id, "必须指定业务记录id");

        String filePath = dbMetaService.findDataFieldById(metaField.getParent(), metaField, id);

        Preconditions.checkNotNull(filePath, "未找到可下载的文件地址");
        renderImageOrFile(ServiceManager.fileService().getFile(filePath));
    }

    /**
     * TODO 问题: 这种方式开辟了,只要有相对路径就能够通过该接口访问上传目录下的文件
     * 这样架空了"file/down" 亦或是增加了一个文件下载的接口?
     * 后面非图片类型的文件是否可以通过这个接口来完成预览?
     */
    @ActionKey("file/preview")
    public void tmpPre() {
        String path = getPara("path", "");
        UploadService uploadService = ServiceManager.fileService();
        File file = uploadService.getFile(path);

        if (!file.exists()) {
            renderJson(Ret.fail("msg", "文件找不到了"));
            log.warn("文件找不到了, path: {}", path);
            return;
        }
        renderImageOrFile(uploadService.getFile(path));
    }
}
