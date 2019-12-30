package com.hthjsj.analysis.db;

import com.hthjsj.AnalysisConfig;
import com.hthjsj.App;
import com.hthjsj.analysis.MetaAnalysisException;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * <p> Mysql 元数据的默认获取方法</p>
 * <p> @Describe: </p>
 * <p> @Date : 2019-08-20 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
public class MysqlService implements DbService {

    @Override
    public List<String> showSchema() {
        return AnalysisConfig.me().getDbSources().stream().map(AnalysisConfig.DBSource::configName).collect(Collectors.toList());
    }

    @Override
    public List<Table> showTables(String schema) {
        if (StrKit.isBlank(schema)) {
            throw new MetaAnalysisException("必须指定schema");
        }

        //多数据源下,找到schema对应的dbConfig
        Optional<String> schemaName = showSchema().stream().filter(s -> schema.equalsIgnoreCase(s)).findFirst();

        List<Record> records = null;
        if (schemaName.isPresent()) {
            records = Db.use(schema).find("select * from information_schema.tables where table_schema=? ", schema);
        } else {
            records = Db.use(App.DB_MAIN).find("select * from information_schema.tables where table_schema=? ", schema);
        }

        if (records == null || records.isEmpty()) {
            throw new MetaAnalysisException("未查询到[%s]下的表数据", schema);
        }

        List<Table> tables = new ArrayList<>();
        for (Record r : records) {
            tables.add(new Table(r));
        }

        return tables;
    }

    @Override
    public Table getTable(String schema, String tableName) {
        Record record = Db.use(App.DB_MAIN).findFirst("select * from information_schema.tables where table_schema=? and table_name=?", schema, tableName);
        Table table = new Table(record);
        return table.setColumns(getColumns(schema, tableName));
    }

    @Override
    public List<Column> getColumns(String schema, String tableName) {
        List<Record> records = Db.use(App.DB_MAIN).find("select * from information_schema.columns where table_schema=? and table_name=?", schema, tableName);
        List<Column> result = new ArrayList<>();
        for (Record record : records) {
            result.add(new Column(record.getColumns()));
        }
        return result;
    }
}
