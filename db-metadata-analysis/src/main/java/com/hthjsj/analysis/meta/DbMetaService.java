package com.hthjsj.analysis.meta;

import com.hthjsj.App;
import com.hthjsj.analysis.db.*;
import com.jfinal.aop.Aop;
import com.jfinal.aop.Before;
import com.jfinal.json.Json;
import com.jfinal.kit.Kv;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.plugin.activerecord.tx.Tx;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * <p> @Date : 2019-08-22 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
@Slf4j
@Before(Tx.class)
public class DbMetaService {

    public IMetaObject importFromTable(String schema, String table) {
        DbService dbService = Aop.get(MysqlService.class);
        Table t = dbService.getTable(schema, table);
        MetaObjectAssembly<Table, IMetaObject> dbMetaObjectAssembly = new MetaObjectMysqlAssembly();
        return dbMetaObjectAssembly.assembly(t);
    }

    public boolean isExists(String objectCode) {
        return Db.queryInt("select count(1) from meta_object where code=?", objectCode) == 0;
    }

    public IMetaObject findByCode(String objectCode) {
        if (StrKit.isBlank(objectCode)) {
            throw new MetaOperateException("必须指定元对象编码,当前元对象编码:%s", objectCode);
        }
        Record moRecord = Db.use(App.DB_MAIN).findFirst("select * from meta_object where code=?", objectCode);
        if (moRecord == null) {
            throw new MetaOperateException("无效的元对象编码: %s ", objectCode);
        }
        List<Record> metafields = Db.use(App.DB_MAIN).find("select * from meta_field where object_code=? order by order_num ", objectCode);
        IMetaObject IMetaObject = new MetaObject(moRecord.getColumns());
        for (Record metafield : metafields) {
            MetaField defaultMetaField = new MetaField(metafield.getColumns());
            IMetaObject.addField(defaultMetaField);
        }
        return IMetaObject;
    }

    public IMetaField findFieldByCode(String objectCode, String fieldCode) {
        IMetaField metaField = null;
        if (StrKit.notBlank(objectCode, fieldCode)) {
            Record field = Db.use(App.DB_MAIN).findFirst("select * from meta_field where object_code=? and field_code=?", objectCode, fieldCode);
            if (field == null) {
                throw new MetaOperateException("未查询到结果.objectCode[%s],fieldCode[%s]", objectCode, fieldCode);
            }
            metaField = new MetaField(field.getColumns());
        } else {
            throw new MetaOperateException("元对象编码和字段编码必须指定,objectCode[%s],fieldCode[%s]", objectCode, fieldCode);
        }
        return metaField;
    }

    public boolean saveMetaObject(IMetaObject metaObject, boolean saveFields) {
        if (new MetaObjectConfigParse(metaObject.config(), metaObject.code()).isUUIDPrimary()) {
            metaObject.dataMap().put("id", SnowFlake.me().nextId());
        }
        boolean moSaved = Db.use(App.DB_MAIN).save("meta_object", new Record().setColumns(metaObject.dataMap()));
        if (saveFields) {
            List<Record> updateRecords = new ArrayList<>();
            metaObject.fields().forEach((re) -> {
                re.dataMap().put("id", SnowFlake.me().nextId());
                //TODO independent setting
                re.objectCode(metaObject.code());
                updateRecords.add(new Record().setColumns(re.dataMap()));
            });
            int[] result = Db.use(App.DB_MAIN).batchSave("meta_field", updateRecords, 50);
            log.info("batchSave result:{}", Arrays.toString(result));
        }
        return moSaved;
    }

    public boolean updateMetaObject(IMetaObject metaObject) {
        boolean moUpdated = Db.use(App.DB_MAIN).update("meta_object", metaObject.primaryKey(), new Record().setColumns(metaObject.dataMap()));
        List<Record> updateRecords = new ArrayList<>();
        metaObject.fields().forEach((re) -> {
            updateRecords.add(new Record().setColumns(re.dataMap()));
        });
        Db.use(App.DB_MAIN).batchUpdate("meta_field", updateRecords, 50);
        return moUpdated;
    }

    public boolean deleteMetaObject(String objectCode) {
        return Db.use(App.DB_MAIN).delete("delete from meta_object where code=?", objectCode) > 0 && Db.use(App.DB_MAIN).delete(
                "delete from meta_field where object_code=?",
                objectCode) > 0;
    }

    public boolean saveData(IMetaObject object, Kv data) {
        boolean status = Db.use(App.DB_MAIN).save(object.tableName(), object.primaryKey(), new Record().setColumns(data));
        buriedPoint(object, Kv.create(), data);
        return status;
    }

    public Record findData(IMetaObject object, String id) {
        return Db.findById(object.tableName(), id);
    }

    public boolean updateData(IMetaObject object, Kv data) {
        // TODO support single primaryKey;
        Record old = Db.use(App.DB_MAIN).findById(object.tableName(), data.get(object.primaryKey()));
        boolean status = Db.use(App.DB_MAIN).update(object.tableName(), object.primaryKey(), new Record().setColumns(data));
        buriedPoint(object, old.getColumns(), data);
        return status;
    }

    public boolean deleteData(IMetaObject object, String[] ids) {
        return Db.deleteByIds(object.tableName(), object.primaryKey(), ids);
    }

    /**
     * 对通过元对象save/update的的数据进行保存;
     *
     * @param object
     * @param oldData
     * @param newData
     */
    private void buriedPoint(IMetaObject object, Map oldData, Map newData) {
        Record record = new Record();
        record.set("id", SnowFlake.me().nextId());
        record.set("object_code", object.code());
        record.set("table_name", object.tableName());
        record.set("pkey", object.primaryKey());
        // TODO support single primaryKey;
        record.set("pvalue", newData.get(object.primaryKey()));
        record.set("olddata", Json.getJson().toJson(oldData));
        record.set("newData", Json.getJson().toJson(newData));
        Db.use(App.DB_MAIN).save("change_log", record);
    }
}
