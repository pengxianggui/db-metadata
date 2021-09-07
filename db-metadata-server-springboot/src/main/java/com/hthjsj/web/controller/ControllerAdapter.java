package com.hthjsj.web.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.serializer.SimplePropertyPreFilter;
import com.hthjsj.AnalysisConfig;
import com.hthjsj.analysis.meta.BusinessService;
import com.hthjsj.analysis.meta.DbMetaService;
import com.hthjsj.web.AppConst;
import com.hthjsj.web.ServiceManager;
import com.hthjsj.web.component.ComponentService;
import com.hthjsj.web.feature.FeatureService;
import com.hthjsj.web.kit.tree.TreeService;
import com.hthjsj.web.query.QueryHelper;
import com.hthjsj.web.upload.MultipartRequest;
import com.jfinal.core.JFinal;
import com.jfinal.kit.Kv;
import com.jfinal.upload.UploadFile;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.List;

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

    public HttpServletRequest getRequest() {
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
    }

    public HttpServletResponse getResponse() {
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
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
