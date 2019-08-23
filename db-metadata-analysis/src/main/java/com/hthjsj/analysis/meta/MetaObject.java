package com.hthjsj.analysis.meta;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.Collection;

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
    
    @JSONField(name = "config")
    MetaConfig config();
    
    void config(String config);
    
    void config(MetaConfig config);
}

