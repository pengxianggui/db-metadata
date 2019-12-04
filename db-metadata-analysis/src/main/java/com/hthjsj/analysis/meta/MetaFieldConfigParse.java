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
public class MetaFieldConfigParse extends MetaData {

    public static final int NORMAL = 100;

    public static final int HIDDEN = 50;

    public static final int READONLY = 30;

    public static final int DISABLE = 10;

    MetaFieldConfigParse(Kv config) {
        set(config);
    }

    MetaFieldConfigParse(String config) {
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

    public boolean isSearch() {
        return Boolean.parseBoolean(getStr("isSearch"));
    }

    public boolean isListShow() {
        return Boolean.parseBoolean(getStr("isListShow"));
    }

    public boolean isFile() {
        return Boolean.parseBoolean(getStr("isFile")) && StrKit.notBlank(getStr("fileUploadPath"));
    }

    /**
     * addStatus/updateStatus 控制展示行为
     * 正常(能看,能生成sql)
     * 隐藏(不能看,能生成sql)
     * 只读(能看,不生成sql)
     * 禁用(不能看,不生成SQL)
     * <p>
     * 100/50/30/10
     *
     * @return
     */
    public int addStatus() {
        return Integer.valueOf(getStr("addStatus"));
    }

    public int updateStatus() {
        return Integer.valueOf(getStr("updateStatus"));
    }

    public int viewStatus() {
        return Integer.valueOf(getStr("viewStatus"));
    }

    public boolean isAdd() {
        return addStatus() > DISABLE;
    }

    public boolean isUpdate() {
        return updateStatus() > DISABLE;
    }

    public boolean isView() {
        return viewStatus() > DISABLE;
    }
}
