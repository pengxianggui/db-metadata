package com.hthjsj.analysis.meta;

import com.jfinal.plugin.activerecord.Record;

import java.util.Collection;

/**
 * <p> Class title: </p>
 * <p> @Describe: </p>
 * <p> @Date : 2019-08-22 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
public class SimpleDBMetaObject extends Record implements MetaObject {
    
    public SimpleDBMetaObject() {
    }
    
    @Override
    public String code() {
        return null;
    }
    
    @Override
    public String name() {
        return null;
    }
    
    @Override
    public String tableName() {
        return null;
    }
    
    @Override
    public String dataSource() {
        return null;
    }
    
    @Override
    public String[] primarys() {
        return new String[0];
    }
    
    @Override
    public Collection<MetaField> getFields() {
        return null;
    }
}
