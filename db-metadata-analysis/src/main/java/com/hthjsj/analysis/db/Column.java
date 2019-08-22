package com.hthjsj.analysis.db;

import com.jfinal.plugin.activerecord.Record;

/**
 * <p> Class title: </p>
 * <p> @Describe: </p>
 * <p> @Date : 2019-08-20 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
public class Column {
    
    Record record;
    
    public Column(Record record) {
        this.record = record;
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
    
    public String getColumnName() {
        return record.getStr("COLUMN_NAME");
    }
    
    public Long getOrdinalPosition() {
        return record.getLong("ORDINAL_POSITION");
    }
    
    public String getColumnDefault() {
        return record.getStr("COLUMN_DEFAULT");
    }
    
    public String getIsNullable() {
        return record.getStr("IS_NULLABLE");
    }
    
    public String getDataType() {
        return record.getStr("DATA_TYPE");
    }
    
    public Long getCharacterMaximumLength() {
        return record.getLong("CHARACTER_MAXIMUM_LENGTH");
    }
    
    public Long getCharacterOctetLength() {
        return record.getLong("CHARACTER_OCTET_LENGTH");
    }
    
    public Long getNumericPrecision() {
        return record.getLong("NUMERIC_PRECISION");
    }
    
    public Long getNumericScale() {
        return record.getLong("NUMERIC_SCALE");
    }
    
    public Long getDatetimePrecision() {
        return record.getLong("DATETIME_PRECISION");
    }
    
    public String getCharacterSetName() {
        return record.getStr("CHARACTER_SET_NAME");
    }
    
    public String getCollationName() {
        return record.getStr("COLLATION_NAME");
    }
    
    public String getColumnType() {
        return record.getStr("COLUMN_TYPE");
    }
    
    public String getColumnKey() {
        return record.getStr("COLUMN_KEY");
    }
    
    public String getExtra() {
        return record.getStr("EXTRA");
    }
    
    public String getPrivileges() {
        return record.getStr("PRIVILEGES");
    }
    
    public String getColumnComment() {
        return record.getStr("COLUMN_COMMENT");
    }
    
    public String getGenerationExpression() {
        return record.getStr("GENERATION_EXPRESSION");
    }
}
