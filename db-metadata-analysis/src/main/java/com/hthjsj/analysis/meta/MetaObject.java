package com.hthjsj.analysis.meta;

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
    
    String code();
    
    String name();
    
    String tableName();
    
    String schemaName();
    
    MetaField[] primarys();
    
    Collection<MetaField> getFields();
    
    void setFields(Collection<MetaField> fields);
}

interface MetaObjectAccess {
    
    boolean isSingle();
    
    boolean isShowRowNum();
}

interface MetaObjectDbBehavior {
    
    MetaData save(MetaData data);
    
    int update(MetaData data);
    
    int delete(MetaData data);
    
}
