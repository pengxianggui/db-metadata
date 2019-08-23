package com.hthjsj.analysis.meta;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * <p> Class title: </p>
 * <p> @Describe: </p>
 * <p> @Date : 2019-08-22 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
public class SimpleMetaObject extends AbstractMetaObject {
    
    List<MetaField> primarys = new ArrayList<>();
    List<MetaField> fields   = new ArrayList<>();
    
    MetaObjectAccess     metaObjectAccess;
    MetaObjectDbBehavior metaObjectDbBehavior;
    
    public SimpleMetaObject() {
    }
    
    public SimpleMetaObject(MetaObjectAccess metaObjectAccess, MetaObjectDbBehavior metaObjectDbBehavior) {
        metaObjectAccess = metaObjectAccess;
        metaObjectDbBehavior = metaObjectDbBehavior;
    }
    
    @Override
    public String code() {
        return record.getStr("code");
    }
    
    @Override
    public String name() {
        return record.getStr("name");
    }
    
    @Override
    public String tableName() {
        return record.getStr("table_name");
    }
    
    @Override
    public String schemaName() {
        return record.getStr("schema_name");
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
    public Collection<MetaField> getFields() {
        return fields;
    }
    
    @Override
    public void setFields(Collection<MetaField> fields) {
        this.fields = new ArrayList<>(fields);
    }
    
    @Override
    public boolean isSingle() {
        return metaObjectAccess.isSingle();
    }
    
    @Override
    public boolean isShowRowNum() {
        return metaObjectAccess.isShowRowNum();
    }
    
    @Override
    public MetaData save(MetaObject object, MetaData data) {
        return metaObjectDbBehavior.save(object, data);
    }
    
    @Override
    public int update(MetaObject object, MetaData data) {
        return metaObjectDbBehavior.update(object, data);
    }
    
    @Override
    public int delete(MetaObject object, MetaData data) {
        return metaObjectDbBehavior.delete(object, data);
    }
    
    @Override
    MetaObjectConfig getConfig() {
        return new MetaObjectConfig(record.getStr("config"), code());
    }
}
