package com.hthjsj.analysis.meta;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

/**
 * <p> Class title: </p>
 * <p> @Describe: </p>
 * <p> @Date : 2019-08-23 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
public class MetaObjectConfig implements MetaConfig {
    
    public static final String VERSION_0_1 = "0.1";
    private             String config;
    private             String objectCode;
    
    public MetaObjectConfig(String config, String objectCode) {
        this.config = config;
        this.objectCode = objectCode;
    }
    
    public JSONObject getJson() {
        return JSON.parseObject(getConfig());
    }
    
    @Override
    public String module() {
        return MODULE_OBJECT;
    }
    
    @Override
    public String moduleCode() {
        return objectCode;
    }
    
    @Override
    public String getVersion() {
        return VERSION_0_1;
    }
    
    @Override
    public String getConfig() {
        return config;
    }
    
    public void setConfig(String config) {
        this.config = config;
    }
}
