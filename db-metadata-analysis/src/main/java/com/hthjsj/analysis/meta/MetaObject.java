package com.hthjsj.analysis.meta;

import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Record;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * <p> Class title: </p>
 * <p> @Describe: </p>
 * <p> @Date : 2019/9/11 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
public class MetaObject implements IMetaObject {

    List<IMetaField> fields = new ArrayList<>();

    MetaObjectConfigParse metaObjectConfigParse;

    Record record = new Record();

    MetaObject() {
    }

    MetaObject(Map<String, Object> dataMap) {
        this.record = new Record().setColumns(dataMap);
    }

    @Override
    public String code() {
        return record.getStr("code");
    }

    @Override
    public void code(String value) {
        record.set("code", value);
    }

    @Override
    public String name() {
        return record.getStr("name");
    }

    @Override
    public void name(String value) {
        record.set("name", value);
    }

    @Override
    public String tableName() {
        return record.getStr("table_name");
    }

    @Override
    public void tableName(String value) {
        record.set("table_name", value);
    }

    @Override
    public String schemaName() {
        return record.getStr("schema_name");
    }

    @Override
    public void schemaName(String value) {
        record.set("schema_name", value);
    }

    @Override
    public String primaryKey() {
        return record.getStr("primarys");
    }

    @Override
    public boolean isMultiplePrimaryKey() {
        return record.getStr("primarys") != null && record.getStr("primarys").contains(",");
    }

    @Override
    public void primaryKey(String primaryKey) {
        record.set("primarys", primaryKey);
    }

    @Override
    public Collection<IMetaField> fields() {
        return fields;
    }

    @Override
    public void fields(Collection<IMetaField> fields) {
        this.fields = new ArrayList<>(fields);
    }

    @Override
    public void addField(IMetaField value) {
        fields.add(value);
    }

    @Override
    public String config() {
        return record.getStr("config");
    }

    @Override
    public boolean isSystem() {
        return "1".equals(record.getStr("is_sys"));
    }

    @Override
    public void config(String config) {
        record.set("config", config);
    }

    @Override
    public MetaObjectConfigParse configParser() {
        if (metaObjectConfigParse == null) {
            metaObjectConfigParse = new MetaObjectConfigParse(config());
        }
        return metaObjectConfigParse;
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
    public MetaJudge metaType() {
        return new MetaJudge(MetaJudge.TABLE);
    }

    @Override
    public IMetaField getField(String fieldCode) {
        return fields.stream().filter(f -> fieldCode.equalsIgnoreCase(f.fieldCode())).findFirst().get();
    }
}
