package com.hthjsj.analysis.meta;

import com.jfinal.plugin.activerecord.Record;

/**
 * <p> Class title: </p>
 * <p> @Describe: </p>
 * <p> @Date : 2019-08-23 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
public abstract class AbstractMetaField implements MetaField, MetaFieldAccess, MetaFieldShowBehavior {
    
    Record record;
    
    public Record getRecord() {
        return record;
    }
    
    public void setRecord(Record record) {
        this.record = record;
    }
    
    abstract MetaFieldConfig getConfig();
}
