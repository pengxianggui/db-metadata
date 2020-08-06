package com.hthjsj.web.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.serializer.SimplePropertyPreFilter;
import com.google.common.collect.Lists;
import com.google.common.io.Files;
import com.hthjsj.analysis.meta.DbMetaService;
import com.hthjsj.web.ServiceManager;
import com.hthjsj.web.component.ComponentService;
import com.hthjsj.web.feature.FeatureService;
import com.hthjsj.web.jfinal.render.PictureRender;
import com.hthjsj.web.kit.tree.TreeService;
import com.jfinal.core.Controller;
import com.jfinal.core.JFinal;
import com.jfinal.kit.Kv;
import com.jfinal.kit.Ret;

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
public class FrontRestController extends Controller implements FrontRest {

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

    public void api() {
        renderJson(JFinal.me().getAllActionKeys());
    }

    @Override
    public void index() {
        list();
    }

    @Override
    public void toAdd() {
        renderJson(faildMsgInfo());
    }

    @Override
    public void doAdd() {
        renderJson(faildMsgInfo());
    }

    @Override
    public void toUpdate() {
        renderJson(faildMsgInfo());
    }

    @Override
    public void doUpdate() {
        renderJson(faildMsgInfo());
    }

    @Override
    public void detail() {
        renderJson(faildMsgInfo());
    }

    @Override
    public void delete() {
        renderJson(faildMsgInfo());
    }

    @Override
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

    Kv toPage(int total, int index, int size) {
        return Kv.by("total", total).set("index", index).set("size", size);
    }

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
}
