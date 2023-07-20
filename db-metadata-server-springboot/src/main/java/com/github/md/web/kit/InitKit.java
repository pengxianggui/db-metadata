package com.github.md.web.kit;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.Feature;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.github.md.analysis.AnalysisSpringUtil;
import com.github.md.analysis.SpringAnalysisManager;
import com.github.md.analysis.component.ComponentType;
import com.github.md.analysis.db.SnowFlake;
import com.github.md.analysis.db.Table;
import com.github.md.analysis.kit.Kv;
import com.github.md.analysis.meta.IMetaField;
import com.github.md.analysis.meta.IMetaObject;
import com.github.md.analysis.meta.MetaData;
import com.github.md.web.AppConst;
import com.github.md.web.ServiceManager;
import com.github.md.web.config.QuickJudge;
import com.github.md.web.feature.FeatureType;
import com.github.md.web.ui.MetaFieldViewAdapter;
import com.github.md.web.ui.MetaObjectViewAdapter;
import com.github.md.web.ui.UIManager;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.datasource.init.ScriptUtils;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 只初始化系统表对应的元数据和实例配置, 参考 {@link AppConst.INNER_TABLE}
 * <p> @Date : 2019/11/30 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
@Slf4j
public class InitKit {

    private static final InitKit me = new InitKit();

    private static final String OBJECT_CONFIG = "data/reset/buildInObject.json"; // 系统内置的元数据

    private static final String INSTANCE_JSON = "data/reset/buildInInstance.json"; // 系统内置的实例配置数据

    private static final String FEATURE_JSON = "data/reset/buildInFeature.json"; // 系统内置的功能配置

    private static final String BIZ_DATA_SQL = "data/reset/buildInSysData.sql"; // 内建业务数据

    private static JSONObject jsonObjectConfig = new JSONObject();

    private static JSONObject jsonInstanceConfig = new JSONObject();

    private static JSONArray jsonFeatureConfig = new JSONArray();

    static {
        loadConfig();
    }

    public static InitKit me() {
        return me;
    }

    private static void loadConfig() {
        String objectConfig = UtilKit.loadConfigByFile(OBJECT_CONFIG);
        String instanceConfig = UtilKit.loadConfigByFile(INSTANCE_JSON);
        String featureConfig = UtilKit.loadConfigByFile(FEATURE_JSON);
        jsonObjectConfig = JSON.parseObject(objectConfig, Feature.OrderedField);
        jsonInstanceConfig = JSON.parseObject(instanceConfig, Feature.OrderedField);
        jsonFeatureConfig = JSON.parseArray(featureConfig);
    }

    /**
     * 更新元对象配置(依据buildInObject.json, 文件中未定义的元对象将不会被更新)
     *
     * @return
     */
    public InitKit updateMetaObjectConfig() {
        log.info("即将依据{}对内置元对象和元字段进行更新操作...", OBJECT_CONFIG);
        List<IMetaObject> lists = ServiceManager.metaService().findAll();
        updateMetaObjects(lists);
        return this;
    }

    /**
     * 依据元对象配置更新其所有已有的容器实例配置。即通过元对象、原字段逻辑配置，推导一些实例配置，减轻buildInInstance.json工作量，防止重复
     * 配置一些东西。
     *
     * @return
     */
    public InitKit updateInstanceConfigByMetaObject() {
        log.info("即将对内置实例配置进行更新，更新依据为{}...", OBJECT_CONFIG);
        for (String objectCode : jsonObjectConfig.keySet()) {
            JSONObject self = jsonObjectConfig.getJSONObject(objectCode);
            JSONObject fields = self.containsKey("_fields") ? self.getJSONObject("_fields") : new JSONObject();

            IMetaObject metaObject = ServiceManager.metaService().findByCode(objectCode);

            List<ComponentType> existTypes = ServiceManager.componentService().loadTypesByObjectCode(metaObject.code());
            for (ComponentType type : existTypes) {
                MetaObjectViewAdapter metaObjectViewAdapter = UIManager.getView(metaObject, type);

                for (String fieldCode : fields.keySet()) {
                    MetaFieldViewAdapter metaFieldViewAdapter = metaObjectViewAdapter.getFieldAdapter(fieldCode);
                    if (metaFieldViewAdapter == null) {
                        log.debug(String.format("元字段[%s > %s]在容器[%s]下无UI配置，请确认", objectCode, fieldCode, type.getCode()));
                        continue;
                    }
                    UIManager.update(metaFieldViewAdapter, metaObjectViewAdapter.getComponent().componentType());
                }
            }
        }
        return this;
    }

    /**
     * 更新组件实例配置(依据buildInInstance.json, 文件中未定义的元对象将不会被更新)
     *
     * @return
     */
    public InitKit updateInstanceConfig() {
        log.info("即将对内置实例配置进行更新，更新依据为{}...", INSTANCE_JSON);
        List<IMetaObject> lists = ServiceManager.metaService().findAll();
        for (IMetaObject metaObject : lists) {
            // 应当由buildInInstance.json中的配置决定要更新那些容器组件
            for (ComponentType componentType : getPredefinedComponentType(metaObject.code())) {
                updateMetaComponentInstance(metaObject, componentType);
            }
        }
        return this;
    }

