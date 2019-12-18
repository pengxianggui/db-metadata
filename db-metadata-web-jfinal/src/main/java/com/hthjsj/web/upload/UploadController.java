package com.hthjsj.web.upload;

import com.google.common.base.Preconditions;
import com.hthjsj.analysis.meta.DbMetaService;
import com.hthjsj.analysis.meta.IMetaField;
import com.hthjsj.analysis.meta.IMetaObject;
import com.hthjsj.web.ServiceManager;
import com.hthjsj.web.kit.UtilKit;
import com.hthjsj.web.query.QueryHelper;
import com.jfinal.core.Controller;
import com.jfinal.core.JFinal;
import com.jfinal.kit.Kv;
import com.jfinal.kit.Ret;
import com.jfinal.upload.UploadFile;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.util.Optional;

/**
 * <p> @Date : 2019/12/4 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
@Slf4j
public class UploadController extends Controller {

    /**
     * TODO 透传文件给前端
     */
    public void index() {
        String filename = getPara();
        Preconditions.checkNotNull(filename);
        filename += ".json";
        if (JFinal.me().getConstants().getDevMode()) {
            Optional<String> result = Optional.ofNullable(UtilKit.loadConfigByFile(filename));
            if (result.isPresent()) {
                renderJson(Ret.ok("data", UtilKit.getKv(UtilKit.loadConfigByFile(filename))));
            } else {
                renderJson(Ret.fail().set("msg", "[" + filename + "]无法成功读取该文件内容!"));
            }
        } else {
            renderJson(Ret.fail().set("msg", "开发功能已关闭,不能直接通过本接口获得[" + filename + "]文件内容"));
        }
    }

    /**
     * param objectCode
     * param fieldCode
     * param file
     */
    public void upload() {
        QueryHelper queryHelper = new QueryHelper(this);
        String objectCode = queryHelper.getObjectCode();
        String fieldCode = queryHelper.getFieldCode();

        IMetaField metaField = ServiceManager.metaService().findFieldByCode(objectCode, fieldCode);

        Preconditions.checkArgument(metaField.configParser().isFile(), "对象{}-字段{}未配置文件属性不正确", objectCode, fieldCode);

        UploadFile file = getFile();

        UploadService uploadService = ServiceManager.fileService();
        File destFile = uploadService.upload(file.getFile(), objectCode, fieldCode);

        log.info("destFile.getPath : {}", destFile.getPath());

        Kv result = Kv.by("name", file.getFileName()).set("url", destFile.getPath().replaceFirst(uploadService.getBasePath(), ""));
        renderJson(Ret.ok("data", result));
    }

    /**
     * param objectCode
     * param fieldCode
     * param 业务记录 id
     */
    public void down() {
        QueryHelper queryHelper = new QueryHelper(this);
        String objectCode = queryHelper.getObjectCode();
        String fieldCode = queryHelper.getFieldCode();
        String id = getPara("id");
        //TODO 2次查询,待优化
        DbMetaService dbMetaService = ServiceManager.metaService();
        IMetaObject metaObject = dbMetaService.findByCode(objectCode);
        IMetaField metaField = dbMetaService.findFieldByCode(objectCode, fieldCode);

        Preconditions.checkNotNull(id, "必须指定业务记录id");

        String filePath = dbMetaService.findDataFieldById(metaObject, metaField, id);

        Preconditions.checkNotNull(filePath, "未找到可下载的文件地址");

        renderFile(ServiceManager.fileService().getFile(filePath));
    }
}
