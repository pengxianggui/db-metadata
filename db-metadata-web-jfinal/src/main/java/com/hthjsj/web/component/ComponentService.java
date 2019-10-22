package com.hthjsj.web.component;

import com.alibaba.fastjson.JSON;
import com.hthjsj.analysis.db.SnowFlake;
import com.hthjsj.analysis.meta.Component;
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
import java.util.Map;

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

    public Record loadDefault(String componentCode) {
        if (StrKit.isBlank(componentCode)) {
            throw new ComponentException("必须指定组件 Code:%s", componentCode);
        }
        Record record = Db.findFirst("select * from meta_component where code=?", componentCode);
        if (record == null) {
            throw new ComponentException("未找到Code[%s]的组件", componentCode);
        }
        return record;
    }

    public void register(ComponentType type, Component component, Map<String, Object> config) {
        Record record = new Record();
        record.set("id", SnowFlake.me().nextId());
        record.set("en", type.getCode());
        record.set("cn", type.getName());
        record.set("config", JSON.toJSONString(config));
        record.set("code", type.getCode());
        record.set("version", 1);
        User u = ThreadLocalUserKit.getUser();
        if (u != null) {
            record.set("created_by", u.userId());
            record.set("created_time", new Date());
        }
        Db.save("meta_component", record);
    }

    public Record loadObjectConfig(String componentCode, String destCode) {
        return loadConfig(componentCode, destCode, INSTANCE.META_OBJECT.toString());
    }

    public List<Record> loadFieldsConfig(String componentCode, String destCode) {
        return Db.find("select * from meta_component_instance where comp_code=? and dest_object like concat(?,'%') and type=?",
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

    private Record loadConfig(String componentCode, String destCode, String type) {
        Record record = Db.findFirst("select config from meta_component_instance where comp_code=? and dest_object=? and type=?", componentCode, destCode, type);
        return record == null ? new Record() : record;
    }

    public boolean newObjectConfig(ViewComponent component, String objectCode) {
        Record record = getRecord(component, objectCode, INSTANCE.META_OBJECT);
        return Db.save("meta_component_instance", record);
    }

    public boolean newFieldConfig(ViewComponent component, String objectCode, String fieldCode) {
        // objectCode.name
        String dest_code = objectCode + "." + fieldCode;
        Record record = getRecord(component, dest_code, INSTANCE.META_FIELD);
        return Db.save("meta_component_instance", record);
    }

    public boolean newSpecificConfig(ViewComponent component, String specificCode) {
        Record record = getRecord(component, specificCode, INSTANCE.SPECIFIC);
        return Db.save("meta_component_instance", record);
    }

    private Record getRecord(ViewComponent component, String specificCode, INSTANCE specific) {
        Record record = new Record();
        record.set("id", SnowFlake.me().nextId());
        record.set("comp_code", component.type());
        record.set("type", specific.toString());
        record.set("dest_object", specificCode);
        record.set("config", component.config());
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
