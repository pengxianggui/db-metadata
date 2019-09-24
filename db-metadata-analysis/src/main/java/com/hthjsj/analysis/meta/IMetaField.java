package com.hthjsj.analysis.meta;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.Map;

/**
 * <p> Class title: </p>
 * <p> @Describe: </p>
 * <p> @Date : 2019-08-20 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
public interface IMetaField {

    @JSONField(name = "isPrimary")
    boolean isPrimary();

    @JSONField(name = "isPrimary")
    void isPrimary(boolean value);

    @JSONField(name = "fieldCode")
    String fieldCode();

    @JSONField(name = "fieldCode")
    void fieldCode(String value);

    @JSONField(name = "objectCode")
    String objectCode();

    @JSONField(name = "objectCode")
    void objectCode(String value);

    @JSONField(name = "cn")
    String cn();

    @JSONField(name = "cn")
    void cn(String value);

    @JSONField(name = "en")
    String en();

    @JSONField(name = "en")
    void en(String value);

    @JSONField(name = "dbType")
    String dbType();

    @JSONField(name = "dbType")
    void dbType(String value);

    @JSONField(name = "javaType")
    String javaType();

    @JSONField(name = "javaType")
    void javaType(String value);

    @JSONField(name = "orderNum")
    int orderNum();

    @JSONField(name = "orderNum")
    void orderNum(int value);

    @JSONField(name = "dbTypeLength")
    Long dbTypeLength();

    @JSONField(name = "dbTypeLength")
    void dbTypeLength(Long value);

    @JSONField(name = "config")
    IMetaConfig config();

    Map<String, Object> dataMap();

    void dataMap(Map<String, Object> data);

    void config(String config);

    void config(IMetaConfig config);

}

