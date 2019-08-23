package com.hthjsj.analysis.meta;

import com.hthjsj.analysis.db.Column;
import com.hthjsj.analysis.db.Table;

/**
 * <p> Class title: </p>
 * <p> @Describe: </p>
 * <p> @Date : 2019-08-22 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
public interface MetaObjectAssembly<T, M> {
    
    M assembly(T t);
}

class DBMetaObjectAssembly implements MetaObjectAssembly<Table, MetaObject> {
    
    @Override
    public MetaObject assembly(Table table) {
        SimpleMetaObject metaObject = new SimpleMetaObject(null, null);
        for (Column column : table.getColumns()) {
        
        }
        return metaObject;
    }
}