    /**
     * 更新功能配置(依据buildInFeature.json，文件中未定义的功能不会被更新)。入库时采用先删后插
     *
     * @return
     */
    public InitKit updateFeatureConfig() {
        log.info("即将对内置功能进行更新，更新依据为{}...", FEATURE_JSON);

        for (Object item : jsonFeatureConfig) {
            JSONObject jsonItem = (JSONObject) item;
            String typeStr = jsonItem.getString("type");
            FeatureType type = FeatureType.V(typeStr);
            String name = jsonItem.getString("name");
            String code = jsonItem.getString("code");
            JSONObject configJson = jsonItem.getJSONObject("config");
            MetaData config = JSON.parseObject(JSONObject.toJSONString(configJson), type.getConfigEntity());

            boolean flag = ServiceManager.featureService().saveFeature(type, name, code, config, true);
            AssertKit.isTrue(flag, "导入系统内置功能时发生错误, 数据未导入库， 功能编码:%s", code);
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
     * 2. 以元对象列表分解 buildInObject.json 配置
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
            log.error("Error loading default configuration in [buildInObject.json]");
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
//            Kv fieldConfig = UtilKit.getKv(fieldsConf.getString(metaField.fieldCode()));

            Kv fieldConfig = UtilKit.getKv(JSONObject.toJSONString(fieldsConf.getJSONObject(metaField.fieldCode()),
                    SerializerFeature.WriteMapNullValue)); // 保留json中的空值键值对
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
     * 获取buildInInstance.json中指定元对象编码预定义的组件类型
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

    /**
     * 初始化系统元数据: 元对象、元字段、实例配置。依据: {@link AppConst.INNER_TABLE} 和 文件 buildInInstance.json.
     * <p>
     * 注意：方法内不会导入buildInInstance.json文件中定义的UI实例配置，只是依据 {@link AppConst.INNER_TABLE} 确定要为哪些表初始化元对象和元字段，
     * 然后再依据buildInInstance.json含有容器组件声明，确定要为这些元对象生成哪些容器下的实例配置, 其实例配置为自动计算生成，而不会使用buildInInstance.json内容进行覆盖。
     */
    public void initSysMeta() {
        log.info("即将执行内置元对象、内置元字段、内置实例配置的初始化(依据自动计算)...");
        QuickJudge quickJudge = AnalysisSpringUtil.getBean(QuickJudge.class);
        String mainDB = quickJudge.mainDbStr();
        Set<String> innerTables = Arrays.stream(AppConst.INNER_TABLE.values()).map(AppConst.INNER_TABLE::getTableName).collect(Collectors.toSet());
        List<Table> sysTables = ServiceManager.mysqlService().showTables(quickJudge.mainDbStr())
                // 过滤出内置表
                .stream().filter(t -> innerTables.contains(t.getTableName())).collect(Collectors.toList());

        for (Table t : sysTables) {
            log.info("init table:{} - {}", t.getTableName(), t.getTableComment());
            IMetaObject metaObject = ServiceManager.metaService().importFromTable(mainDB, t.getTableName());
            metaObject.dataMap().put("build_in", true);
            ServiceManager.metaService().saveMetaObject(metaObject, true);

            // 根据 buildInInstance.json 中定义了的元对象 初始化系统表元对象对应的容器组件
            for (ComponentType type : InitKit.me().getPredefinedComponentType(metaObject.code())) {
                MetaObjectViewAdapter metaObjectIViewAdapter = UIManager.getSmartAutoView(metaObject, type);
                // 初始化实例配置
                ServiceManager.componentService().newObjectConfig(
                        metaObjectIViewAdapter.getComponent(),
                        metaObject,
                        metaObjectIViewAdapter.getInstanceConfig());
            }
        }
    }

    /**
     * 刷新 除元数据(元对象、元字段、组件默认配置、组件全局配置、功能) 外的其他内建业务数据
     *
     * @return
     */
    public InitKit updateBizData() {
        log.info("即将对内置业务数据(接口资源、权限、权限模块、字典、菜单、路由、角色、系统设置)进行更新，更新依据为{}...", BIZ_DATA_SQL);
        String sql = UtilKit.loadConfigByFile(BIZ_DATA_SQL);

        Resource sqlResource = new ByteArrayResource(sql.getBytes());
        SpringAnalysisManager.me().dbMain().execute((conn -> {
            try {
                ScriptUtils.executeSqlScript(conn, sqlResource);
                return true;
            } catch (Exception e) {
                conn.rollback();
                throw new RuntimeException(e.getMessage());
            }
        }));
        return this;
    }

    /**
     * 初始化账号ROOT
     */
    public void initROOTAccount() {
        IMetaObject metaObject = ServiceManager.metaService().findByCode(AppConst.INNER_TABLE.META_USER.getTableName());
        MetaData metaData = new MetaData();
        metaData.set("id", SnowFlake.me().nextId())
                .set("username", "ROOT")
                .set("password", PassKit.encryptPass())
                .set("nickname", "ROOT")
                .set("realname", "ROOT")
                .set("build_in", true)
                .set("created_time", new Date())
                .set("created_by", "SYSTEM");
        ServiceManager.businessService().saveData(metaObject, metaData);
    }
}

