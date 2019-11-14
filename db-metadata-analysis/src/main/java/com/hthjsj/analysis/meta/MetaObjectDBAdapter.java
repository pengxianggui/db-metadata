package com.hthjsj.analysis.meta;

import com.jfinal.aop.Aop;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;

import java.util.Collection;
import java.util.Map;

/**
 * <p> Class title: </p>
 * <p> @Describe: </p>
 * <p> @Date : 2019-08-23 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
public class MetaObjectDBAdapter implements IMetaObject, Storage {

    Record record;

    IMetaObject metaObject;

    Storage storage;

    public MetaObjectDBAdapter(MetaObject object) {
        this.metaObject = object;
        this.record = object.record;
        this.storage = new DBStorageImpl();
    }

    public MetaObjectDBAdapter(Record record, Storage storage) {
        this.record = record;
        this.metaObject = new MetaObject(record.getColumns());
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

    @Override
    public String code() {
        return metaObject.code();
    }

    @Override
    public void code(String value) {
        metaObject.code(value);
    }

    @Override
    public String name() {
        return metaObject.name();
    }

    @Override
    public void name(String value) {
        metaObject.name(value);
    }

    @Override
    public String tableName() {
        return metaObject.tableName();
    }

    @Override
    public void tableName(String value) {
        metaObject.tableName(value);
    }

    @Override
    public String schemaName() {
        return metaObject.schemaName();
    }

    @Override
    public void schemaName(String value) {
        metaObject.schemaName(value);
    }

    @Override
    public IMetaField[] primarys() {
        return metaObject.primarys();
    }

    @Override
    public void primarys(IMetaField[] value) {
        metaObject.primarys(value);
    }

    @Override
    public String primaryKey() {
        return metaObject.primaryKey();
    }

    @Override
    public Collection<IMetaField> fields() {
        return metaObject.fields();
    }

    @Override
    public void fields(Collection<IMetaField> value) {
        metaObject.fields(value);
    }

    @Override
    public void addField(IMetaField value) {
        metaObject.addField(value);
    }

    @Override
    public String config() {
        return metaObject.config();
    }

    @Override
    public boolean isSystem() {
        return metaObject.isSystem();
    }

    @Override
    public void config(String config) {
        metaObject.config(config);
    }

    @Override
    public void config(IMetaConfig config) {
        metaObject.config(config);
    }

    @Override
    public Map<String, Object> dataMap() {
        return metaObject.dataMap();
    }

    @Override
    public void dataMap(Map<String, Object> data) {
        metaObject.dataMap(data);
    }

    class DBStorageImpl implements Storage {

        @Override
        public Object save() {
            Aop.get(DbMetaService.class).saveMetaObject(metaObject, true);
            return metaObject.dataMap().get(metaObject.primaryKey());
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
