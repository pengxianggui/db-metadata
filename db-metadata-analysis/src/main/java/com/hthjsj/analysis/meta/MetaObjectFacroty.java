package com.hthjsj.analysis.meta;

import com.jfinal.plugin.activerecord.Record;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * <p> Class title: </p>
 * <p> @Describe: </p>
 * <p> @Date : 2019-08-23 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
public class MetaObjectFacroty {
    
    public static DefaultMetaObject create(Record record) {
        return new DefaultMetaObject(record);
    }
    
    public static DefaultMetaField createField(Record record) {
        return new DefaultMetaField(record);
    }
    
    public static class DefaultMetaField implements MetaField {
        
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
    
    public static class DefaultMetaObject implements MetaObject {
        
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
