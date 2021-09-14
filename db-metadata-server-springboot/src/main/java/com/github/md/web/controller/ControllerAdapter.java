package com.github.md.web.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.serializer.SimplePropertyPreFilter;
import com.github.md.analysis.AnalysisSpringUtil;
import com.github.md.web.ServiceManager;
import com.github.md.web.component.ComponentService;
import com.github.md.web.feature.FeatureService;
import com.github.md.web.upload.UploadService;
import com.github.md.analysis.meta.BusinessService;
import com.github.md.analysis.meta.DbMetaService;
import com.github.md.web.config.QuickJudge;
import com.github.md.web.kit.tree.TreeService;
import com.github.md.web.query.QueryHelper;
import com.github.md.analysis.kit.Kv;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;

/**
 * <p> @Date : 2021/9/7 </p>
 * <p> @Project : db-metadata-server-springboot</p>
 *
 * <p> @author konbluesky </p>
 */
public class ControllerAdapter {

    public DbMetaService metaService() {
        return ServiceManager.metaService();
    }

    public ComponentService componentService() {
        return ServiceManager.componentService();
    }

    public TreeService treeService() {
        return ServiceManager.treeService();
    }

    public BusinessService businessService() {
        return ServiceManager.businessService();
    }

    public FeatureService featureService() {
        return ServiceManager.featureService();
    }

    public UploadService uploadService() {
        return ServiceManager.fileService();
    }

    public QuickJudge quickJudge() {
        return AnalysisSpringUtil.getBean(QuickJudge.class);
    }

    public HttpServletRequest getRequest() {
        return quickJudge().getRequest();
    }

    public HttpServletResponse getResponse() {
        return quickJudge().getResponse();
    }

    public ParameterHelper parameterHelper() {
        return new ParameterHelper(getRequest());
    }

    public QueryHelper queryHelper() {
        return new QueryHelper(getRequest());
    }

    protected Kv toPage(int total, int index, int size) {
        return Kv.by("total", total).set("index", index).set("size", size);
    }

    public Object renderJsonExcludes(Object data, String... excludes) {
        if (excludes != null && excludes.length > 0) {

            SimplePropertyPreFilter simplePropertyPreFilter = new SimplePropertyPreFilter();
            simplePropertyPreFilter.getExcludes().addAll(Arrays.asList(excludes));

            return JSON.toJSONString(data,
                                     simplePropertyPreFilter,
                                     SerializerFeature.DisableCircularReferenceDetect,
                                     SerializerFeature.WriteDateUseDateFormat,
                                     SerializerFeature.WriteNullListAsEmpty,
                                     SerializerFeature.WriteMapNullValue,
                                     SerializerFeature.WriteNullStringAsEmpty);
        } else {
            return data;
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
    //    @Override
    //    public UploadFile getFile() {
    //        UploadFile file = null;
    //        try {
    //            file = super.getFile();
    //        } catch (RuntimeException e) {
    //            if (e.getMessage().contains("premature ending")) {
    //                log.error("该上传动作出现错误,可能是oreilly组件引起;建议使用Common UploadFile 组件 ");
    //                log.info("尝试使用 Apache Common UploadFile 组件");
    //                MultipartRequest.init(
    //                        AnalysisConfig.me().getProp().get(AppConst.UPLOAD_DIR, "/"),
    //                        JFinal.me().getConstants().getMaxPostSize(),
    //                        JFinal.me().getConstants().getEncoding());
    //
    //                MultipartRequest multipartRequest = new MultipartRequest(getRequest());
    //                List<UploadFile> files = multipartRequest.getFiles();
    //                files.forEach(f -> log.info("fetched file: {} ", f.getFileName()));
    //            }
    //        }
    //        return file;
    //    }
}
