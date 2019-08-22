package com.hthjsj.analysis.db.entity;

import java.util.Date;

/**
 * (Tables)表实体类
 *
 * @author makejava
 * @since 2019-08-20 10:45:54
 */
public class Tables {
    
    private String tableCatalog;
    
    private String tableSchema;
    
    private String tableName;
    
    private String tableType;
    
    private String engine;
    
    private Object version;
    
    private String rowFormat;
    
    private Object tableRows;
    
    private Object avgRowLength;
    
    private Object dataLength;
    
    private Object maxDataLength;
    
    private Object indexLength;
    
    private Object dataFree;
    
    private Object autoIncrement;
    
    private Date createTime;
    
    private Date updateTime;
    
    private Date checkTime;
    
    private String tableCollation;
    
    private Object checksum;
    
    private String createOptions;
    
    private String tableComment;
    
    public String getTableCatalog() {
        return tableCatalog;
    }
    
    public void setTableCatalog(String tableCatalog) {
        this.tableCatalog = tableCatalog;
    }
    
    public String getTableSchema() {
        return tableSchema;
    }
    
    public void setTableSchema(String tableSchema) {
        this.tableSchema = tableSchema;
    }
    
    public String getTableName() {
        return tableName;
    }
    
    public void setTableName(String tableName) {
        this.tableName = tableName;
    }
    
    public String getTableType() {
        return tableType;
    }
    
    public void setTableType(String tableType) {
        this.tableType = tableType;
    }
    
    public String getEngine() {
        return engine;
    }
    
    public void setEngine(String engine) {
        this.engine = engine;
    }
    
    public Object getVersion() {
        return version;
    }
    
    public void setVersion(Object version) {
        this.version = version;
    }
    
    public String getRowFormat() {
        return rowFormat;
    }
    
    public void setRowFormat(String rowFormat) {
        this.rowFormat = rowFormat;
    }
    
    public Object getTableRows() {
        return tableRows;
    }
    
    public void setTableRows(Object tableRows) {
        this.tableRows = tableRows;
    }
    
    public Object getAvgRowLength() {
        return avgRowLength;
    }
    
    public void setAvgRowLength(Object avgRowLength) {
        this.avgRowLength = avgRowLength;
    }
    
    public Object getDataLength() {
        return dataLength;
    }
    
    public void setDataLength(Object dataLength) {
        this.dataLength = dataLength;
    }
    
    public Object getMaxDataLength() {
        return maxDataLength;
    }
    
    public void setMaxDataLength(Object maxDataLength) {
        this.maxDataLength = maxDataLength;
    }
    
    public Object getIndexLength() {
        return indexLength;
    }
    
    public void setIndexLength(Object indexLength) {
        this.indexLength = indexLength;
    }
    
    public Object getDataFree() {
        return dataFree;
    }
    
    public void setDataFree(Object dataFree) {
        this.dataFree = dataFree;
    }
    
    public Object getAutoIncrement() {
        return autoIncrement;
    }
    
    public void setAutoIncrement(Object autoIncrement) {
        this.autoIncrement = autoIncrement;
    }
    
    public Date getCreateTime() {
        return createTime;
    }
    
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
    
    public Date getUpdateTime() {
        return updateTime;
    }
    
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
    
    public Date getCheckTime() {
        return checkTime;
    }
    
    public void setCheckTime(Date checkTime) {
        this.checkTime = checkTime;
    }
    
    public String getTableCollation() {
        return tableCollation;
    }
    
    public void setTableCollation(String tableCollation) {
        this.tableCollation = tableCollation;
    }
    
    public Object getChecksum() {
        return checksum;
    }
    
    public void setChecksum(Object checksum) {
        this.checksum = checksum;
    }
    
    public String getCreateOptions() {
        return createOptions;
    }
    
    public void setCreateOptions(String createOptions) {
        this.createOptions = createOptions;
    }
    
    public String getTableComment() {
        return tableComment;
    }
    
    public void setTableComment(String tableComment) {
        this.tableComment = tableComment;
    }
    
}
