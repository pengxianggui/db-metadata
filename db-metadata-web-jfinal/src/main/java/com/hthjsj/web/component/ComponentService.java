package com.hthjsj.web.component;

import com.alibaba.fastjson.JSON;
import com.hthjsj.analysis.db.SnowFlake;
import com.hthjsj.analysis.meta.IMetaObject;
import com.hthjsj.web.ThreadLocalUserKit;
import com.hthjsj.web.User;
import com.jfinal.aop.Before;
import com.jfinal.kit.Kv;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.plugin.activerecord.tx.Tx;

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
        Record record = Db.findFirst("select * from " + META_COMPONENT + " where code=?", componentCode);
        if (record == null) {
            throw new ComponentException("未找到Code[%s]的组件", componentCode);
        }
        return record;
    }

    public void newDefault(String componentCode, Kv config) {
        Record defaultRecord = Db.findFirst("select * from " + META_COMPONENT + " where code=?", componentCode);
        if (defaultRecord == null) {
            register(ComponentType.V(componentCode), config);
        }
        defaultRecord.set("config", JSON.toJSONString(config));
        Db.update(META_COMPONENT, defaultRecord);
    }

    public boolean deleteDefault(String componentCode) {
        return Db.delete("delete from " + META_COMPONENT + " where code=?", componentCode) > 0;
    }

    public List<Record> listComponents() {
        return Db.findAll(META_COMPONENT);
    }

    public void register(ComponentType type, Kv config) {
        if (Db.queryInt("select count(1) from " + META_COMPONENT + " where code=?", type.getCode()) == 0) {
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
            Db.save(META_COMPONENT, record);
        }
    }

    public Record loadObjectConfig(String componentCode, String destCode) {
        return loadConfig(componentCode, destCode, INSTANCE.META_OBJECT.toString());
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

    public List<Record> loadFieldsConfig(String componentCode, String destCode) {
        return Db.find("select * from " + META_COMPONENT_INSTANCE + " where comp_code=? and dest_object like concat(?,'%') and type=?",
                       componentCode,
                       destCode,
                       INSTANCE.META_FIELD.toString());
    }

    public Record loadFieldConfig(String componentCode, String destCode, String fieldCode) {
        List<Record> records = loadFieldsConfig(componentCode, destCode);
        for (Record record : records) {
            if (record.getStr("dest_object").endsWith(fieldCode)) {
                return record;
            }
        }
        return new Record();
    }

    private Record loadConfig(String componentCode, String destCode, String type) {
        Record record = Db.findFirst("select config from " + META_COMPONENT_INSTANCE + " where comp_code=? and dest_object=? and type=?", componentCode, destCode, type);
        return record == null ? new Record() : record;
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
        boolean oc = newObjectConfig(component, object.code(), config);

        //FIXME revert
        //        Collection<IMetaField> fields = object.fields();
        //
        //        List<Record> fieldRecords = Lists.newArrayList();
        //        fields.forEach(f -> {
        //            Kv fkv = JSON.parseObject(config.getStr(f.fieldCode()), Kv.class);
        //            fieldRecords.add(getFieldConfigRecord(component, object.code(), f.fieldCode(), fkv));
        //        });
        //
        //        Db.batchSave(META_COMPONENT_INSTANCE, fieldRecords, 50);

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
