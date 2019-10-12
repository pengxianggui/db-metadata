package com.hthjsj.web.component;

import com.jfinal.aop.Before;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.plugin.activerecord.tx.Tx;

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

    public Record load(String componentCode, String objectCode) {
        return Db.findFirst("select * from meta_component where comp_code=? and object_code", componentCode, objectCode);
    }

    public boolean newComponentInstance(ViewComponent component, String objectCode) {
        Record record = new Record();
        record.set("id", StrKit.getRandomUUID());
        record.set("comp_code", component.code());
        record.set("object_code", objectCode);
        record.set("config", component.config());
        return Db.save("meta_component_instance", record);
    }
}
