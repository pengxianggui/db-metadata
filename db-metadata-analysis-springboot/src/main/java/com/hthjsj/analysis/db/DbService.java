package com.hthjsj.analysis.db;

import java.util.List;

/**
 * <p> Class title: DB 层,表数据分解工具</p>
 * <p> @Date : 2019-08-20 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
public interface DbService {

    List<String> showSchema();

    List<Table> showTables(String schema);

    Table getTable(String schema, String tableName);

    List<Column> getColumns(String schema, String tableName);
}
