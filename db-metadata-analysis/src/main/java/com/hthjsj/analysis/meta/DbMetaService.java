package com.hthjsj.analysis.meta;

import com.hthjsj.analysis.db.DbService;
import com.hthjsj.analysis.db.MysqlService;
import com.hthjsj.analysis.db.Table;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;

import java.util.List;

/**
 * <p> Class title: </p>
 * <p> @Describe: </p>
 * <p> @Date : 2019-08-22 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
public class DbMetaService {
    
    public MetaObject importFromTable(String schema, String table) {
        DbService dbService = new MysqlService();
        Table t = dbService.getTable(schema, table);
        
        DBMetaObjectAssembly dbMetaObjectAssembly = new DBMetaObjectAssembly();
        
        return dbMetaObjectAssembly.assembly(t);
    }
    
    public MetaObject findByCode(String code) {
        Record moRecord = Db.findFirst("select * from meta_object where code=?", code);
        List<Record> metafields = Db.find("select * from meta_field where object_code=? order by order_num ", code);
        MetaObject metaObject = new MetaObject.DefaultMetaObject(moRecord);
        for (Record metafield : metafields) {
            MetaField.DefaultMetaField defaultMetaField = new MetaField.DefaultMetaField(metafield);
            metaObject.addField(defaultMetaField);
        }
        return metaObject;
    }
    
}
