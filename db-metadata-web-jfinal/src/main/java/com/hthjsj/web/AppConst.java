package com.hthjsj.web;

import com.google.common.collect.HashBasedTable;

/**
 * <p> @Date : 2019/11/20 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
public class AppConst {

    public static final String VERSION = "1.0";

    public static final HashBasedTable<String, String, Boolean> SYS_TABLE = HashBasedTable.create();

    public static final String CONFIG_ALLOW_REPLACE = "config.allow.replace";

    public static final String NEED_LOGIN = "user.login";

    public static final String NEED_AUTH = "user.auth";

    static {
        SYS_TABLE.put("meta_object", "config", true);
        SYS_TABLE.put("meta_field", "config", true);
        SYS_TABLE.put("meta_component", "config", true);
        SYS_TABLE.put("meta_component_instance", "config", true);
    }
}
