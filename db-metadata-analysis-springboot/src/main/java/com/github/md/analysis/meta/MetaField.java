package com.github.md.analysis.meta;

import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Record;

import java.util.Map;

/**
 * <p> @Date : 2019/9/11 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
class MetaField implements IMetaField {

    Record record = new Record();

    IMetaObject parent;

    DbTypeJudge dbTypeJudge;

    MetaFieldConfigParse metaFieldConfigParse;

    MetaField(Map<String, Object> fieldMap) {
        this.record = new Record().setColumns(fieldMap);
    }

    MetaField(IMetaObject object) {
        this.parent = object;
    }

    @Override
    public boolean isPrimary() {
        return ("1".equals(record.getStr("is_primary")));
    }

    @Override
    public void isPrimary(boolean value) {
        record.set("is_primary", value ? 1 : 0);
    }

    @Override
    public String objectCode() {
        return record.getStr("object_code");
    }

    @Override
    public void objectCode(String value) {
        record.set("object_code", value);
    }

    @Override
    public String cn() {
        return record.getStr("cn");
    }

    @Override
    public void cn(String value) {
        record.set("cn", value);
    }

    @Override
    public String en() {
        return record.getStr("en");
    }

    @Override
    public void en(String value) {
        record.set("en", value);
    }

    @Override
    public DbTypeJudge dbType() {
        if (dbTypeJudge == null) {
            dbTypeJudge = new DbTypeJudge(record.getStr("db_type"));
        }
        return dbTypeJudge;
    }

    @Override
    public void dbType(String value) {
        record.set("db_type", value);
    }

    @Override
    public String javaType() {
        return record.getStr("java_type");
    }

    @Override
    public void javaType(String value) {
        record.set("java_type", value);
    }

    @Override
    public String fieldCode() {
        return record.getStr("field_code");
    }

    @Override
    public void fieldCode(String value) {
        record.set("field_code", value);
    }

    @Override
    public int orderNum() {
        return record.getInt("order_num");
    }

    @Override
    public void orderNum(int value) {
        record.set("order_num", value);
    }

    @Override
    public Long dbTypeLength() {
        String val = record.getStr("db_type_length");
        return Long.valueOf(StrKit.isBlank(val) ? "0" : val);
    }

    @Override
    public void dbTypeLength(Long value) {
        record.set("db_type_length", value);
    }

    @Override
    public String config() {
        return record.getStr("config");
    }

    @Override
    public MetaFieldConfigParse configParser() {
        if (metaFieldConfigParse == null) {
            metaFieldConfigParse = new MetaFieldConfigParse(config());
        }
        return metaFieldConfigParse;
    }

    @Override
    public Map<String, Object> dataMap() {
        return record.getColumns();
    }

    @Override
    public void dataMap(Map<String, Object> data) {
        record.setColumns(data);
    }

    @Override
    public void config(String config) {
        record.set("config", config);
    }

    @Override
    public IMetaObject getParent() {
        return parent;
    }

    @Override
    public boolean buildIn() {
        return record.getBoolean("build_in");
    }
}
