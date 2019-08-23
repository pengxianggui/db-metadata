package com.hthjsj.analysis.db;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;

import java.util.ArrayList;
import java.util.List;

/**
 * <p> Class title: </p>
 * <p> @Describe: </p>
 * <p> @Date : 2019-08-20 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
public class MysqlService implements DbService {
    
    @Override
    public List<Table> showTables(String schema) {
        List<Record> records = Db.find("select * from tables where table_schema=? ", schema);
        List<Table> tables = new ArrayList<>();
        for (Record r : records) {
            tables.add(new Table(r));
        }
        return tables;
    }
    
    @Override
    public Table getTable(String schema, String tableName) {
        Record record = Db.findFirst("select * from tables where table_schema=? and table_name=?", schema, tableName);
        Table table = new Table(record);
        return table.setColumns(getColumns(schema, tableName));
    }
    
    @Override
    public List<Column> getColumns(String schema, String tableName) {
        List<Record> records = Db.find("select * from columns where table_schema=? and table_name=?", schema, tableName);
        List<Column> result = new ArrayList<>();
        for (Record record : records) {
            result.add(new Column(record));
        }
        return result;
    }
}
