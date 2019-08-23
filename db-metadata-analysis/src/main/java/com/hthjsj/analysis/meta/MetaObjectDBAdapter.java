package com.hthjsj.analysis.meta;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;

import java.util.UUID;

/**
 * <p> Class title: </p>
 * <p> @Describe: </p>
 * <p> @Date : 2019-08-23 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
public abstract class MetaObjectDBAdapter implements MetaObject, MetaObjectDbBehavior {
    
    Record     record;
    MetaObject metaObject;
    
    MetaObjectDbBehavior metaObjectDbBehavior;
    
    public MetaObjectDBAdapter(Record record) {
        this.record = record;
        this.metaObject = MetaObjectFacroty.create(record);
        this.metaObjectDbBehavior = new MetaObjectDbBehaviorImpl(record, metaObject);
    }
    
    public MetaObjectDBAdapter(Record record, MetaObjectDbBehavior metaObjectDbBehavior) {
        this.record = record;
        this.metaObject = MetaObjectFacroty.create(record);
        this.metaObjectDbBehavior = metaObjectDbBehavior;
    }
    
    public Record getRecord() {
        return record;
    }
    
    @Override
    public Object save() {
        return metaObjectDbBehavior.save();
    }
    
    @Override
    public boolean update() {
        return metaObjectDbBehavior.update();
    }
    
    @Override
    public boolean delete() {
        return metaObjectDbBehavior.delete();
    }
    
    static class MetaObjectDbBehaviorImpl implements MetaObjectDbBehavior {
        
        Record     record;
        MetaObject metaObject;
        
        public MetaObjectDbBehaviorImpl(Record record, MetaObject metaObject) {
            this.record = record;
            this.metaObject = metaObject;
        }
        
        @Override
        public Object save() {
            Db.save(metaObject.tableName(), UUID.randomUUID().toString().replaceAll("-", ""), record);
            return record.getStr("id");
        }
        
        @Override
        public boolean update() {
            return Db.update(metaObject.tableName(), metaObject.primaryKey(), record);
        }
        
        @Override
        public boolean delete() {
            return Db.deleteByIds(metaObject.tableName(), metaObject.primaryKey(), record);
        }
    }
}
