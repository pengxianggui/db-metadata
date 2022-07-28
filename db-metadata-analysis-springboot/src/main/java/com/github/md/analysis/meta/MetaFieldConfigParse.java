package com.github.md.analysis.meta;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.github.md.analysis.kit.Kv;
import com.jfinal.kit.StrKit;

import java.util.ArrayList;
import java.util.List;

/**
 * 元字段配置解析器
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
     * 是否有数据源配置
     *
     * @return
     */
    public boolean hasTranslation() {

        /**
         * 1. isFromDB
         * 2. hasDataOpts
         * 3. 指定数据源
         */
        return isSql() || isOptions() || isDict() || isUrl();
    }

    /**
     * 是否需要转义。显示时，是否需要转义。TODO 应当细分下，支持表格显示时无需转义，详情时需要转义。这个涉及元字段配置的UI，乃至数据结构的调整
     *
     * @return 若元字段未配置，则默认为true(向下兼容)
     */
    public boolean escape() {
        String escape = getStr("escape");
        if (StrKit.isBlank(escape)) {
            return true;
        }
        return Boolean.parseBoolean(escape);
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

    public boolean isDict() {
        return !StrKit.isBlank(getDictName());
    }

    public String getDictName() {
        return getStr("scopeDict");
    }


    public boolean isSql() {
        return StrKit.notBlank(scopeSql());
    }

    public boolean isUrl() {
        return StrKit.notBlank(scopeUrl());
    }

    public String scopeSql() {
        String s = getStr("scopeSql");
        if (StrKit.notBlank(s)) {
            return s.split(";")[0];
        }
        return null;
    }

    public String scopeUrl() {
        return getStr("scopeUrl");
    }

    public String dbConfig() {
        String scopeSql = getStr("scopeSql");
        if (scopeSql.contains(";")) {
            String[] sqlArr = scopeSql.split(";");
            if (sqlArr.length == 2) {
                return sqlArr[1];
            }
        }
        return null;
    }

    public boolean isRequired() {
        return !Boolean.parseBoolean(getStr("isNullable"));
    }

    public String defaultVal() {
        return getStr("defaultVal");
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

    public void isListShow(boolean value) {
        set("isListShow", value);
    }

    public boolean isFile() {
        return Boolean.parseBoolean(getStr("isFile"));
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
