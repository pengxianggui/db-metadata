package com.github.md.web;

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

    public static final String COMPONENT_ALLOW_REPLACE = "component.allow.replace";

    public static final String NEED_LOGIN = "user.login";

    public static final String NEED_AUTH = "user.auth";

    public static final String ROUTER_PREFIX = "router.prefix";

    public static final String UPLOAD_DIR = "upload.dir";

    public static final String INITABLE = "initable";

    static {
        SYS_TABLE.put("meta_object", INITABLE, true);
        SYS_TABLE.put("meta_field", INITABLE, true);
        SYS_TABLE.put("meta_component", INITABLE, true);
        SYS_TABLE.put("meta_component_instance", INITABLE, true);
        SYS_TABLE.put("meta_router", INITABLE, true);
        SYS_TABLE.put("meta_menu", INITABLE, true);
        SYS_TABLE.put("meta_feature", INITABLE, true);
        SYS_TABLE.put("meta_config", INITABLE, true);
        SYS_TABLE.put("change_log", INITABLE, false);
        SYS_TABLE.put("meta_exception", INITABLE, false);
        SYS_TABLE.put("meta_dict", INITABLE, false);
        SYS_TABLE.put("meta_auth", INITABLE, false);
    }
}
