package com.hthjsj.web.component;

import com.alibaba.fastjson.JSON;
import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.hthjsj.App;
import com.hthjsj.analysis.component.Component;
import com.hthjsj.analysis.component.ComponentType;
import com.hthjsj.analysis.db.SnowFlake;
import com.hthjsj.analysis.meta.IMetaField;
import com.hthjsj.analysis.meta.IMetaObject;
import com.hthjsj.web.kit.UtilKit;
import com.hthjsj.web.ui.ComponentInstanceConfig;
import com.hthjsj.web.user.User;
import com.hthjsj.web.user.UserThreadLocal;
import com.jfinal.aop.Before;
import com.jfinal.ext.kit.DateKit;
import com.jfinal.kit.Kv;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.plugin.activerecord.tx.Tx;

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
@Before(Tx.class)
public class ComponentService {

    private static final String META_COMPONENT = "meta_component";

    private static final String META_COMPONENT_INSTANCE = META_COMPONENT + "_instance";

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

    public Record loadDefaultByVersion(String componentCode, int version) {
        if (StrKit.isBlank(componentCode)) {
            throw new ComponentException("必须指定组件 Code:%s", componentCode);
        }
        Record record = Db.use(App.DB_MAIN).findFirst("select * from " + META_COMPONENT + " where code=? and version=?", componentCode, version);
        if (record == null) {
            throw new ComponentException("未找到Code[%s]的组件", componentCode);
        }
        return record;
    }

    public boolean newDefault(String componentCode, Kv config) {
        Record defaultRecord = loadLatestComponentRecord(componentCode);
        if (defaultRecord == null) {
            Record record = getNewComponentRecord(ComponentType.V(componentCode), config);
            return Db.use(App.DB_MAIN).save(META_COMPONENT, record);
        }
        return false;
    }

    private Record loadLatestComponentRecord(String componentCode) {
        return Db.use(App.DB_MAIN).findFirst("select * from " + META_COMPONENT + " where code=? and version=(select max(version) from meta_component where code=?)",
                                             componentCode,
                                             componentCode);
    }

    public void updateDefault(String componentCode, Kv config) {
        Record defaultRecord = loadLatestComponentRecord(componentCode);
        if (defaultRecord != null) {
            defaultRecord.set("version", defaultRecord.getInt("version") + 1);
            defaultRecord.set("config", JSON.toJSONString(config));
            UtilKit.setUpdateUser(defaultRecord.getColumns());
            Db.use(App.DB_MAIN).update(META_COMPONENT, defaultRecord);
        }
    }

    public boolean deleteDefault(String componentCode) {
        return Db.use(App.DB_MAIN).update("update " + META_COMPONENT + " set config=? where code=?", Kv.create().toJson(), componentCode) > 0;
    }

