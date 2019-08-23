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
public class MetaFieldConfig implements MetaConfig {
    
    public static final String VERSION_0_1 = "0.1";
    
    private String config;
    private String objectCode;
    private String fieldCode;
    
    public MetaFieldConfig(String config, String objectCode, String fieldCode) {
        this.config = config;
        this.objectCode = objectCode;
        this.fieldCode = fieldCode;
    }
    
    @Override
    public JSONObject toJson() {
        return JSON.parseObject(getConfig());
    }
    
    @Override
    public String module() {
        return MODULE_FIELD;
    }
    
    @Override
    public String moduleCode() {
        return new StringBuilder(objectCode).append(".").append(fieldCode).toString();
    }
    
    @Override
    public String getVersion() {
        return null;
    }
    
    @Override
    public String getConfig() {
        return config;
    }
}
