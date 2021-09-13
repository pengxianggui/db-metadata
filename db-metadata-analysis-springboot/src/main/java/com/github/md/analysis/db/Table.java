package com.github.md.analysis.db;

import com.jfinal.plugin.activerecord.Record;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Table
 * mysql: information_schema -> Tables表
 * <p>
 * 注:
 * TODO :现元数据通过sql直接查询mysql.information_schema获取,耦合度高,并且只用到了部分信息,后期可以考虑通过jdbc metadata来替换;
 * <p> Class title: </p>
 * <p> @Describe: </p>
 * <p> @Date : 2019-08-20 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
public class Table {

    private final Map<String, Object> dataMap;

    Record record;

    List<Column> columns;

    public Table(Record record) {
        this.record = record;
        dataMap = record.getColumns();
    }

    public Map<String, Object> dataMap() {
        return dataMap;
    }

    public List<Column> getColumns() {
        return columns;
    }

    public Table setColumns(List<Column> columns) {
        this.columns = columns;
        return this;
    }

    public String getTableCatalog() {
        return record.getStr("TABLE_CATALOG");
    }

    public String getTableSchema() {
        return record.getStr("TABLE_SCHEMA");
    }

    public String getTableName() {
        return record.getStr("TABLE_NAME");
    }

    public String getTableType() {
        return record.getStr("TABLE_TYPE");
    }

    public String getEngine() {
        return record.getStr("ENGINE");
    }

    public Long getVersion() {
        return record.getLong("VERSION");
    }

    public String getRowFormat() {
        return record.getStr("ROW_FORMAT");
    }

    public Long getTableRows() {
        return record.getLong("TABLE_ROWS");
    }

    public Long getAvgRowLength() {
        return record.getLong("AVG_ROW_LENGTH");
    }

    public Long getDataLength() {
        return record.getLong("DATA_LENGTH");
    }

    public Long getMaxDataLength() {
        return record.getLong("MAX_DATA_LENGTH");
    }

    public Long getIndexLength() {
        return record.getLong("INDEX_LENGTH");
    }

    public Long getDataFree() {
        return record.getLong("DATA_FREE");
    }

    /**
     * 如表中设置了自增主键，则AUTO_INCREMENT != null,表创建成功后AUTO_INCREMENT=1
     *
     * @return
     */
    public Long getAutoIncrement() {
        return record.getLong("AUTO_INCREMENT");
    }

    public Date getCreateTime() {
        return record.getDate("CREATE_TIME");
    }

    public Date getUpdateTime() {
        return record.getDate("UPDATE_TIME");
    }

    public Date getCheckTime() {
        return record.getDate("CHECK_TIME");
    }

    public String getTableCollation() {
        return record.getStr("TABLE_COLLATION");
    }

    public Long getChecksum() {
        return record.getLong("CHECKSUM");
    }

    public String getCreateOptions() {
        return record.getStr("CREATE_OPTIONS");
    }

    public String getTableComment() {
        return record.getStr("TABLE_COMMENT");
    }
}
