package com.hthjsj.analysis.meta;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.jfinal.kit.Kv;
import com.jfinal.kit.StrKit;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

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
        String s = getStr("scopeSql");
        if (StrKit.notBlank(s)) {
            return s.split(";")[0];
        }
        return null;
    }

    public String dbConfig() {
        if (getStr("scopeSql").contains(";")) {
            return getStr("scopeSql").split(";")[1];
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

    public FileConfig fileConfig() {
        FileConfig fileConfig = JSON.parseObject(getStr("fileConfig"), FileConfig.class);
        return fileConfig;
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

    /**
     * 每个插槽的作用
     * 一个插槽可以上传一个或多个文件 由single来
     */
    @Data
    @AllArgsConstructor
    public static class SlotItem {

        private String name;

        private boolean single;

        public static SlotItem of(String name, boolean single) {
            return new SlotItem(name, single);
        }
    }

    /**
     * 文件配置
     * <pre>
     *     默认有一个default插槽
     * </pre>
     */
    public static class FileConfig {

        //SingleGroup,MultiGroup

        /**
         * Group1            Group2
         * +------------+    +------------+
         * |   File|1   |    |   File|1   |
         * |   File|2   |    |   File|2   |
         * |   File|1   |    |   File|1   |
         * |   File-4   |    |   File-4   |
         * +------------+    +------------+
         */
        @Getter
        @Setter
        private List<SlotItem> slots = new ArrayList<>();

        public FileConfig() {
            slots.add(SlotItem.of("default", true));
        }

        private SlotItem getByName(String name) {
            return slots.parallelStream().filter(s -> name.equalsIgnoreCase(s.name)).findFirst().get();
        }

        public SlotItem getDefault() {
            return getByName("default");
        }

        public boolean isDefault() {
            return getByName("default") != null || slots.isEmpty();
        }
    }
}
