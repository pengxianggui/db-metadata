package com.github.md.web;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * <p> @Date : 2019/11/20 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
public class AppConst {
    public static final String version = "v2.2.2-beta"; // pom若为snapshot, 则为dbmeta为beta测试版本。

//    /**
//     * row代表表名, column代表属性, value代表值
//     *
//     * @deprecated 使用 {@link INNER_TABLE}替代
//     */
//    @Deprecated
//    public static final HashBasedTable<String, String, Boolean> SYS_TABLE = HashBasedTable.create();

//    /**
//     * 是否可清理。注意, 只会清理内建数据(build_in=true)
//     *
//     * @deprecated
//     */
//    @Deprecated
//    public static final String CLEARABLE = "clearable";

//    static {
//        SYS_TABLE.put("meta_object", CLEARABLE, true);
//        SYS_TABLE.put("meta_field", CLEARABLE, true);
//        SYS_TABLE.put("meta_component", CLEARABLE, true);
//        SYS_TABLE.put("meta_component_instance", CLEARABLE, true);
//        SYS_TABLE.put("meta_router", CLEARABLE, true);
//        SYS_TABLE.put("meta_menu", CLEARABLE, true);
//        SYS_TABLE.put("meta_profile_menu", CLEARABLE, true);
//        SYS_TABLE.put("meta_feature", CLEARABLE, true);
//        SYS_TABLE.put("meta_config", CLEARABLE, false);
//        SYS_TABLE.put("meta_change_log", CLEARABLE, false);
//        SYS_TABLE.put("meta_exception", CLEARABLE, false);
//        SYS_TABLE.put("meta_dict", CLEARABLE, true);
//        SYS_TABLE.put("meta_api_resource", CLEARABLE, true);
//        SYS_TABLE.put("meta_auth", CLEARABLE, true);
//        SYS_TABLE.put("meta_auth_module", CLEARABLE, true);
//        SYS_TABLE.put("meta_role", CLEARABLE, true);
//        SYS_TABLE.put("meta_role_auth_rela", CLEARABLE, true);
//        SYS_TABLE.put("meta_user", CLEARABLE, true);
//        SYS_TABLE.put("meta_user_role_rela", CLEARABLE, true);
//    }

    @Getter
    @AllArgsConstructor
    public enum INNER_TABLE {
        META_OBJECT("meta_object", true, false, true),
        META_FIELD("meta_field", true, false, true),
        META_FEATURE("meta_feature", true, false, true),
        META_COMPONENT("meta_component", true, false, true),
        META_COMPONENT_INSTANCE("meta_component_instance", true, false, true),
        META_CONFIG("meta_config", false, false, true),
        META_CHANGE_LOG("meta_change_log", false, false, true),
        META_EXCEPTION("meta_exception", false, false, false),
        META_ROUTER("meta_router", true, true, true),
        META_MENU("meta_menu", true, true, true),
        META_PROFILE_MENU("meta_profile_menu", true, true, true),
        META_DICT("meta_dict", true, true, true),
        META_API_RESOURCE("meta_api_resource", true, true, true),
        META_AUTH("meta_auth", true, true, true),
        META_AUTH_MODULE("meta_auth_module", true, true, true),
        META_ROLE("meta_role", true, true, false),
        META_ROLE_AUTH_RELA("meta_role_auth_rela", true, true, false),
        META_USER("meta_user", true, true, false),
        META_USER_ROLE_RELA("meta_user_role_rela", true, true, false),
        ;

        /**
         * 表名
         */
        private String tableName;
        /**
         * 是否可重置
         */
        private boolean resetable;
        /**
         * 内建数据是否存在于sql中维护
         */
        private boolean buildInSql;

        /**
         * 默认可导出
         */
        private boolean exportable;

        /**
         * 获取默认可导出的表名
         *
         * @return
         */
        public static Set<INNER_TABLE> getDefaultExportTable() {
            return Arrays.stream(INNER_TABLE.values()).filter(INNER_TABLE::isExportable).collect(Collectors.toSet());
        }

        /**
         * 获取可重置的表
         *
         * @return
         */
        public static Set<INNER_TABLE> getResetableTable() {
            return Arrays.stream(INNER_TABLE.values()).filter(INNER_TABLE::isResetable).collect(Collectors.toSet());
        }
    }
}
