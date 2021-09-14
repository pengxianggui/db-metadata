package com.github.md.analysis.db;

import com.github.md.analysis.SpringAnalysisManager;
import com.github.md.analysis.MetaAnalysisException;
import com.github.md.analysis.db.registry.DataSourceManager;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * <p> Mysql 元数据的默认获取方法</p>
 * <p> @Describe: </p>
 * <p> @Date : 2019-08-20 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
@Service
@Transactional
public class MysqlService implements DbService {

    DataSourceManager dataSourceManager;

    public MysqlService(DataSourceManager dataSourceManager) {
        this.dataSourceManager = dataSourceManager;
    }

    @Override
    public List<String> showSchema() {
        return dataSourceManager.sourceNameList();
    }

    @Override
    public List<Table> showTables(String schema) {
        if (StrKit.isBlank(schema)) {
            throw new MetaAnalysisException("必须指定schema");
        }

        //多数据源下,找到schema对应的dbConfig
        Optional<String> schemaName = showSchema().stream().filter(s -> schema.equalsIgnoreCase(s)).findFirst();

        List<Record> records = null;
        String showTablesSql = "select * from information_schema.tables where table_schema=? ";
        if (schemaName.isPresent()) {
            records = Db.use(schema).find(showTablesSql, schema);
        } else {
            records = SpringAnalysisManager.me().dbMain().find(showTablesSql, schema);
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
        Optional<String> schemaName = showSchema().stream().filter(s -> schema.equalsIgnoreCase(s)).findFirst();
        Record record = null;
        String getTableSql = "select * from information_schema.tables where table_schema=? and table_name=?";
        if (schemaName.isPresent()) {
            record = Db.use(schema).findFirst(getTableSql, schema, tableName);
        } else {
            record = SpringAnalysisManager.me().dbMain().findFirst(getTableSql, schema, tableName);
        }
        Table table = new Table(record);
        return table.setColumns(getColumns(schema, tableName));
    }

    @Override
    public List<Column> getColumns(String schema, String tableName) {

        //多数据源下,找到schema对应的dbConfig
        Optional<String> schemaName = showSchema().stream().filter(s -> schema.equalsIgnoreCase(s)).findFirst();

        List<Record> records = null;
        String showColumns = "select * from information_schema.columns where table_schema=? and table_name=?";
        if (schemaName.isPresent()) {
            records = Db.use(schema).find(showColumns, schema, tableName);
        } else {
            records = SpringAnalysisManager.me().dbMain().find(showColumns, schema, tableName);
        }
        List<Column> result = new ArrayList<>();
        for (Record record : records) {
            result.add(new Column(record.getColumns()));
        }
        return result;
    }
}
