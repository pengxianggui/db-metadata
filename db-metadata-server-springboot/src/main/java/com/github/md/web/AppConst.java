package com.github.md.web;

import com.google.common.collect.HashBasedTable;

/**
 * <p> @Date : 2019/11/20 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
public class AppConst {
    /**
     * row代表表名, column代表属性, value代表值
     */
    public static final HashBasedTable<String, String, Boolean> SYS_TABLE = HashBasedTable.create();

    /**
     * 是否可清理
     */
    public static final String CLEARABLE = "clearable";

    static {
        SYS_TABLE.put("meta_object", CLEARABLE, true);
        SYS_TABLE.put("meta_field", CLEARABLE, true);
        SYS_TABLE.put("meta_component", CLEARABLE, true);
        SYS_TABLE.put("meta_component_instance", CLEARABLE, true);
        SYS_TABLE.put("meta_router", CLEARABLE, false);
        SYS_TABLE.put("meta_menu", CLEARABLE, false);
        SYS_TABLE.put("meta_feature", CLEARABLE, false);
        SYS_TABLE.put("meta_config", CLEARABLE, false);
        SYS_TABLE.put("meta_change_log", CLEARABLE, false);
        SYS_TABLE.put("meta_exception", CLEARABLE, false);
        SYS_TABLE.put("meta_dict", CLEARABLE, false);
        SYS_TABLE.put("meta_api_resource", CLEARABLE, false);
        SYS_TABLE.put("meta_auth", CLEARABLE, false);
        SYS_TABLE.put("meta_role", CLEARABLE, false);
        SYS_TABLE.put("meta_user", CLEARABLE, false);
    }
}
