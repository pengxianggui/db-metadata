package com.hthjsj.analysis.meta;

import sun.jvm.hotspot.oops.Metadata;

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
    
    String dataSource();
    
    String[] primarys();
    
    Collection<MetaField> getFields();
}

interface MetaObjectAccess {
    
    boolean isSingle();
    
    boolean isShowRowNum();
}

interface MetaObjectDbBehavior {
    
    Metadata save(MetaObject object, Metadata data);
    
    int update(MetaObject object, Metadata data);
    
    int delete(MetaObject object, Metadata data);
    
}
