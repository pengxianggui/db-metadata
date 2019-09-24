package com.hthjsj.analysis.meta;

import com.hthjsj.analysis.db.*;
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
        DbService dbService = new MysqlService();
        Table t = dbService.getTable(schema, table);

        MetaObjectAssembly<Table, IMetaObject> dbMetaObjectAssembly = new MetaObjectMysqlAssembly();

        return dbMetaObjectAssembly.assembly(t);
    }

    public IMetaObject findByCode(String code) {
        Record moRecord = Db.findFirst("select * from meta_object where code=?", code);
        List<Record> metafields = Db.find("select * from meta_field where object_code=? order by order_num ", code);
        IMetaObject IMetaObject = new MetaObject(moRecord.getColumns());
        for (Record metafield : metafields) {
            MetaField defaultMetaField = new MetaField(metafield.getColumns());
            IMetaObject.addField(defaultMetaField);
        }
        return IMetaObject;
    }

    public boolean saveMetaObject(MetaObjectDBAdapter object, boolean saveFields) {

        if (((MetaConfigFactory.MetaObjectConfig) object.config()).isUUIDPrimary()) {
            object.record.set("id", StrKit.getRandomUUID());
        }
        boolean moSaved = Db.save("meta_object", object.record);
        if (saveFields) {
            List<Record> updateRecords = new ArrayList<>();
            object.fields().forEach((re) -> {
                re.dataMap().put("id", StrKit.getRandomUUID());
                updateRecords.add(new Record().setColumns(re.dataMap()));
            });
            int[] result = Db.batchSave("meta_field", updateRecords, 50);
            log.info("batchSave result:{}", Arrays.toString(result));
        }
        return moSaved;
    }

    public void deleteMetaObject(MetaObject metaObject) {
        Db.delete("delete from meta_object where code=?", metaObject.code());
        Db.delete("delete from meta_field where object_code=?", metaObject.code());
    }

}
