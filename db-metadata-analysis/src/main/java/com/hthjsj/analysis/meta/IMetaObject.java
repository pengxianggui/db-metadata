package com.hthjsj.analysis.meta;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.Collection;
import java.util.Map;

/**
 * <p> Class title: </p>
 * <p> @Describe: </p>
 * <p> @Date : 2019-08-20 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
public interface IMetaObject {

    @JSONField(name = "code")
    String code();

    @JSONField(name = "code")
    void code(String value);

    @JSONField(name = "name")
    String name();

    @JSONField(name = "name")
    void name(String value);

    @JSONField(name = "tableName")
    String tableName();

    @JSONField(name = "tableName")
    void tableName(String value);

    @JSONField(name = "schemaName")
    String schemaName();

    @JSONField(name = "schemaName")
    void schemaName(String value);

    @JSONField(name = "primarys")
    IMetaField[] primarys();

    @JSONField(name = "primarys")
    void primarys(IMetaField[] value);

    @JSONField(name = "primaryKey")
    String primaryKey();

    @JSONField(name = "fields")
    Collection<IMetaField> fields();

    @JSONField(name = "fields")
    void fields(Collection<IMetaField> value);

    void addField(IMetaField value);

    @JSONField(name = "config")
    String config();

    boolean isSystem();

    void config(String config);

    MetaObjectConfigParse configParser();

    Map<String, Object> dataMap();

    void dataMap(Map<String, Object> data);
}

