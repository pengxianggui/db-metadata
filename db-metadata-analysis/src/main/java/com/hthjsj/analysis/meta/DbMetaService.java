package com.hthjsj.analysis.meta;

import com.hthjsj.analysis.db.DbService;
import com.hthjsj.analysis.db.MysqlService;
import com.hthjsj.analysis.db.Table;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * <p> Class title: </p>
 * <p> @Describe: </p>
 * <p> @Date : 2019-08-22 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
public class DbMetaService {


    public IMetaObject importFromTable(String schema, String table) {
        DbService dbService = new MysqlService();
        Table t = dbService.getTable(schema, table);

        DBMetaObjectAssembly dbMetaObjectAssembly = new DBMetaObjectAssembly();

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

    public boolean saveMetaObject(MetaObjectDBAdapter object, List<IMetaField> fields) {

        object.record.set("id", UUID.randomUUID().toString().replaceAll("-", ""));

        boolean moSaved = Db.save("meta_object", object.record);

        List<Record> updateRecords = new ArrayList<>();
        fields.forEach((re) -> updateRecords.add(new Record().setColumns(re.dataMap())));

        int[] result = Db.batchSave("meta_field", updateRecords, 50);
        System.out.println(result);
        return moSaved;
    }

}
