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
public class MetaConfigFactory {
    
    public static MetaFieldConfig create(String config, String objectCode, String fieldCode) {
        return new MetaFieldConfig(config, objectCode, fieldCode);
    }
    
    public static MetaObjectConfig create(String config, String objectCode) {
        return new MetaObjectConfig(config, objectCode);
    }
    
    public static class MetaFieldConfig implements MetaConfig {
        
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
    
    public static class MetaObjectConfig implements MetaConfig {
        
        public static final String VERSION_0_1 = "0.1";
        private             String config;
        private             String objectCode;
        
        public MetaObjectConfig(String config, String objectCode) {
            this.config = config;
            this.objectCode = objectCode;
        }
        
        @Override
        public JSONObject toJson() {
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
}
