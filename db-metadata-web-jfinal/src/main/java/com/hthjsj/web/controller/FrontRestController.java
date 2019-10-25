package com.hthjsj.web.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.serializer.SimplePropertyPreFilter;
import com.jfinal.core.Controller;
import com.jfinal.core.JFinal;
import com.jfinal.kit.Kv;
import com.jfinal.kit.Ret;

import java.util.Arrays;

/**
 * <p> @Date : 2019/10/9 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
public abstract class FrontRestController extends Controller implements FrontRest {

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
     * FIXME
     * f=en&ef=en 时候 会序列化出columnNames,columnValues,columns字段
     * 原因:f=en&ef=en 即指定又排除时,会触发Record的hashmap 序列化机制,所以序列化除了的Record自身的字段;
     * 解决:在源头解决
     *
     * @param data
     * @param excludes
     */
    void renderJsonExcludes(Object data, String... excludes) {
        if (excludes != null && excludes.length > 0) {

            SimplePropertyPreFilter simplePropertyPreFilter = new SimplePropertyPreFilter();
            simplePropertyPreFilter.getExcludes().addAll(Arrays.asList(excludes));

            renderJson(JSON.toJSONString(data,
                                         simplePropertyPreFilter,
                                         SerializerFeature.DisableCircularReferenceDetect,
                                         SerializerFeature.WriteDateUseDateFormat,
                                         SerializerFeature.WriteNullListAsEmpty,
                                         SerializerFeature.WriteMapNullValue,
                                         SerializerFeature.WriteNullStringAsEmpty,
                                         SerializerFeature.WriteNonStringValueAsString));
        } else {
            renderJson(data);
        }
    }

    Kv toPage(int total, int index, int size) {
        return Kv.by("total", total).set("index", index).set("size", size);
    }
}
