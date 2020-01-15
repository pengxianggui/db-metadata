package com.hthjsj.web.ui;

import com.hthjsj.web.kit.UtilKit;
import com.jfinal.kit.Kv;

/**
 * <p> @Date : 2020/1/15 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
public class ComponentInstanceConfig extends Kv {

    Kv self;

    Kv fieldsMap;

    public ComponentInstanceConfig(Kv config, String objectCode) {
        this.self = UtilKit.getKv(config, objectCode);
        this.fieldsMap = UtilKit.getKv(config, "fieldsMap");
    }

    public ComponentInstanceConfig(Kv objectConfig, Kv fieldsMap) {
        this.self = objectConfig;
        this.fieldsMap = fieldsMap;
    }

    public Kv getObjectConfig() {
        return self;
    }

    public Kv getFieldsMap() {
        return fieldsMap;
    }
}
