package com.hthjsj.analysis.meta;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.Map;

/**
 * <p> Class title: </p>
 * <p> @Describe: </p>
 * <p> @Date : 2019-08-23 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
public interface IMetaConfig {

    String MODULE_OBJECT = "meta_object";
    String MODULE_FIELD = "meta_field";

    String module();

    String moduleCode();

    String getVersion();

    @JSONField(serialize = false)
    Map getConfig();

    @JSONField(serialize = false)
    void setItem(Object key, Object value);

    @JSONField(serialize = false)
    <T> T getItem(Object key);
}
