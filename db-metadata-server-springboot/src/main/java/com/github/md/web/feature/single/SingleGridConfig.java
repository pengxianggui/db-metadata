package com.github.md.web.feature.single;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.md.analysis.component.ComponentType;
import com.github.md.web.feature.FeatureConfig;
import lombok.Data;

/**
 * @author pengxg
 * @date
 */
@Data
public class SingleGridConfig extends FeatureConfig {
    private JSONObject config;
    private JSONObject instanceCodes;

    public String getInstanceCode(ComponentType componentType) {
        if (instanceCodes == null) {
            instanceCodes = JSON.parseObject(getStr("instanceCodes"));
        }
        return instanceCodes.getString(componentType.getCode());
    }

    public String getObjectCode() {
        if (config == null) {
            config = JSON.parseObject(getStr("config"));
        }
        return config.getString("objectCode");
    }

}
