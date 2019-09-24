package com.hthjsj.analysis.meta;

import com.hthjsj.App;
import com.hthjsj.analysis.db.*;
import com.jfinal.aop.Aop;
import com.jfinal.aop.Before;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.plugin.activerecord.tx.Tx;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * <p> Class title: </p>
 * <p> @Describe: </p>
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

    public IMetaObject findByCode(String code) {
        Record moRecord = Db.use(App.DB_MAIN).findFirst("select * from meta_object where code=?", code);
        List<Record> metafields = Db.use(App.DB_MAIN).find("select * from meta_field where object_code=? order by order_num ", code);
        IMetaObject IMetaObject = new MetaObject(moRecord.getColumns());
        for (Record metafield : metafields) {
            MetaField defaultMetaField = new MetaField(metafield.getColumns());
            IMetaObject.addField(defaultMetaField);
        }
        return IMetaObject;
    }

    public boolean saveMetaObject(IMetaObject metaObject, boolean saveFields) {

        if (((MetaConfigFactory.MetaObjectConfig) metaObject.config()).isUUIDPrimary()) {
            metaObject.dataMap().put("id", StrKit.getRandomUUID());
        }
        boolean moSaved = Db.use(App.DB_MAIN).save("meta_object", new Record().setColumns(metaObject.dataMap()));
        if (saveFields) {
            List<Record> updateRecords = new ArrayList<>();
            metaObject.fields().forEach((re) -> {
                re.dataMap().put("id", StrKit.getRandomUUID());
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


    public void deleteMetaObject(MetaObject metaObject) {
        Db.use(App.DB_MAIN).delete("delete from meta_object where code=?", metaObject.code());
        Db.use(App.DB_MAIN).delete("delete from meta_field where object_code=?", metaObject.code());
    }

}
