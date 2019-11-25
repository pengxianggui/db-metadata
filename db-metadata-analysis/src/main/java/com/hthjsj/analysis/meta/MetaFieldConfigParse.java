package com.hthjsj.analysis.meta;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.jfinal.kit.Kv;
import com.jfinal.kit.StrKit;

import java.util.ArrayList;
import java.util.List;

/**
 * <p> @Date : 2019/11/13 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
public class MetaFieldConfigParse extends MetaConfigFactory.MetaFieldConfig {

    public MetaFieldConfigParse(String config) {
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
        return isRange() || isSql() || isOptions();
    }

    public boolean isRange() {
        return range().length > 0;
    }

    public String[] range() {
        JSONArray jsonArray = getAs("scopeRange");
        if (jsonArray == null) {
            jsonArray = new JSONArray();
        }
        return jsonArray.toArray(new String[jsonArray.size()]);
    }

    public boolean isOptions() {
        return options().size() > 0;
    }

    public List<Kv> options() {
        String jsonArray = getStr("scopeOptions");
        if (jsonArray == null) {
            return new ArrayList<>();
        }
        return JSONArray.parseArray(jsonArray, Kv.class);
    }

    public boolean isSql() {
        return StrKit.notBlank(scopeSql());
    }

    public String scopeSql() {
        return getStr("scopeSql");
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

    public boolean isQuery() {
        return false;
    }

    public int addStatus() {
        return 0;
    }

    public int updateStatus() {
        return 0;
    }

    public int viewStatus() {
        return 0;
    }
}
