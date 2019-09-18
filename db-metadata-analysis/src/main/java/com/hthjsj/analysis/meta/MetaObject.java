package com.hthjsj.analysis.meta;

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

    List<IMetaField> _primarys = new ArrayList<>();
    List<IMetaField> fields = new ArrayList<>();
    Record record = new Record();
    MetaConfig metaConfig;

    public MetaObject() {
    }

    public MetaObject(Map<String, Object> dataMap) {
        this.record = new Record().setColumns(dataMap);
    }

    public MetaObject(String code, String name, String tableName, String schemaName) {
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
    public IMetaField[] primarys() {
        if (_primarys.isEmpty()) {
            for (IMetaField field : fields) {
                if (field.isPrimary()) {
                    _primarys.add(field);
                }
            }
        }
        return _primarys.toArray(new IMetaField[_primarys.size()]);
    }

    @Override
    public void primarys(IMetaField[] primarys) {
        StringBuilder sb = new StringBuilder();
        for (IMetaField mf : primarys) {
            sb.append(",").append(mf.fieldCode());
        }
        record.set("primarys", sb.substring(1));
    }

    public void addPrimary(IMetaField field) {
        _primarys.add(field);
        primarys(_primarys.toArray(new IMetaField[_primarys.size()]));
    }

    @Override
    public String primaryKey() {
        return record.getStr("primarys");
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
    public MetaConfig config() {
        return new MetaConfigFactory.MetaObjectConfig(record.getStr("config"), code());
    }

    @Override
    public void config(String config) {
        record.set("config", config);
    }

    @Override
    public void config(MetaConfig config) {
        this.metaConfig = config;
        record.set("config", metaConfig.getConfig());
    }

}
