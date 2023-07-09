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
    /**
     * 当前dbmeta版本。不能放到配置文件里，否则可能被用户覆盖。这个值必须真实反应当前依赖的dbmeta的版本，每次升级必须记得更新此值。
     * 后面可以考虑借助此值实现增量脚本的自动更新。
     */
    public static final String version = "2.4";

    /**
     * 未知用户的缺省用户名
     */
    public static final String UN_KNOW_USERNAME = "UnKnow";

    /**
     * dbmeta的内置表，以及相关配置
     */
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
