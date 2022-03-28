package com.github.md.analysis.meta;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.Collection;
import java.util.Map;

/**
 * <pre>
 * 实体作用的接口
 * 提供实现:
 *      MetaObject 默认实现,主要用来表示Table
 *      ManualMetaObject 手动指定,可以根据sql生成临时用的元对象
 * </pre>
 *
 * <p> @see MetaFactory </p>
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

    @JSONField(name = "primaryKey")
    String primaryKey();

    boolean isMultiplePrimaryKey();

    void primaryKey(String primaryKey);

    @JSONField(name = "fields")
    Collection<IMetaField> fields();

    @JSONField(name = "fields")
    void fields(Collection<IMetaField> value);

    void addField(IMetaField value);

    @JSONField(name = "config")
    String config();

    boolean buildIn();

    void config(String config);

    MetaObjectConfigParse configParser();

    Map<String, Object> dataMap();

    void dataMap(Map<String, Object> data);

    MetaJudge metaType();

    IMetaField getField(String fieldCode);
}