    public List<Record> loadComponents() {
        return Db.findAll(META_COMPONENT);
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

    public List<ComponentType> loadTypesByObjectCode(String objectCode) {
        List<String> codes = Db.use(App.DB_MAIN).query("select comp_code from " + META_COMPONENT_INSTANCE + " where type=? and dest_object=?",
                                                       INSTANCE.META_OBJECT.toString(),
                                                       objectCode);
        List<ComponentType> types = new ArrayList<>();
        codes.forEach(s -> {
            types.add(ComponentType.V(s));
        });
        return types;
    }

    public List<String> loadObjectsByType(String typeCode) {
        return Db.query("select dest_object from " + META_COMPONENT_INSTANCE + " where comp_code=? and type=?", typeCode, INSTANCE.META_OBJECT.toString());
    }

    /**
     * 通过instanceCode获取实例配置信息
     * 注: instanceCode 的唯一是指以instanceCode为准的这套配置唯一,并不是记录唯一
     *
     * @param instanceCode
     *
     * @return
     */
    public ComponentInstanceConfig loadObjectConfig(String instanceCode) {
        String sql = "select * from " + META_COMPONENT_INSTANCE + " where code=?";
        List<Record> records = Db.use(App.DB_MAIN).find(sql, instanceCode);
        Kv objectConfig = Kv.create();
        Kv fieldsMap = Kv.create();
        AtomicReference<String> objectCode = new AtomicReference<>();
        AtomicReference<String> instanceName = new AtomicReference<>();
        records.forEach(record -> {
            if (record.getStr("config").contains(".")) {
                String s = record.getStr("dest_object").split("\\.")[1];
                fieldsMap.set(s, record.getStr("config"));
            } else {
                //dest_object -> meta_object_code
                objectCode.set(record.getStr("dest_object"));
                instanceName.set(record.getStr("label"));
                objectConfig.set(objectCode, record.getStr("config"));
            }
        });
        return ComponentInstanceConfig.Load(objectConfig, fieldsMap, objectCode.get(), instanceCode, instanceName.get());
    }

    /**
     * <pre>
     *  加载某元对象与控件实例的配置信息
     * </pre>
     *
     * @param componentCode
     * @param destCode
     *
     * @return
     */
    public ComponentInstanceConfig loadObjectConfig(String componentCode, String destCode) {
        //load single object config
        Record objectConfig = Db.use(App.DB_MAIN).findFirst("select * from " + META_COMPONENT_INSTANCE + " where comp_code=? and dest_object=? and type=?",
                                                            componentCode,
                                                            destCode,
                                                            INSTANCE.META_OBJECT.toString());
        //ensure return avalible value, like "" , "{}"
        String strConfig = objectConfig.getStr("config");
        Kv objConf = Kv.by(destCode, StrKit.isBlank(strConfig) ? Maps.newHashMapWithExpectedSize(0) : strConfig);

        Kv fieldsMap = Kv.create();
        List<Record> fields = Db.use(App.DB_MAIN).find("select * from " + META_COMPONENT_INSTANCE + " where comp_code=? and dest_object like concat(?,'%') and type=?",
                                                       componentCode,
                                                       destCode,
                                                       INSTANCE.META_FIELD.toString());
        for (Record record : fields) {
            String s = record.getStr("dest_object").split("\\.")[1];
            fieldsMap.set(s, record.getStr("config"));
        }

        String instanceCode = objectConfig.getStr("code");
        String instanceName = objectConfig.getStr("name");
        return ComponentInstanceConfig.Load(objConf, fieldsMap, destCode, instanceCode, instanceName);
    }

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
        Db.use(App.DB_MAIN).save(META_COMPONENT_INSTANCE, record);

        List<Record> fieldRecords = Lists.newArrayList();
        Kv fieldsMap = componentInstanceConfig.getFieldsMap();
        object.fields().forEach(f -> {
            Kv fkv = UtilKit.getKv(fieldsMap, f.fieldCode());
            fieldRecords.add(getFieldConfigRecord(component,
                                                  object.code(),
                                                  f.fieldCode(),
                                                  componentInstanceConfig.getInstanceCode(),
                                                  componentInstanceConfig.getInstanceName(),
                                                  fkv));
        });

        Db.use(App.DB_MAIN).batchSave(META_COMPONENT_INSTANCE, fieldRecords, 50);

        return true;
    }

    /**
     * 先删除,再插入
     *
     * @param component
     * @param object
     * @param componentInstanceConfig
     *
     * @return
     */
    public boolean updateObjectConfig(Component component, IMetaObject object, ComponentInstanceConfig componentInstanceConfig) {

        deleteObjectConfig(component.code(), object.code(), false);

        Record record = getRecord(component,
                                  object.code(),
                                  componentInstanceConfig.getInstanceCode(),
                                  componentInstanceConfig.getInstanceName(),
                                  INSTANCE.META_OBJECT,
                                  componentInstanceConfig.getObjectConfig());
        Db.use(App.DB_MAIN).save(META_COMPONENT_INSTANCE, record);

        Collection<IMetaField> fields = object.fields();

        List<Record> fieldRecords = Lists.newArrayList();
        fields.forEach(f -> {
            Kv fkv = UtilKit.getKv(componentInstanceConfig.getFieldsMap(), f.fieldCode());
            fieldRecords.add(getFieldConfigRecord(component,
                                                  object.code(),
                                                  f.fieldCode(),
                                                  componentInstanceConfig.getInstanceCode(),
                                                  componentInstanceConfig.getInstanceName(),
                                                  fkv));
        });

        Db.use(App.DB_MAIN).batchSave(META_COMPONENT_INSTANCE, fieldRecords, 50);

        return true;
    }

