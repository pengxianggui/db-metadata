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
public abstract class MetaObjectDBAdapter implements MetaObject, Storage {
    
    Record     record;
    MetaObject metaObject;
    
    Storage storage;
    
    public MetaObjectDBAdapter(Record record) {
        this.record = record;
        this.metaObject = new DefaultMetaObject(record);
        this.storage = new DBStorageImpl(record, metaObject);
    }
    
    public MetaObjectDBAdapter(Record record, Storage storage) {
        this.record = record;
        this.metaObject = new DefaultMetaObject(record);
        this.storage = storage;
    }
    
    public Record getRecord() {
        return record;
    }
    
    @Override
    public Object save() {
        return storage.save();
    }
    
    @Override
    public boolean update() {
        return storage.update();
    }
    
    @Override
    public boolean delete() {
        return storage.delete();
    }
    
    static class DBStorageImpl implements Storage {
        
        Record     record;
        MetaObject metaObject;
        
        public DBStorageImpl(Record record, MetaObject metaObject) {
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
