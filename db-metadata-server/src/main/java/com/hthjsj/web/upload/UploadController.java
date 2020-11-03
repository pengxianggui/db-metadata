package com.hthjsj.web.upload;

import com.google.common.base.Preconditions;
import com.hthjsj.analysis.meta.DbMetaService;
import com.hthjsj.analysis.meta.IMetaField;
import com.hthjsj.web.ServiceManager;
import com.hthjsj.web.controller.FrontRestController;
import com.hthjsj.web.query.QueryHelper;
import com.jfinal.core.ActionKey;
import com.jfinal.core.JFinal;
import com.jfinal.kit.Kv;
import com.jfinal.kit.Ret;
import com.jfinal.kit.StrKit;
import com.jfinal.upload.UploadFile;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.util.List;

/**
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
     *
     * <pre>
     *     当与springboot 集成时上传文件会出现异常(无论使用tomcat容器或jetty容器)
     *     java.lang.RuntimeException: java.io.IOException: Corrupt form data: premature ending
     * </pre>
     */
    public void index() {
        QueryHelper queryHelper = new QueryHelper(this);
        String objectCode = queryHelper.getObjectCode();
        String fieldCode = queryHelper.getFieldCode();

        IMetaField metaField = ServiceManager.metaService().findFieldByCode(objectCode, fieldCode);

        Preconditions.checkArgument(metaField.configParser().isFile(), "对象{}-字段{}未配置文件属性不正确", objectCode, fieldCode);

        UploadFile file = null;
        UploadService uploadService = ServiceManager.fileService();
        try {
            file = getFile();
        } catch (RuntimeException e) {
            if (e.getMessage().contains("premature ending")) {
                log.error("该上传动作出现错误,可能是oreilly组件引起;建议使用Common UploadFile 组件 ");
                log.info("尝试使用 Apache Common UploadFile 组件");
                MultipartRequest.init(uploadService.getBasePath(), JFinal.me().getConstants().getMaxPostSize(), JFinal.me().getConstants().getEncoding());
                MultipartRequest multipartRequest = new MultipartRequest(getRequest());
                List<UploadFile> files = multipartRequest.getFiles();
                files.forEach(f -> {
                    log.info("fetched file: {} ", f.getFileName());
                });
            }
        }

        File destFile = uploadService.upload(file.getFile(), objectCode, fieldCode);

        log.info("destFile.getPath : {}", destFile.getPath());

        String url = destFile.getPath().replaceFirst(uploadService.getBasePath(), "");
        Kv result = Kv.by("name", file.getFileName());
        result.set("value", url);
        result.set("url", UploadKit.previewUrl(url));
        renderJson(Ret.ok("data", result));
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
            return;
        }
        renderImageOrFile(uploadService.getFile(path));
    }
}
