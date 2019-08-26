package com.hthjsj.analysis.meta;

import com.alibaba.fastjson.annotation.JSONField;
import com.jfinal.plugin.activerecord.Record;

/**
 * <p> Class title: </p>
 * <p> @Describe: </p>
 * <p> @Date : 2019-08-20 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
public interface MetaField {
    
    @JSONField(name = "isPrimary")
    boolean isPrimary();
    
    @JSONField(name = "isPrimary")
    void isPrimary(boolean value);
    
    @JSONField(name = "fieldCode")
    String fieldCode();
    
    @JSONField(name = "fieldCode")
    void fieldCode(String value);
    
    @JSONField(name = "objectCode")
    String objectCode();
    
    @JSONField(name = "objectCode")
    void objectCode(String value);
    
    @JSONField(name = "cn")
    String cn();
    
    @JSONField(name = "cn")
    void cn(String value);
    
    @JSONField(name = "en")
    String en();
    
    @JSONField(name = "en")
    void en(String value);
    
    @JSONField(name = "dbType")
    String dbType();
    
    @JSONField(name = "dbType")
    void dbType(String value);
    
    @JSONField(name = "javaType")
    String javaType();
    
    @JSONField(name = "javaType")
    void javaType(String value);
    
    @JSONField(name = "orderNum")
    int orderNum();
    
    @JSONField(name = "orderNum")
    void orderNum(int value);
    
    @JSONField(name = "config")
    MetaConfig config();
    
    void config(String config);
    
    void config(MetaConfig config);
    
    class DefaultMetaField implements MetaField {
        
        Record     record;
        MetaConfig metaFieldConfig;
        
        public DefaultMetaField(Record record) {
            this.record = record;
        }
        
        @Override
        public boolean isPrimary() {
            return record.getBoolean("is_primary");
        }
        
        @Override
        public void isPrimary(boolean value) {
            record.set("is_primary", value);
        }
        
        @Override
        public String objectCode() {
            return record.getStr("object_code");
        }
        
        @Override
        public void objectCode(String value) {
            record.set("object_code", value);
        }
        
        @Override
        public String cn() {
            return record.getStr("cn");
        }
        
        @Override
        public void cn(String value) {
            record.set("cn", value);
        }
        
        @Override
        public String en() {
            return record.getStr("en");
        }
        
        @Override
        public void en(String value) {
            record.set("en", value);
        }
        
        @Override
        public String dbType() {
            return record.getStr("db_type");
        }
        
        @Override
        public void dbType(String value) {
            record.set("db_type", value);
        }
        
        @Override
        public String javaType() {
            return record.getStr("java_type");
        }
        
        @Override
        public void javaType(String value) {
            record.set("java_type", value);
        }
        
        @Override
        public String fieldCode() {
            return record.getStr("field_code");
        }
        
        @Override
        public void fieldCode(String value) {
            record.set("field_code", value);
        }
        
        @Override
        public int orderNum() {
            return record.getInt("order_num");
        }
        
        @Override
        public void orderNum(int value) {
            record.set("order_num", value);
        }
        
        @Override
        public MetaConfig config() {
            return new MetaConfigFactory.MetaFieldConfig(record.getStr("config"), objectCode(), fieldCode());
        }
        
        @Override
        public void config(String config) {
            record.set("config", config);
        }
        
        @Override
        public void config(MetaConfig config) {
            metaFieldConfig = config;
            record.set("config", config.getConfig());
        }
    }
}

