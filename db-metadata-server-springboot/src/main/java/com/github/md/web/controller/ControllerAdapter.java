package com.github.md.web.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.serializer.SimplePropertyPreFilter;
import com.github.md.analysis.AnalysisSpringUtil;
import com.github.md.analysis.kit.Kv;
import com.github.md.analysis.meta.BusinessService;
import com.github.md.analysis.meta.DbMetaService;
import com.github.md.web.ServiceManager;
import com.github.md.web.component.ComponentService;
import com.github.md.web.config.QuickJudge;
import com.github.md.web.feature.FeatureService;
import com.github.md.web.kit.tree.TreeService;
import com.github.md.web.query.QueryHelper;
import com.github.md.web.upload.UploadService;

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
}
