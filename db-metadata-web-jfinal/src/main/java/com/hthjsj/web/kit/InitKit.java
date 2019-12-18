package com.hthjsj.web.kit;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.Feature;
import com.google.common.collect.Sets;
import com.hthjsj.analysis.component.ComponentType;
import com.hthjsj.analysis.meta.IMetaField;
import com.hthjsj.analysis.meta.IMetaObject;
import com.hthjsj.web.ServiceManager;
import com.hthjsj.web.ui.MetaObjectViewAdapter;
import com.hthjsj.web.ui.UIManager;
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
        String objectConfig = UtilKit.loadConfigByFile(OBJECT_CONFIG);
        String instanceConfig = UtilKit.loadConfigByFile(INSTANCE_JSON);
        jsonObjectConfig = JSON.parseObject(objectConfig, Feature.OrderedField);
        jsonInstanceConfig = JSON.parseObject(instanceConfig, Feature.OrderedField);
    }

    /**
     * 加载元对象配置
     *
     * @return
     */
    public InitKit importMetaObjectConfig() {
        log.info("Start the MetaObject configuration import.....");
        List<IMetaObject> lists = ServiceManager.metaService().findAll();
        resolveMetaObjects(lists);
        return this;
    }

    /**
     * 加载组件实例配置
     *
     * @return
     */
    public InitKit importInstanceConfig() {
        log.info("Start the MetaObject configuration import.....");
        List<IMetaObject> lists = ServiceManager.metaService().findAll();
        for (IMetaObject metaObject : lists) {
            //TODO 只自动覆盖FORMVIEW
            resolveMetaComponentInstance(metaObject, ComponentType.FORMVIEW);
        }
        return this;
    }

    public void resolveMetaObjects(List<IMetaObject> metaObjects) {
        for (IMetaObject metaObject : metaObjects) {
            resolveMetaObject(metaObject);
        }
    }

    /**
     * <pre>
     * 1. 获取元对象列表
     * 2. 以元对象列表分解 defaultObject.json 配置
     * 3. 分解元对象自身
     *      3.1 取元对象数据 key 与 json key 交集 ,防止无效key混入map,导致更新失败
     *      3.2 merge
     * 4. 分解元字段
     *      4.1-2 同上
     *  </pre>
     */
    public void resolveMetaObject(IMetaObject metaObject) {
        if (!jsonObjectConfig.containsKey(metaObject.code()))
            return;
        log.info("found the object configuration of {}", metaObject.code());
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
                        Kv config = UtilKit.getKv(self.getString(selfFieldKey));
                        metaObject.dataMap().merge(selfFieldKey, config.toJson(), (oldValue, newValue) -> newValue);
                    } else {
                        metaObject.dataMap().merge(selfFieldKey, self.get(selfFieldKey), (oldValue, newValue) -> newValue);
                    }
                    hasModify = true;
                }

                //元子段自身配置更新
                if (!fields.isEmpty()) {
                    JSONObject fieldJson = null;
                    log.info("Begin parsing the property configuration");
                    for (IMetaField metaField : metaObject.fields()) {

                        if (!fields.containsKey(metaField.fieldCode()))
                            continue;

                        fieldJson = fields.getJSONObject(metaField.fieldCode());
                        Sets.SetView<String> intersectionKeys = Sets.intersection(metaField.dataMap().keySet(), fieldJson.keySet());
                        if (intersectionKeys.isEmpty())
                            continue;

                        log.info("Find {} properties that you can merge : {}", intersectionKeys.size(), intersectionKeys);
                        for (String intersectionKey : intersectionKeys) {
                            log.info("merge Field{} - {} ", metaObject.code(), intersectionKey);

                            if (fieldJson.get(intersectionKey) instanceof JSONObject) {
                                Kv orgin = UtilKit.getKv(metaField.config());
                                Kv config = UtilKit.getKv(fieldJson.getString(intersectionKey));
                                Kv result = Kv.create().set(UtilKit.deepMerge(orgin, config, true));
                                metaField.dataMap().merge(intersectionKey, result.toJson(), (oldValue, newValue) -> newValue);
                            } else {
                                metaField.dataMap().merge(intersectionKey, fieldJson.get(intersectionKey), (oldValue, newValue) -> newValue);
                            }
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

    public void resolveMetaComponentInstance(IMetaObject metaObject, ComponentType componentType) {
        if (!jsonInstanceConfig.containsKey(metaObject.code()))
            return;
        log.info("found the component instance configuration of {} - {}", metaObject.code(), componentType);
        //识别元对象
        JSONObject objectSelf = jsonInstanceConfig.getJSONObject(metaObject.code());
        if (objectSelf == null || objectSelf.isEmpty() || !objectSelf.containsKey(componentType.getCode()))
            return;
        //识别组件
        JSONObject component = objectSelf.getJSONObject(componentType.getCode());

        MetaObjectViewAdapter metaObjectViewAdapter = UIManager.getView(metaObject, componentType);
        for (IMetaField metaField : metaObject.fields()) {
            //json中未配置的field 直接放行;
            if (!component.containsKey(metaField.fieldCode()))
                continue;

            //TODO 因mysql jdbc 无法直接操作JSON类型,需要使用Kv 接收,用kv.toJson -> String 后存入  ||HACK 写法
            //WARN 仅支持配置config字段
            if (component.get(metaField.fieldCode()) instanceof JSONObject) {
                Kv config = UtilKit.getKv(component.getString(metaField.fieldCode()));
                if (!config.isEmpty()) {
                    UIManager.update(metaObjectViewAdapter.getFieldAdapter(metaField.fieldCode()), config);
                    log.info("update new Component Instance Config by {} - {} - {} ", componentType.getCode(), metaObject.code(), metaField.fieldCode());
                }
            }
        }
    }
}

