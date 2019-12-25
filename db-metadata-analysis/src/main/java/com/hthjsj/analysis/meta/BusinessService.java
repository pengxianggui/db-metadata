package com.hthjsj.analysis.meta;

import com.hthjsj.App;
import com.hthjsj.analysis.MetaAnalysisException;
import com.hthjsj.analysis.db.SnowFlake;
import com.jfinal.aop.Before;
import com.jfinal.json.Json;
import com.jfinal.kit.Kv;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.plugin.activerecord.tx.Tx;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

/**
 * 面向业务数据库查询的方法,主要是基于数据源不同而拆分开的
 * <p> @Date : 2019/12/10 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
@Slf4j
@Before(Tx.class)
public class BusinessService {

    public Record findDataByIds(IMetaObject object, Object... ids) {
        return Db.use(object.schemaName()).findByIds(object.tableName(), object.primaryKey(), ids);
    }

    public <T> T findDataFieldById(IMetaObject object, IMetaField metaField, String id) {
        //select metaField.fileCode() from object.tableName() where object.primarykey()=id
        if (object.isMultiplePrimaryKey()) {
            throw new MetaAnalysisException("%s 元对象为复合主键", object.code());
        }
        return (T) Db.use(object.schemaName()).queryFirst("select " + metaField.fieldCode() + " from " + object.tableName() + " where " + object.primaryKey() + "=?", id);
    }

    public boolean saveData(IMetaObject object, Kv data) {
        boolean status = Db.use(object.schemaName()).save(object.tableName(), object.primaryKey(), new Record().setColumns(data));
        buriedPoint(object, Kv.create(), data);
        return status;
    }

    public boolean updateData(IMetaObject object, MetaData data) {
        // TODO support single primaryKey;
        Record old = Db.use(object.schemaName()).findByIds(object.tableName(), object.primaryKey(), data.getPks(object.primaryKey()));
        boolean status = Db.use(object.schemaName()).update(object.tableName(), object.primaryKey(), new Record().setColumns(data));
        buriedPoint(object, old.getColumns(), data);
        return status;
    }

    public boolean deleteData(IMetaObject object, String[] ids) {
        if (object.isMultiplePrimaryKey()) {
            throw new MetaAnalysisException("%s 元对象为复合主键", object.code());
        }
        String idsString = StrKit.join(ids, "','");
        return Db.use(object.schemaName()).update("delete from " + object.tableName() + " where " + object.primaryKey() + " in ('" + idsString + "')") > 0;
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
