package com.hthjsj.analysis.meta;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;

/**
 * <p> @Date : 2019/11/13 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
public class FieldConfigWrapper extends MetaConfigFactory.MetaFieldConfig {

    public FieldConfigWrapper(String config) {
        set(JSON.parseObject(config));
    }

    /**
     * 是否需要翻译
     *
     * @return
     */
    public boolean hasTranslation() {

        /**
         * 1. isFromDB
         * 2. hasDataOpts
         * 3. 指定数据源
         */
        return range().length > 0;
    }

    public String[] range() {
        JSONArray jsonArray = getAs("range");
        if (jsonArray == null) {
            jsonArray = new JSONArray();
        }
        return jsonArray.toArray(new String[jsonArray.size()]);
    }

    public boolean isRequired() {
        return !Boolean.parseBoolean(getStr("isNullable"));
    }

    public String defaultVal() {
        return getStr("defaultValue");
    }

    public boolean isMultiple() {
        return Boolean.parseBoolean(getStr("isMultiple"));
    }
}
