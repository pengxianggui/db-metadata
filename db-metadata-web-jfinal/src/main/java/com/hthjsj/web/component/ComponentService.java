package com.hthjsj.web.component;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.hthjsj.analysis.db.SnowFlake;
import com.hthjsj.analysis.meta.IMetaField;
import com.hthjsj.analysis.meta.IMetaObject;
import com.hthjsj.web.ThreadLocalUserKit;
import com.hthjsj.web.User;
import com.jfinal.aop.Before;
import com.jfinal.kit.Kv;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.plugin.activerecord.tx.Tx;

import java.util.Collection;
import java.util.Date;
import java.util.List;

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
        Record record = Db.findFirst("select * from " + META_COMPONENT + " where code=? and version=?", componentCode, version);
        if (record == null) {
            throw new ComponentException("未找到Code[%s]的组件", componentCode);
        }
        return record;
    }

    public boolean newDefault(String componentCode, Kv config) {
        Record defaultRecord = loadLatestComponentRecord(componentCode);
        if (defaultRecord == null) {
            Record record = getNewComponentRecord(ComponentType.V(componentCode), config);
            return Db.save(META_COMPONENT, record);
        }
        return false;
    }

    private Record loadLatestComponentRecord(String componentCode) {
        return Db.findFirst("select * from " + META_COMPONENT + " where code=? and version=(select max(version) from meta_component where code=?)",
                            componentCode,
                            componentCode);
    }

    public void updateDefault(String componentCode, Kv config) {
        Record defaultRecord = loadLatestComponentRecord(componentCode);
        if (defaultRecord != null) {
            defaultRecord.set("version", defaultRecord.getInt("version") + 1);
            defaultRecord.set("config", JSON.toJSONString(config));
            Db.update(META_COMPONENT, defaultRecord);
        }
    }

    public boolean deleteDefault(String componentCode) {
        return Db.update("update " + META_COMPONENT + "set config=? where code=?", Kv.create().toJson(), componentCode) > 0;
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
     *      FormTmpl	 {"columns":[],"component_name":"FormTmpl","name":"FormTmpl","action":"/save","btns":{"cancel":{"conf":{},"label":"取消"},"submit":{"conf":{"type":"primary"},"label":"提交"}},"conf":{"size":"medium","rules":{},"label-width":"100px"},"label":"表单模板"}
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
        User u = ThreadLocalUserKit.getUser();
        if (u != null) {
            record.set("created_by", u.userId());
            record.set("created_time", new Date());
        }
        return record;
    }

    public String loadObjectConfig(String componentCode, String destCode) {
        return loadConfig(componentCode, destCode, INSTANCE.META_OBJECT.toString());
    }

    public Kv loadObjectConfigFlat(String componentCode, String destCode) {
        //load single object config
        String objectConfig = loadConfig(componentCode, destCode, INSTANCE.META_OBJECT.toString());
        Kv objConf = Kv.by(destCode, objectConfig);

        //load fields config
        objConf.set(loadFieldsConfigMap(componentCode, destCode));
        return objConf;
    }

    public List<Record> loadFieldsConfig(String componentCode, String destCode) {
        return Db.find("select * from " + META_COMPONENT_INSTANCE + " where comp_code=? and dest_object like concat(?,'%') and type=?",
                       componentCode,
                       destCode,
                       INSTANCE.META_FIELD.toString());
    }

    public Kv loadFieldsConfigMap(String componentCode, String destCode) {
        Kv kv = Kv.create();
        List<Record> fields = loadFieldsConfig(componentCode, destCode);
        for (Record record : fields) {
            String s = record.getStr("dest_object").split("\\.")[1];
            kv.set(s, record.getStr("config"));
        }
        return kv;
    }

    private String loadConfig(String componentCode, String destCode, String type) {
        return Db.queryStr("select config from " + META_COMPONENT_INSTANCE + " where comp_code=? and dest_object=? and type=?", componentCode, destCode, type);
    }

    public boolean newObjectConfig(ViewComponent component, String objectCode, Kv config) {
        Record record = getRecord(component, objectCode, INSTANCE.META_OBJECT, config);
        return Db.save(META_COMPONENT_INSTANCE, record);
    }

    public boolean newObjectConfig(ViewComponent component, IMetaObject object, Kv config, boolean strict) {
        /**
         * new objectConfig
         * foreach fieldsConfig
         */
        newObjectConfig(component, object.code(), JSON.parseObject(config.getStr(object.code()), Kv.class));

        Collection<IMetaField> fields = object.fields();

        List<Record> fieldRecords = Lists.newArrayList();
        fields.forEach(f -> {
            Kv fkv = JSON.parseObject(config.getStr(f.fieldCode()), Kv.class);
            fieldRecords.add(getFieldConfigRecord(component, object.code(), f.fieldCode(), fkv));
        });

        Db.batchSave(META_COMPONENT_INSTANCE, fieldRecords, 50);

        return true;
    }

    public boolean newFieldConfig(ViewComponent component, String objectCode, String fieldCode, Kv config) {
        Kv fkv = JSON.parseObject(config.getStr(fieldCode), Kv.class);
        Record fieldRecord = getFieldConfigRecord(component, objectCode, fieldCode, fkv);
        return Db.save(META_COMPONENT_INSTANCE, fieldRecord);
    }

    private Record getFieldConfigRecord(ViewComponent component, String objectCode, String fieldCode, Kv config) {
        String dest_code = objectCode + "." + fieldCode;
        return getRecord(component, dest_code, INSTANCE.META_FIELD, config);
    }

    //    public boolean newSpecificConfig(ViewComponent component, String specificCode) {
    //        Record record = getRecord(component, specificCode, INSTANCE.SPECIFIC, Kv.create());
    //        return Db.save(META_COMPONENT_INSTANCE, record);
    //    }

    public boolean deleteObjectConfig(String componentCode, String objectCode, boolean isSingle) {

        Db.delete("delete from " + META_COMPONENT_INSTANCE + " where comp_code=? and type=? and dest_object=?",
                  componentCode,
                  INSTANCE.META_OBJECT.toString(),
                  objectCode);

        if (!isSingle) {
            Db.delete("delete from " + META_COMPONENT_INSTANCE + " where comp_code=? and type=? and dest_object like concat(?,'%')",
                      componentCode,
                      INSTANCE.META_FIELD.toString(),
                      objectCode);
        }
        return true;
    }

    public boolean deleteFieldConfig(String componentCode, String objectCode, String fieldCode) {
        Db.delete("delete from " + META_COMPONENT_INSTANCE + " where comp_code=? and type=? and dest_object=?",
                  componentCode,
                  INSTANCE.META_FIELD.toString(),
                  objectCode + "." + fieldCode);
        return true;
    }

    public boolean hasObjectConfig(String componentCode, String objectCode) {
        return loadObjectConfig(componentCode, objectCode) != null;
    }

    private Record getRecord(ViewComponent component, String specificCode, INSTANCE specific, Kv config) {
        Record record = new Record();
        record.set("id", SnowFlake.me().nextId());
        record.set("comp_code", component.type());
        record.set("type", specific.toString());
        record.set("dest_object", specificCode);
        record.set("config", config == null ? Kv.create().toJson() : config.toJson());
        User u = ThreadLocalUserKit.getUser();
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
