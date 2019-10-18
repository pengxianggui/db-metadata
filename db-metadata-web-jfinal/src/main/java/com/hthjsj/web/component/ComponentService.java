package com.hthjsj.web.component;

import com.alibaba.fastjson.JSON;
import com.hthjsj.web.ThreadLocalUserKit;
import com.hthjsj.web.User;
import com.jfinal.aop.Before;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.plugin.activerecord.tx.Tx;

import java.util.Date;
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

    public Record load(String componentCode) {
        if (StrKit.isBlank(componentCode)) {
            throw new ComponentException("必须指定组件 Code:%s", componentCode);
        }
        Record record = Db.findFirst("select * from meta_component where code=?", componentCode);
        if (record == null) {
            throw new ComponentException("未找到Code[%s]的组件", componentCode);
        }
        return record;
    }

    public Record loadObjectConfig(String componentCode, String destCode) {
        return loadConfig(componentCode, destCode, INSTANCE.META_OBJECT.toString());
    }

    private Record loadConfig(String componentCode, String destCode, String type) {
        return Db.findFirst("select config from meta_component_instance where comp_code=? and dest_object=? and type=?", componentCode, destCode, type);
    }

    public boolean newObjectConfig(ViewComponent component, String objectCode) {
        Record record = getRecord(component, objectCode, INSTANCE.META_OBJECT);
        return Db.save("meta_component_instance", record);
    }

    public boolean newSpecificConfig(ViewComponent component, String specificCode) {
        Record record = getRecord(component, specificCode, INSTANCE.SPECIFIC);
        return Db.save("meta_component_instance", record);
    }

    public Map getSpecificConfig(String componentCode, String specificCode) {
        String config = Db.queryStr("select * from meta_component_instance where comp_code=? and dest_object", componentCode, specificCode);
        return JSON.parseObject(config, Map.class);
    }

    private Record getRecord(ViewComponent component, String specificCode, INSTANCE specific) {
        Record record = new Record();
        record.set("id", StrKit.getRandomUUID());
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
        META_OBJECT, META_FIELD, SPECIFIC,
    }
}
