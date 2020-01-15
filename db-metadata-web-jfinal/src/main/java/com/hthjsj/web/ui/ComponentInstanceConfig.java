package com.hthjsj.web.ui;

import com.hthjsj.web.kit.UtilKit;
import com.jfinal.kit.Kv;
import lombok.Getter;

/**
 * <pre>
 *    Data structure:
 *    {
 *        meta_object_code_abc:{config object},
 *        fieldsMap:{
 *             meta_field1:{config object},
 *             meta_field2:{config object},
 *             meta_field3:{config object}
 *        }
 *    }
 * </pre>
 * <p> @Date : 2020/1/15 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
public class ComponentInstanceConfig extends Kv {

    @Getter
    String instanceCode;

    @Getter
    String instanceName;

    String objectCode;

    Kv self;

    Kv fieldsMap;

    private ComponentInstanceConfig(Kv config, String objectCode, String instanceCode, String instanceName) {
        this.self = UtilKit.getKv(config, objectCode);
        this.fieldsMap = UtilKit.getKv(config, "fieldsMap");
        this.objectCode = objectCode;
        this.instanceCode = instanceCode;
        this.instanceName = instanceName;
        this.set(self).set("fieldsMap", fieldsMap).set("instanceCode", instanceCode).set("instanceName", instanceName);
    }

    private ComponentInstanceConfig(Kv objectConfig, Kv fieldsMap, String objectCode, String instanceCode, String instanceName) {
        this.self = objectConfig;
        this.fieldsMap = fieldsMap;
        this.objectCode = objectCode;
        this.instanceCode = instanceCode;
        this.instanceName = instanceName;
        this.set(self).set("fieldsMap", fieldsMap).set("instanceCode", instanceCode).set("instanceName", instanceName);
    }

    public static ComponentInstanceConfig New(Kv config, String objectCode, String instanceCode, String instanceName) {
        return new ComponentInstanceConfig(config, objectCode, instanceCode, instanceName);
    }

    public static ComponentInstanceConfig Load(Kv objectConfig, Kv fieldsMap, String objectCode, String instanceCode, String instanceName) {
        return new ComponentInstanceConfig(objectConfig, fieldsMap, objectCode, instanceCode, instanceName);
    }

    public Kv getObjectConfig() {
        return self;
    }

    public Kv getFieldsMap() {
        return fieldsMap;
    }
}
