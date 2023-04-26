package com.github.md.web.component;

import com.alibaba.fastjson.JSON;
import com.github.md.analysis.SpringAnalysisManager;
import com.github.md.analysis.component.Component;
import com.github.md.analysis.component.ComponentType;
import com.github.md.analysis.db.SnowFlake;
import com.github.md.analysis.kit.Kv;
import com.github.md.analysis.meta.IMetaField;
import com.github.md.analysis.meta.IMetaObject;
import com.github.md.web.kit.DateKit;
import com.github.md.web.kit.Okv;
import com.github.md.web.kit.UtilKit;
import com.github.md.web.ui.ComponentInstanceConfig;
import com.github.md.web.user.User;
import com.github.md.web.user.UserThreadLocal;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Record;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

/**
 * <p> Class title: </p>
 * <p> @Describe: </p>
 * <p> @Date : 2019/10/10 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
@Transactional
@Service
public class ComponentService {

    private static final String META_COMPONENT = "meta_component";

    private static final String META_COMPONENT_INSTANCE = META_COMPONENT + "_instance";

    /**
     * 获取某个组件最新版本的配置
     *
     * @param componentCode
     * @return
     */
    public Record loadDefault(String componentCode) {
        if (StrKit.isBlank(componentCode)) {
            throw new ComponentException("必须指定组件 Code:%s", componentCode);
        }
        Record record = loadLatestComponentRecord(componentCode);
        if (record == null) {
            throw new ComponentException("未找到Code[%s]的组件", componentCode);
        }
        return record;
    }

    /**
     * 获取指定组件指定版本的配置
     *
     * @param componentCode
     * @param version
     * @return
     */
    public Record loadDefaultByVersion(String componentCode, int version) {
        if (StrKit.isBlank(componentCode)) {
            throw new ComponentException("必须指定组件 Code:%s", componentCode);
        }
        Record record = SpringAnalysisManager.me().dbMain().findFirst("select * from " + META_COMPONENT + " where code=? and version=?", componentCode, version);
        if (record == null) {
            throw new ComponentException("未找到Code[%s]的组件", componentCode);
        }
        return record;
    }

    /**
     * 若指定组件的最新版本不存在, 则使用提供配置的创建一个并保存
     *
     * @param componentCode 指定组件
     * @param config        后备配置
     * @return 若创建成功，则返回true；未创建或创建失败则返回false
     */
    public boolean newIfNull(String componentCode, Kv config, boolean buildId) {
        Record defaultRecord = loadLatestComponentRecord(componentCode);
        if (defaultRecord == null) {
            Record record = getNewComponentRecord(ComponentType.V(componentCode), config);
            record.set("build_in", buildId);
            return SpringAnalysisManager.me().dbMain().save(META_COMPONENT, record);
        }
        return false;
    }

    /**
     * 加载指定组件最新版本的配置
     *
     * @param componentCode
     * @return
     */
    private Record loadLatestComponentRecord(String componentCode) {
        return SpringAnalysisManager.me().dbMain().findFirst(
                "select * from " + META_COMPONENT + " where code=? and version=(select max(version) from meta_component where code=?)", componentCode, componentCode);
    }

    /**
     * 更新指定组件的配置, 版本号+1。若不存在此组件配置，则不会更新。
     *
     * @param componentCode
     * @param config
     */
    public void updateDefault(String componentCode, Kv config) {
        Record defaultRecord = loadLatestComponentRecord(componentCode);
        if (defaultRecord != null) {
            defaultRecord.set("version", defaultRecord.getInt("version") + 1);
            defaultRecord.set("config", JSON.toJSONString(config));
            UtilKit.setUpdateUser(defaultRecord.getColumns());
            SpringAnalysisManager.me().dbMain().update(META_COMPONENT, defaultRecord);
        }
    }

    /**
     * 置空一个组件的配置数据。组件记录还在
     *
     * @param componentCode
     * @return
     */
    public boolean deleteDefault(String componentCode) {
        return SpringAnalysisManager.me().dbMain().update("update " + META_COMPONENT + " set config=? where code=?", Kv.create().toJson(), componentCode) > 0;
    }

    /**
     * 加载所有组件数据
     *
     * @return
     */
    public List<Record> loadComponents() {
        return SpringAnalysisManager.me().dbMain().findAll(META_COMPONENT);
    }

    /**
     * <pre>
     *     配置格式:load meta_component
     *          [key]     [value]
     *      TableList	 {"pagination":{"layout":"total, sizes, prev, pager, next, jumper","page-size":10,"current-page":1,"page-sizes":20},"methods":"GET","columns":[],"component_name":"TableList","name":"TableList","conf":{"size":"medium","default-sort":{"prop":"id","order":"descending"},"highlight-current-row":true},"label":"表格模板","data_url":"/table/list"}
     *      DropDownBox	 {"component_name":"DropDownBox","name":"DropDownBox","conf":{"clearable":true},"label":"下拉框","group":false}
     *      FormView	 {"columns":[],"component_name":"FormView","name":"FormView","action":"/save","btns":{"cancel":{"conf":{},"label":"取消"},"submit":{"conf":{"type":"primary"},"label":"提交"}},"conf":{"size":"medium","rules":{},"label-width":"100px"},"label":"表单模板"}
     *      TextBox	     {"component_name":"TextBox","name":"TextBox","conf":{"clearable":true,"placeholder":"请输入内容.."},"label":"文本框"}
     *      RadioBox	 {"component_name":"RadioBox","name":"RadioBox","conf":{},"label":"单选框","data_url":"","group":false}
     *      NumBox	     {"component_name":"NumBox","name":"NumBox","conf":{"controls":false,"placeholder":"请输入数值.."},"label":"数字框"}
     *      TextAreaBox	 {"component_name":"TextAreaBox","name":"TextAreaBox","conf":{"clearable":true,"placeholder":"请输入文本内容.."},"label":"文本域"}
     *      DateTimeBox	 {"component_name":"DateTimeBox","name":"DateTimeBox","conf":{"clearable":true,"value-format":"yyyy-MM-dd HH:mm:ss"},"label":"日期时间框"}
     *      DateBox	     {"component_name":"DateBox","name":"DateBox","conf":{"clearable":true,"value-format":"yyyy-MM-dd"},"label":"日期框"}
     *      BoolBox	     {"component_name":"BoolBox","name":"BoolBox","conf":{},"label":"布尔框"}
     * </pre>
     *
     * @return
     */
    public Kv loadComponentsFlatMap() {
        Kv kv = Kv.create();
        List<Record> components = loadComponents();
        components.forEach(record -> {
            kv.set(record.getStr("code"), record.getStr("config"));
        });
        return kv;
    }

    private Record getNewComponentRecord(ComponentType type, Kv config) {
        Record record = new Record();
        record.set("id", SnowFlake.me().nextId());
        record.set("en", type.getCode());
        record.set("cn", type.getCn());
        record.set("config", JSON.toJSONString(config));
        record.set("code", type.getCode());
        record.set("version", 1);
        User u = UserThreadLocal.getUser();
        if (u != null) {
            record.set("created_by", u.userId());
            record.set("created_time", new Date());
        }
        return record;
    }

    /**
     * 加载元对象已经存在的容器实例配置对应的容器类型
     *
     * @param objectCode
     * @return
     */
    public List<ComponentType> loadTypesByObjectCode(String objectCode) {
        List<String> codes = SpringAnalysisManager.me().dbMain().query("select comp_code from " + META_COMPONENT_INSTANCE + " where type=? and dest_object=?",
                INSTANCE.META_OBJECT.toString(),
                objectCode);
        List<ComponentType> types = new ArrayList<>();
        codes.forEach(s -> {
            types.add(ComponentType.V(s));
        });
        return types;
    }

    /**
     * 根据元对象 返回所有相关的 ComponentInstanceConfig
     *
     * @param objectCode
     * @return
     */
    public List<ComponentInstanceConfig> loadInstanceByObjectCode(String objectCode) {
        List<ComponentInstanceConfig> instanceConfigList = new ArrayList<>();
        List<String> instanceCodes = loadInstanceCodeByObjectCode(objectCode, ComponentType.UNKNOWN);
        instanceCodes.forEach(instanceCode -> {
            instanceConfigList.add(loadObjectConfig(instanceCode));
        });
        return instanceConfigList;
    }

    /**
     * 根据元对象+组件类型 获得所有instanceCode
     * 注: type=Unknow时不参加条件拼接
     *
     * @param objectCode
     * @param type       UNKNOWN时查询所有
     * @return
     */
    public List<String> loadInstanceCodeByObjectCode(String objectCode, ComponentType type) {
        if (type == ComponentType.UNKNOWN) {
            return SpringAnalysisManager.me().dbMain().query("select code from " + META_COMPONENT_INSTANCE + " where dest_object=? and type=?",
                    objectCode,
                    INSTANCE.META_OBJECT.toString());
        } else {
            return SpringAnalysisManager.me().dbMain().query("select code from " + META_COMPONENT_INSTANCE + " where dest_object=? and comp_code=? and type=?",
                    objectCode,
                    type.getCode(),
                    INSTANCE.META_OBJECT.toString());
        }
    }

    /**
     * 通过ic获取实例配置信息
     * 注: ic 的唯一是指以instanceCode为准的这套配置唯一,并不是记录唯一
     *
     * @param ic
     * @return
     */
    public ComponentInstanceConfig loadObjectConfig(String ic) {
        String sql = "select * from " + META_COMPONENT_INSTANCE + " where code=?";
        List<Record> records = SpringAnalysisManager.me().dbMain().find(sql, ic);
        Kv objectConfig = Kv.create();
        Okv fieldsMap = Okv.create();
        AtomicReference<ComponentType> containerType = new AtomicReference<>();
        AtomicReference<String> objectCode = new AtomicReference<>();
        AtomicReference<String> instanceName = new AtomicReference<>();

        Assert.isTrue(!CollectionUtils.isEmpty(records), String.format("容器实例配置不存在, ic=%s", ic));

        records.forEach(record -> {
            if (record.getStr("type").equals(INSTANCE.META_OBJECT.toString())) {
                containerType.set(ComponentType.V(record.getStr("comp_code")));
                instanceName.set(record.getStr("name"));
                objectCode.set(record.getStr("dest_object"));

                String strConfig = record.getStr("config");
                objectConfig.set(objectCode.get(), StrKit.isBlank(strConfig) ? Maps.newHashMapWithExpectedSize(0) : UtilKit.getKv(strConfig));
            } else {
                //dest_object -> meta_object_code
                String[] ss = record.getStr("dest_object").split("\\.");
                fieldsMap.put(ss[1], record.getStr("config"));
            }
        });
        return ComponentInstanceConfig.Load(objectConfig, fieldsMap, objectCode.get(), ic, instanceName.get(), containerType.get());
    }

    /**
     * 获取指定实例编码下的指定类型记录
     *
     * @param ic
     * @param type
     * @return
     */
    public Record getRecord(String ic, INSTANCE type) {
        String sql = "select * from " + META_COMPONENT_INSTANCE + " where code=? and type =?";
        return SpringAnalysisManager.me().dbMain().findFirst(sql, ic, type.toString());
    }

    /**
     * <pre>
     *  加载某元对象与控件实例的配置信息
     * </pre>
     *
     * @param componentCode
     * @param destCode
     * @return
     */
    @Deprecated
    public ComponentInstanceConfig loadObjectConfig(String componentCode, String destCode) {
        //load single object config
        Record objectConfig = SpringAnalysisManager.me().dbMain().findFirst(
                "select * from " + META_COMPONENT_INSTANCE + " where comp_code=? and dest_object=? and type=?", componentCode, destCode, INSTANCE.META_OBJECT.toString());
        //ensure return avalible value, like "" , "{}"
        String strConfig = objectConfig.getStr("config");
        Kv objConf = Kv.by(destCode, StrKit.isBlank(strConfig) ? Maps.newHashMapWithExpectedSize(0) : UtilKit.getKv(strConfig));

        Okv fieldsMap = Okv.create();
        List<Record> fields = SpringAnalysisManager.me().dbMain().find(
                "select * from " + META_COMPONENT_INSTANCE + " where comp_code=? and dest_object like concat(?,'.%') and type=?",
                componentCode,
                destCode,
                INSTANCE.META_FIELD.toString());
        for (Record record : fields) {
            String s = record.getStr("dest_object").split("\\.")[1];
            fieldsMap.set(s, record.getStr("config"));
        }

        String instanceCode = objectConfig.getStr("code");
        String instanceName = objectConfig.getStr("name");
        return ComponentInstanceConfig.Load(objConf, fieldsMap, destCode, instanceCode, instanceName, ComponentType.V(componentCode));
    }

    /**
     * 创建新的实例配置
     *
     * @param component               容器组件
     * @param object                  元对象
     * @param componentInstanceConfig 组件实例配置
     * @return
     */
    public boolean newObjectConfig(Component component, IMetaObject object, ComponentInstanceConfig componentInstanceConfig) {
        /**
         * new objectConfig
         * foreach fieldsConfig
         */
        Record record = getRecord(component,
                object.code(),
                componentInstanceConfig.getInstanceCode(),
                componentInstanceConfig.getInstanceName(),
                INSTANCE.META_OBJECT,
                componentInstanceConfig.getObjectConfig());
        record.set("build_in", object.buildIn());
        SpringAnalysisManager.me().dbMain().save(META_COMPONENT_INSTANCE, record);

        List<Record> fieldRecords = Lists.newArrayList();
        Okv fieldsMap = componentInstanceConfig.getFieldsMap();
        object.fields().forEach(f -> {
            Kv fkv = UtilKit.getKv(fieldsMap, f.fieldCode());
            Record fieldConfigRecord = getFieldConfigRecord(component,
                    object.code(),
                    f.fieldCode(),
                    componentInstanceConfig.getInstanceCode(),
                    componentInstanceConfig.getInstanceName(),
                    fkv);
            fieldConfigRecord.set("build_in", f.buildIn());
            fieldRecords.add(fieldConfigRecord);
        });

        SpringAnalysisManager.me().dbMain().batchSave(META_COMPONENT_INSTANCE, fieldRecords, 50);

        return true;
    }

    /**
     * 先删除,再插入
     *
     * @param component
     * @param object
     * @param componentInstanceConfig
     * @return
     */
    public boolean updateObjectConfig(Component component, IMetaObject object, ComponentInstanceConfig componentInstanceConfig) {

        deleteObjectConfig(componentInstanceConfig.getInstanceCode());

        Record record = getRecord(component,
                object.code(),
                componentInstanceConfig.getInstanceCode(),
                componentInstanceConfig.getInstanceName(),
                INSTANCE.META_OBJECT,
                componentInstanceConfig.getObjectConfig());
        record.set("build_in", object.buildIn());
        SpringAnalysisManager.me().dbMain().save(META_COMPONENT_INSTANCE, record);

        Collection<IMetaField> fields = object.fields();

        List<Record> fieldRecords = Lists.newArrayList();
        fields.forEach(f -> {
            Kv fkv = UtilKit.getKv(componentInstanceConfig.getFieldsMap(), f.fieldCode());
            Record fieldConfigRecord = getFieldConfigRecord(component,
                    object.code(),
                    f.fieldCode(),
                    componentInstanceConfig.getInstanceCode(),
                    componentInstanceConfig.getInstanceName(),
                    fkv);
            fieldConfigRecord.set("build_in", f.buildIn());
            fieldRecords.add(fieldConfigRecord);
        });

        SpringAnalysisManager.me().dbMain().batchSave(META_COMPONENT_INSTANCE, fieldRecords, 50);

        return true;
    }

    /**
     * TODO 单独更新实例字段,目前只在InitKit中使用;
     *
     * @param containerType
     * @param metaField
     * @param config
     * @return
     */
    public boolean updateFieldConfig(ComponentType containerType, IMetaField metaField, Kv config) {
        Record fieldInstance = SpringAnalysisManager.me().dbMain().findFirst(
                "select * from " + META_COMPONENT_INSTANCE + " where comp_code=? and type=? and dest_object=?",
                containerType.getCode(),
                INSTANCE.META_FIELD.toString(),
                metaField.objectCode() + "." + metaField.fieldCode());
        fieldInstance.set("config", config.toJson());
        Date timestamp = new Date();
        fieldInstance.set("updated_time", timestamp);
        fieldInstance.set("remark", "from file" + DateKit.toStr(timestamp));
        return SpringAnalysisManager.me().dbMain().update(META_COMPONENT_INSTANCE, fieldInstance);
    }

    /**
     * 单独更新实例容器配置(不更新字段配置), 只在InitKit中使用
     *
     * @param containerType
     * @param metaObject
     * @param config
     * @return
     */
    public boolean updateObjectConfigSelf(ComponentType containerType, IMetaObject metaObject, Kv config) {
        Record objectInstance = SpringAnalysisManager.me().dbMain().findFirst(
                "select * from " + META_COMPONENT_INSTANCE + " where comp_code=? and type=? and dest_object=?",
                containerType.getCode(),
                INSTANCE.META_OBJECT.toString(),
                metaObject.code());
        objectInstance.set("config", config.toJson());
        Date timestamp = new Date();
        objectInstance.set("updated_time", timestamp);
        objectInstance.set("remark", "from file " + DateKit.toStr(timestamp));
        return SpringAnalysisManager.me().dbMain().update(META_COMPONENT_INSTANCE, objectInstance);
    }

    /**
     * 单独新增实例字段,配合增量更新元字段使用
     *
     * @param component 传容器的Component
     * @param metaField
     * @param config
     * @return
     */
    public boolean newFieldConfig(Component component, IMetaField metaField, String instanceCode, String instanceName, Kv config) {
        Record fieldInstance = getFieldConfigRecord(component, metaField.objectCode(), metaField.fieldCode(), instanceCode, instanceName, config);
        fieldInstance.set("build_in", metaField.buildIn());
        return SpringAnalysisManager.me().dbMain().save(META_COMPONENT_INSTANCE, fieldInstance);
    }

    private Record getFieldConfigRecord(Component component, String objectCode, String fieldCode, String instanceCode, String instanceName, Kv config) {
        String dest_code = objectCode + "." + fieldCode;
        return getRecord(component, dest_code, instanceCode, instanceName, INSTANCE.META_FIELD, config);
    }

    /**
     * 删除某元对象下所有关联配置信息
     *
     * @param objectCode
     * @return
     */
    public boolean deleteObjectAll(String objectCode) {
        SpringAnalysisManager.me().dbMain().delete("delete from " + META_COMPONENT_INSTANCE + " where type=? and dest_object=?",
                INSTANCE.META_OBJECT.toString(),
                objectCode);
        SpringAnalysisManager.me().dbMain().delete("delete from " + META_COMPONENT_INSTANCE + " where type=? and dest_object like concat(?,'%')",
                INSTANCE.META_FIELD.toString(),
                objectCode);
        return true;
    }

    /**
     * 删除整套UI实例配置
     *
     * @param instanceCode
     * @return
     */
    public boolean deleteObjectConfig(String instanceCode) {
        return SpringAnalysisManager.me().dbMain().delete("delete from " + META_COMPONENT_INSTANCE + " where code = ?", instanceCode) > 0;
    }

    @Deprecated
    public boolean deleteObjectConfig(String componentCode, String objectCode, boolean isSingle) {

        SpringAnalysisManager.me().dbMain().delete("delete from " + META_COMPONENT_INSTANCE + " where comp_code=? and type=? and dest_object=?",
                componentCode,
                INSTANCE.META_OBJECT.toString(),
                objectCode);

        if (!isSingle) {
            SpringAnalysisManager.me().dbMain().delete("delete from " + META_COMPONENT_INSTANCE + " where comp_code=? and type=? and dest_object like concat(?,'%')",
                    componentCode,
                    INSTANCE.META_FIELD.toString(),
                    objectCode);
        }
        return true;
    }

    /**
     * 删除指定元字段在指定容器组件下的实例配置
     *
     * @param componentCode
     * @param objectCode
     * @param fieldCode
     * @return 返回删除的记录数
     */
    public int deleteFieldConfig(String componentCode, String objectCode, String fieldCode) {
        return SpringAnalysisManager.me().dbMain().delete("delete from " + META_COMPONENT_INSTANCE + " where comp_code=? and type=? and dest_object=?",
                componentCode,
                INSTANCE.META_FIELD.toString(),
                objectCode + "." + fieldCode);
    }

    /**
     * 删除指定元字段在所有容器组件下的实例配置。这通常用于元字段删除
     *
     * @param objectCode
     * @param fieldCode
     * @return 返回删除的记录数
     */
    public int deleteFieldConfig(String objectCode, String fieldCode) {
        return SpringAnalysisManager.me().dbMain().delete("delete from " + META_COMPONENT_INSTANCE + " where type =? and dest_object=?",
                INSTANCE.META_FIELD.toString(),
                objectCode + "." + fieldCode);
    }


    public boolean hasObjectConfig(String componentCode, String objectCode) {
        return SpringAnalysisManager.me().dbMain().queryInt("select count(1) from " + META_COMPONENT_INSTANCE + " where comp_code = ? and dest_object = ?",
                componentCode,
                objectCode) >= 1;
    }

    /**
     * 判断指定实例编码是否已经存在
     *
     * @param instanceCode
     * @return
     */
    public boolean hasObjectConfig(String instanceCode) {
        return SpringAnalysisManager.me().dbMain().queryInt("select count(1) from " + META_COMPONENT_INSTANCE + " where code = ?", instanceCode) >= 1;
    }

    private Record getRecord(Component component, String destObject, String instanceCode, String instanceName, INSTANCE specific, Kv config) {
        Record record = new Record();
        record.set("id", SnowFlake.me().nextId());
        record.set("code", instanceCode);
        record.set("name", instanceName);
        record.set("comp_code", component.type());
        record.set("type", specific.toString());
        record.set("dest_object", destObject);
        record.set("config", config == null ? Kv.create().toJson() : config.toJson());
        User u = UserThreadLocal.getUser();
        if (u != null) {
            record.set("created_by", u.userId());
            record.set("created_time", new Date());
        }
        return record;
    }

    public Record getComponentInstanceBrief(String instanceCode) {
        return SpringAnalysisManager.me().dbMain().findFirst("select code, name, comp_code, dest_object as object_code from " + META_COMPONENT_INSTANCE + " where code =? and type=?",
                instanceCode, INSTANCE.META_OBJECT.toString());
    }

    public enum INSTANCE {
        META_OBJECT,
        META_FIELD,
        SPECIFIC,
    }
}
