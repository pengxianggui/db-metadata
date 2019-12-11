package com.hthjsj.analysis.meta;

import com.hthjsj.App;
import com.hthjsj.analysis.db.*;
import com.jfinal.aop.Aop;
import com.jfinal.aop.Before;
import com.jfinal.kit.Kv;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.plugin.activerecord.tx.Tx;
import com.jfinal.plugin.activerecord.tx.TxConfig;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * <p> @Date : 2019-08-22 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
@Slf4j
@TxConfig(App.DB_MAIN)
@Before(Tx.class)
public class DbMetaService {

    private BusinessService businessService = Aop.get(BusinessService.class);

    public IMetaObject importFromTable(String schema, String table) {
        DbService dbService = Aop.get(MysqlService.class);
        Table t = dbService.getTable(schema, table);
        MetaObjectAssembly<Table, IMetaObject> dbMetaObjectAssembly = new MetaObjectMysqlAssembly();
        return dbMetaObjectAssembly.assembly(t);
    }

    public boolean isExists(String objectCode) {
        return Db.use(App.DB_MAIN).queryInt("select count(1) from meta_object where code=?", objectCode) == 0;
    }

    public boolean isExists(IMetaObject metaObject, IMetaField metaField, Object value) {
        return Db.use(App.DB_MAIN).queryInt("select count(1) from " + metaObject.tableName() + " where " + metaField.fieldCode() + "=?", value) > 0;
    }

    public List<IMetaObject> findAll() {
        List<IMetaObject> result = new ArrayList<>();

        List<String> objs = Db.use(App.DB_MAIN).query("select code from meta_object");
        for (String metaObjectCode : objs) {
            result.add(findByCode(metaObjectCode));
        }
        return result;
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
        IMetaObject metaObject = new MetaObject(moRecord.getColumns());
        for (Record metafield : metafields) {
            MetaField defaultMetaField = new MetaField(metafield.getColumns());
            metaObject.addField(defaultMetaField);
        }
        return metaObject;
    }

    public List<IMetaObject> findByCodes(String... objectCodes) {
        List<IMetaObject> result = new ArrayList<>();
        for (String objectCode : objectCodes) {
            result.add(findByCode(objectCode));
        }
        return result;
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
        if (metaObject.configParser().isUUIDPrimary()) {
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

    /**
     * 获取该元对象在meta_object当中的记录
     * TODO [绕]
     *
     * @param objectCode
     *
     * @return
     */
    public Record findObjectRecordByCode(String objectCode) {
        return Db.use(App.DB_MAIN).findFirst("select * from meta_object where code=?", objectCode);
    }

    /**
     * 获取指定元对象,元字段 在meta_field当中的记录
     * TODO [绕]
     *
     * @param code
     * @param fieldCode
     *
     * @return
     */
    public Record findFieldRecordByCode(String code, String fieldCode) {
        return Db.use(App.DB_MAIN).findFirst("select * from meta_field where object_code=? and field_code=?", code, fieldCode);
    }

    //*****************业务操作(metadata数据库以外的操作迁移至businessService)**************************

    public boolean updateData(IMetaObject object, MetaData data) {
        return businessService.updateData(object, data);
    }

    public boolean deleteData(IMetaObject object, String[] ids) {
        return businessService.deleteData(object, ids);
    }

    public <T> T findDataFieldById(IMetaObject metaObject, IMetaField metaField, String id) {
        return businessService.findDataFieldById(metaObject, metaField, id);
    }

    public boolean saveData(IMetaObject object, Kv data) {
        return businessService.saveData(object, data);
    }

    public Record findDataByIds(IMetaObject object, Object... ids) {
        return businessService.findDataByIds(object, ids);
    }
}
