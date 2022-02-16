package com.github.md.web.kit;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.Feature;
import com.github.md.web.AppConst;
import com.github.md.web.ServiceManager;
import com.github.md.web.ui.MetaFieldViewAdapter;
import com.github.md.web.ui.MetaObjectViewAdapter;
import com.github.md.web.ui.UIManager;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.github.md.analysis.component.ComponentType;
import com.github.md.analysis.meta.IMetaField;
import com.github.md.analysis.meta.IMetaObject;
import com.github.md.analysis.kit.Kv;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 只初始化系统表对应的元数据和实例配置, 参考 {@link AppConst#SYS_TABLE}
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

    private static void loadConfig() {
        String objectConfig = UtilKit.loadConfigByFile(OBJECT_CONFIG);
        String instanceConfig = UtilKit.loadConfigByFile(INSTANCE_JSON);
        jsonObjectConfig = JSON.parseObject(objectConfig, Feature.OrderedField);
        jsonInstanceConfig = JSON.parseObject(instanceConfig, Feature.OrderedField);
    }

    /**
     * 更新元对象配置(依据defaultObject.json, 文件中未定义的元对象将不会被更新)
     *
     * @return
     */
    public InitKit updateMetaObjectConfig() {
        log.info("Start the MetaObject configuration import.....");
        List<IMetaObject> lists = ServiceManager.metaService().findAll();
        updateMetaObjects(lists);
        return this;
    }

    /**
     * 更新组件实例配置(依据defaultInstance.json, 文件中未定义的元对象将不会被更新)
     *
     * @return
     */
    public InitKit updateInstanceConfig() {
        log.info("Start the MetaObject configuration import.....");
        List<IMetaObject> lists = ServiceManager.metaService().findAll();
        for (IMetaObject metaObject : lists) {
            // 应当由defaultInstance.json中的配置决定要更新那些容器组件
            for (ComponentType componentType : getPredefinedComponentType(metaObject.code())) {
                updateMetaComponentInstance(metaObject, componentType);
            }
        }
        return this;
    }

    private void updateMetaObjects(List<IMetaObject> metaObjects) {
        for (IMetaObject metaObject : metaObjects) {
            updateMetaObject(metaObject);
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
    private void updateMetaObject(IMetaObject metaObject) {
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

    /**
     * 解析并更新元对象实例配置(包括元字段实例配置)
     *
     * @param metaObject
     * @param componentType
     */
    private void updateMetaComponentInstance(IMetaObject metaObject, ComponentType componentType) {
        if (!jsonInstanceConfig.containsKey(metaObject.code())) {
            return;
        }
        log.info("found the component instance configuration of {} - {}", metaObject.code(), componentType);

        //识别元对象
        JSONObject objectSelf = jsonInstanceConfig.getJSONObject(metaObject.code());
        if (objectSelf == null || objectSelf.isEmpty() || !objectSelf.containsKey(componentType.getCode())) {
            return;
        }

        //识别组件
        JSONObject component = objectSelf.getJSONObject(componentType.getCode());
        MetaObjectViewAdapter metaObjectViewAdapter = UIManager.getView(metaObject, componentType);

        if (component.containsKey("config") && (component.get("config") instanceof JSONObject)) {
            // 单独更新容器实例配置
            Kv containerConfig = UtilKit.getKv(component.getString("config"));
            if (!containerConfig.isEmpty()) {
                Kv original = metaObjectViewAdapter.getInstanceConfig().getObjectConfig();
                //merge操作
                Kv mergedResult = Kv.create().set(UtilKit.deepMerge(original, containerConfig, true));
                ServiceManager.componentService().updateObjectConfigSelf(
                        metaObjectViewAdapter.getComponent().componentType(),
                        metaObjectViewAdapter.getMetaObject(),
                        mergedResult
                );
            }
        }

        if (!component.containsKey("_fields") || !(component.get("_fields") instanceof JSONObject)) {
            return;
        }

        JSONObject fieldsConf = component.getJSONObject("_fields");

        for (IMetaField metaField : metaObject.fields()) {
            //json中未配置的field 直接放行;
            if (!fieldsConf.containsKey(metaField.fieldCode()) || !(fieldsConf.get(metaField.fieldCode()) instanceof JSONObject)) {
                continue;
            }

            //TODO 因mysql jdbc 无法直接操作JSON类型,需要使用Kv 接收,用kv.toJson -> String 后存入  ||HACK 写法
            //WARN 仅支持配置config字段
            Kv fieldConfig = UtilKit.getKv(fieldsConf.getString(metaField.fieldCode()));
            if (!fieldConfig.isEmpty()) {
                MetaFieldViewAdapter fieldAdapter = metaObjectViewAdapter.getFieldAdapter(metaField.fieldCode());
                if (fieldAdapter != null) {
                    UIManager.update(fieldAdapter, fieldConfig);
                    log.info("update new Component Instance Config by {} - {} - {} ", componentType.getCode(), metaObject.code(), metaField.fieldCode());
                }
            }
        }
    }

    /**
     * 获取defaultInstance.json中指定元对象编码预定义的组件类型
     *
     * @param code
     * @return
     */
    public List<ComponentType> getPredefinedComponentType(String code) {
        List<ComponentType> componentTypes = Lists.newArrayList();
        if (!jsonInstanceConfig.containsKey(code)) {
            return componentTypes;
        }

        return jsonInstanceConfig.getJSONObject(code).keySet().stream()
                .map(type -> ComponentType.V(type)).collect(Collectors.toList());
    }
}

