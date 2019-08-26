package com.hthjsj.analysis.meta;

import com.alibaba.fastjson.annotation.JSONField;
import com.jfinal.plugin.activerecord.Record;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * <p> Class title: </p>
 * <p> @Describe: </p>
 * <p> @Date : 2019-08-20 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
public interface MetaObject {
    
    @JSONField(name = "code")
    String code();
    
    @JSONField(name = "code")
    void code(String value);
    
    @JSONField(name = "name")
    String name();
    
    @JSONField(name = "name")
    void name(String value);
    
    @JSONField(name = "tableName")
    String tableName();
    
    @JSONField(name = "tableName")
    void tableName(String value);
    
    @JSONField(name = "schemaName")
    String schemaName();
    
    @JSONField(name = "schemaName")
    void schemaName(String value);
    
    @JSONField(name = "primarys")
    MetaField[] primarys();
    
    @JSONField(name = "primarys")
    void primarys(MetaField[] value);
    
    @JSONField(name = "primaryKey")
    String primaryKey();
    
    @JSONField(name = "fields")
    Collection<MetaField> fields();
    
    @JSONField(name = "fields")
    void fields(Collection<MetaField> value);
    
    void addField(MetaField value);
    
    @JSONField(name = "config")
    MetaConfig config();
    
    void config(String config);
    
    void config(MetaConfig config);
    
    class DefaultMetaObject implements MetaObject {
        
        List<MetaField> primarys = new ArrayList<>();
        List<MetaField> fields   = new ArrayList<>();
        Record          record;
        MetaConfig      metaConfig;
        
        public DefaultMetaObject(Record record) {
            this.record = record;
        }
        
        @Override
        public String code() {
            return record.getStr("code");
        }
        
        @Override
        public void code(String value) {
            record.set("code", value);
        }
        
        @Override
        public String name() {
            return record.getStr("name");
        }
        
        @Override
        public void name(String value) {
            record.set("name", value);
        }
        
        @Override
        public String tableName() {
            return record.getStr("table_name");
        }
        
        @Override
        public void tableName(String value) {
            record.set("table_name", value);
        }
        
        @Override
        public String schemaName() {
            return record.getStr("schema_name");
        }
        
        @Override
        public void schemaName(String value) {
            record.set("schema_name", value);
        }
        
        public void setCode(String code) {
            record.set("code", code);
        }
        
        public void setName(String name) {
            record.set("name", name);
        }
        
        public void setTableName(String tableName) {
            record.set("table_name", tableName);
        }
        
        public void setSchemaName(String schemaName) {
            record.set("schema_name", schemaName);
        }
        
        @Override
        public MetaField[] primarys() {
            if (primarys.isEmpty()) {
                for (MetaField field : fields) {
                    if (field.isPrimary()) {
                        primarys.add(field);
                    }
                }
            }
            return primarys.toArray(new MetaField[10]);
        }
        
        @Override
        public void primarys(MetaField[] primarys) {
            StringBuilder sb = new StringBuilder();
            for (MetaField mf : primarys) {
                sb.append(",").append(mf.fieldCode());
            }
            record.set("primarys", sb.substring(1));
        }
        
        @Override
        public String primaryKey() {
            return record.getStr("primarys");
        }
        
        @Override
        public Collection<MetaField> fields() {
            return fields;
        }
        
        @Override
        public void fields(Collection<MetaField> fields) {
            this.fields = new ArrayList<>(fields);
        }
        
        @Override
        public void addField(MetaField value) {
            fields.add(value);
        }
        
        @Override
        public MetaConfig config() {
            return new MetaConfigFactory.MetaObjectConfig(record.getStr("config"), code());
        }
        
        @Override
        public void config(String config) {
            record.set("config", config);
        }
        
        @Override
        public void config(MetaConfig config) {
            this.metaConfig = config;
            record.set("config", metaConfig.getConfig());
        }
        
    }
}