    /**
     * TODO 单独更新实例字段,目前只在InitKit中使用;
     *
     * @param containerType
     * @param metaField
     * @param config
     *
     * @return
     */
    public boolean updateFieldConfig(ComponentType containerType, IMetaField metaField, Kv config) {
        Record fieldInstance = Db.use(App.DB_MAIN).findFirst("select * from " + META_COMPONENT_INSTANCE + " where comp_code=? and type=? and dest_object=?",
                                                             containerType.getCode(),
                                                             INSTANCE.META_FIELD.toString(),
                                                             metaField.objectCode() + "." + metaField.fieldCode());
        fieldInstance.set("config", config.toJson());
        Date timestamp = new Date();
        fieldInstance.set("updated_time", timestamp);
        fieldInstance.set("remark", "from file" + DateKit.toStr(timestamp));
        return Db.use(App.DB_MAIN).update(META_COMPONENT_INSTANCE, fieldInstance);
    }

    private Record getFieldConfigRecord(Component component, String objectCode, String fieldCode, String instanceCodoe, String instanceName, Kv config) {
        String dest_code = objectCode + "." + fieldCode;
        return getRecord(component, dest_code, instanceCodoe, instanceName, INSTANCE.META_FIELD, config);
    }

    /**
     * 删除某元对象下所有关联配置信息
     *
     * @param objectCode
     *
     * @return
     */
    public boolean deleteObjectAll(String objectCode) {
        Db.use(App.DB_MAIN).delete("delete from " + META_COMPONENT_INSTANCE + " where type=? and dest_object=?", INSTANCE.META_OBJECT.toString(), objectCode);
        Db.use(App.DB_MAIN).delete("delete from " + META_COMPONENT_INSTANCE + " where type=? and dest_object like concat(?,'%')",
                                   INSTANCE.META_FIELD.toString(),
                                   objectCode);
        return true;
    }

    public boolean deleteObjectConfig(String componentCode, String objectCode, boolean isSingle) {

        Db.use(App.DB_MAIN).delete("delete from " + META_COMPONENT_INSTANCE + " where comp_code=? and type=? and dest_object=?",
                                   componentCode,
                                   INSTANCE.META_OBJECT.toString(),
                                   objectCode);

        if (!isSingle) {
            Db.use(App.DB_MAIN).delete("delete from " + META_COMPONENT_INSTANCE + " where comp_code=? and type=? and dest_object like concat(?,'%')",
                                       componentCode,
                                       INSTANCE.META_FIELD.toString(),
                                       objectCode);
        }
        return true;
    }

    public boolean deleteFieldConfig(String componentCode, String objectCode, String fieldCode) {
        Db.use(App.DB_MAIN).delete("delete from " + META_COMPONENT_INSTANCE + " where comp_code=? and type=? and dest_object=?",
                                   componentCode,
                                   INSTANCE.META_FIELD.toString(),
                                   objectCode + "." + fieldCode);
        return true;
    }

    public boolean hasObjectConfig(String componentCode, String objectCode) {
        return hasObjectConfig(Joiner.on(".").join(objectCode, componentCode));
    }

    public boolean hasObjectConfig(String instanceCode) {
        return Db.use(App.DB_MAIN).queryInt("select count(1) from " + META_COMPONENT_INSTANCE + " where code = ?", instanceCode) > 1;
    }

    private Record getRecord(Component component, String specificCode, String instanceCode, String instanceName, INSTANCE specific, Kv config) {
        Record record = new Record();
        record.set("id", SnowFlake.me().nextId());
        record.set("code", instanceCode);
        record.set("name", instanceName);
        record.set("comp_code", component.type());
        record.set("type", specific.toString());
        record.set("dest_object", specificCode);
        record.set("config", config == null ? Kv.create().toJson() : config.toJson());
        User u = UserThreadLocal.getUser();
        if (u != null) {
            record.set("created_by", u.userId());
            record.set("created_time", new Date());
        }
        return record;
    }

    enum INSTANCE {
        META_OBJECT,
        META_FIELD,
        SPECIFIC,
    }
}
