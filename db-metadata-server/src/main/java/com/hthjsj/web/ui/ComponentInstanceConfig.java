package com.hthjsj.web.ui;

import com.alibaba.fastjson.annotation.JSONField;
import com.hthjsj.analysis.component.ComponentType;
import com.hthjsj.web.kit.UtilKit;
import com.jfinal.kit.Kv;
import com.jfinal.kit.Okv;
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

    Okv fieldsMap;

    @JSONField(serialize = false)
    @Getter
    ComponentType containerType;

    private ComponentInstanceConfig(Kv configs, String objectCode, String instanceCode, String instanceName, ComponentType containerType) {
        this.self = Kv.by(objectCode, UtilKit.getKv(configs.getStr(objectCode)));
        this.fieldsMap = UtilKit.getOKv(configs, "fieldsMap");
        this.objectCode = objectCode;
        this.instanceCode = instanceCode;
        this.instanceName = instanceName;
        this.containerType = containerType;
        this.set(self).set("fieldsMap", fieldsMap).set("instanceCode", instanceCode).set("instanceName", instanceName);
    }

    private ComponentInstanceConfig(Kv objectConfig, Okv fieldsMap, String objectCode, String instanceCode, String instanceName, ComponentType containerType) {
        this.self = objectConfig;
        this.fieldsMap = fieldsMap;
        this.objectCode = objectCode;
        this.instanceCode = instanceCode;
        this.instanceName = instanceName;
        this.containerType = containerType;
        this.set(self).set("fieldsMap", fieldsMap).set("instanceCode", instanceCode).set("instanceName", instanceName);
    }

    public static ComponentInstanceConfig New(Kv config, String objectCode, String instanceCode, String instanceName, ComponentType containerType) {
        return new ComponentInstanceConfig(config, objectCode, instanceCode, instanceName, containerType);
    }

    public static ComponentInstanceConfig Load(Kv objectConfig, Okv fieldsMap, String objectCode, String instanceCode, String instanceName, ComponentType containerType) {
        return new ComponentInstanceConfig(objectConfig, fieldsMap, objectCode, instanceCode, instanceName, containerType);
    }

    /**
     * expect : {component_name: "TableList", delete_url: "/table/delete/meta_component_instance",…}
     * actual : meta_component_instance: {component_name: "TableList", delete_url: "/table/delete/meta_component_instance",…}
     *
     * @return
     */
    public Kv getObjectConfig() {
        return self.getAs(objectCode);
    }

    public Okv getFieldsMap() {
        return fieldsMap;
    }
}
