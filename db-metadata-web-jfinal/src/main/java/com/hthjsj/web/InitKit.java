package com.hthjsj.web;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.Feature;
import com.google.common.collect.Sets;
import com.hthjsj.analysis.meta.IMetaField;
import com.hthjsj.analysis.meta.IMetaObject;
import com.jfinal.kit.Kv;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * <p> @Date : 2019/11/30 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
@Slf4j
public class InitKit {

    private static final InitKit me = new InitKit();

    private static final String OBJECT_CONFIG = "defaultObject.json";

    private static final String INSTANCE_JSON = "defaultInstance.json";

    private static JSONObject jsonObjectConfig = new JSONObject();

    private static JSONObject jsonInstanceConfig = new JSONObject();

    static {
        loadConfig();
    }

    public static InitKit me() {
        return me;
    }

    public static void loadConfig() {
        String objectConfig = UtilKit.loadContentByFile(OBJECT_CONFIG);
        String instanceConfig = UtilKit.loadContentByFile(INSTANCE_JSON);
        jsonObjectConfig = JSON.parseObject(objectConfig, Feature.OrderedField);
        jsonInstanceConfig = JSON.parseObject(instanceConfig, Feature.OrderedField);
    }

    public void init() {
        log.info("Start the configuration import.....");
        List<IMetaObject> lists = ServiceManager.metaService().findAll();
        resolveMetaObjects(lists);
    }

    public void resolveMetaObjects(List<IMetaObject> metaObjects) {
        for (IMetaObject metaObject : metaObjects) {
            resolveMetaObject(metaObject);
        }
    }

    /**
     * 1. 获取元对象列表
     * 2. 以元对象列表分解 defaultObject.json 配置
     * 3. 组装新配置
     * 4. 更新
     */
    public void resolveMetaObject(IMetaObject metaObject) {
        if (!jsonObjectConfig.containsKey(metaObject.code()))
            return;
        log.info("found the configuration of {}", metaObject.code());
        JSONObject self = jsonObjectConfig.getJSONObject(metaObject.code());
        JSONObject fields = self.containsKey("_fields") ? self.getJSONObject("_fields") : new JSONObject();
        try {
            if (!self.isEmpty()) {
                boolean hasModify = false;

                //元对象自身配置更新
                //取交集,用交集的key进行遍历
                Sets.SetView<String> selfIntersectionKeys = Sets.intersection(metaObject.dataMap().keySet(), self.keySet());
                for (String selfFieldKey : selfIntersectionKeys) {
                    log.info("merge Object Self {} - {} ", metaObject.code(), selfFieldKey);
                    //TODO 因mysql jdbc 无法直接操作JSON类型,需要使用Kv 接收,用kv.toJson -> String 后存入  ||HACK 写法
                    if (self.get(selfFieldKey) instanceof JSONObject) {
                        Kv config = UtilKit.getKv(((JSONObject) self.get(selfFieldKey)).toJSONString());
                        metaObject.dataMap().merge(selfFieldKey, config.toJson(), (oldValue, newValue) -> newValue);
                    } else {
                        metaObject.dataMap().merge(selfFieldKey, self.get(selfFieldKey), (oldValue, newValue) -> newValue);
                    }
                    hasModify = true;
                }

                //元子段自身配置更新
                if (!fields.isEmpty()) {
                    JSONObject field = null;
                    for (IMetaField f : metaObject.fields()) {
                        field = fields.containsKey(f.fieldCode()) ? fields.getJSONObject(f.fieldCode()) : new JSONObject();
                        Sets.SetView<String> intersectionKeys = Sets.intersection(f.dataMap().keySet(), field.keySet());
                        for (String intersectionKey : intersectionKeys) {
                            log.info("merge Field{} - {} ", metaObject.code(), intersectionKey);
                            f.dataMap().merge(intersectionKey, field.get(intersectionKeys), (oldValue, newValue) -> newValue);
                            hasModify = true;
                        }
                    }
                }
                //有变化后 进行更新;
                if (hasModify) {
                    log.info("update new config by {} ", metaObject.code());
                    ServiceManager.metaService().updateMetaObject(metaObject);
                }
            }
        } catch (Exception e) {
            log.error("Error loading default configuration in [defaultObject.json]");
            log.error(e.getMessage(), e);
        }
    }

    public void resolveMetaComponentInstance() {
        List<IMetaObject> lists = ServiceManager.metaService().findAll();
//        List<Component> components = ServiceManager.componentService();

    }
}

