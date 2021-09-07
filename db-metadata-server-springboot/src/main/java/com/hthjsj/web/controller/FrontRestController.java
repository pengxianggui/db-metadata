package com.hthjsj.web.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.serializer.SimplePropertyPreFilter;
import com.google.common.collect.Lists;
import com.google.common.io.Files;
import com.hthjsj.AnalysisConfig;
import com.hthjsj.analysis.meta.BusinessService;
import com.hthjsj.analysis.meta.DbMetaService;
import com.hthjsj.web.AppConst;
import com.hthjsj.web.ServiceManager;
import com.hthjsj.web.component.ComponentService;
import com.hthjsj.web.feature.FeatureService;
import com.hthjsj.web.jfinal.render.PictureRender;
import com.hthjsj.web.kit.tree.TreeService;
import com.hthjsj.web.upload.MultipartRequest;
import com.jfinal.core.Controller;
import com.jfinal.core.JFinal;
import com.jfinal.core.NotAction;
import com.jfinal.kit.Kv;
import com.jfinal.kit.Ret;
import com.jfinal.upload.UploadFile;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * <p> @Date : 2019/10/9 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
@Slf4j
public class FrontRestController extends Controller {

    protected DbMetaService metaService() {
        return ServiceManager.metaService();
    }

    protected ComponentService componentService() {
        return ServiceManager.componentService();
    }

    protected FeatureService featureService() {
        return ServiceManager.featureService();
    }

    protected TreeService treeService() {
        return ServiceManager.treeService();
    }

    protected BusinessService businessService() {
        return ServiceManager.businessService();
    }

    public void api() {
        renderJson(JFinal.me().getAllActionKeys());
    }

    public void index() {
        list();
    }

    public void toAdd() {
        renderJson(faildMsgInfo());
    }

    public void doAdd() {
        renderJson(faildMsgInfo());
    }

    public void toUpdate() {
        renderJson(faildMsgInfo());
    }

    public void doUpdate() {
        renderJson(faildMsgInfo());
    }

    public void detail() {
        renderJson(faildMsgInfo());
    }

    public void delete() {
        renderJson(faildMsgInfo());
    }

    public void list() {
        renderJson(faildMsgInfo());
    }

    Ret faildMsgInfo() {
        Ret ret = Ret.fail();
        if (JFinal.me().getConstants().getDevMode()) {
            ret.set("request_uri", getRequest().getRequestURI());
        }
        ret.set("msg", "not implementation!");
        return ret;
    }

    /**
     * 在序列化json时支持排除字段
     *
     * @param data
     * @param excludes
     */
    @NotAction
    protected void renderJsonExcludes(Object data, String... excludes) {
        if (excludes != null && excludes.length > 0) {

            SimplePropertyPreFilter simplePropertyPreFilter = new SimplePropertyPreFilter();
            simplePropertyPreFilter.getExcludes().addAll(Arrays.asList(excludes));

            renderJson(JSON.toJSONString(data,
                                         simplePropertyPreFilter,
                                         SerializerFeature.DisableCircularReferenceDetect,
                                         SerializerFeature.WriteDateUseDateFormat,
                                         SerializerFeature.WriteNullListAsEmpty,
                                         SerializerFeature.WriteMapNullValue,
                                         SerializerFeature.WriteNullStringAsEmpty));
        } else {
            renderJson(data);
        }
    }

    @NotAction
    protected Kv toPage(int total, int index, int size) {
        return Kv.by("total", total).set("index", index).set("size", size);
    }

    @NotAction
    public void renderImageOrFile(File downloadFile) {
        List<String> anyImageTypes = Lists.newArrayList("jpg", "png", "gif", "jpeg");
        String ext = Files.getFileExtension(downloadFile.getName());
        try {
            if (anyImageTypes.contains(ext)) {
                byte[] fileCtx = Files.toByteArray(downloadFile);
                render(new PictureRender(fileCtx, Files.getFileExtension(downloadFile.getName())));
            } else {
                renderFile(downloadFile);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * <pre>
     *     当与springboot 集成时上传文件会出现异常(无论使用tomcat容器或jetty容器)
     *     java.lang.RuntimeException: java.io.IOException: Corrupt form data: premature ending
     * </pre>
     * 因此将此逻辑提到 FrontRestController类，做一个兼容处理。
     * FIXME 此兼容处理还比较牵强，不够健壮和优雅， 而且其他 super.getFile 并未收到此影响。 需要从根本上解决
     *
     * @return
     */
    @Override
    public UploadFile getFile() {
        UploadFile file = null;
        try {
            file = super.getFile();
        } catch (RuntimeException e) {
            if (e.getMessage().contains("premature ending")) {
                log.error("该上传动作出现错误,可能是oreilly组件引起;建议使用Common UploadFile 组件 ");
                log.info("尝试使用 Apache Common UploadFile 组件");
                MultipartRequest.init(
                        AnalysisConfig.me().getProp().get(AppConst.UPLOAD_DIR, "/"),
                        JFinal.me().getConstants().getMaxPostSize(),
                        JFinal.me().getConstants().getEncoding());

                MultipartRequest multipartRequest = new MultipartRequest(getRequest());
                List<UploadFile> files = multipartRequest.getFiles();
                files.forEach(f -> log.info("fetched file: {} ", f.getFileName()));
            }
        }
        return file;
    }
}
