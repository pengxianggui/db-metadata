package com.github.md.web.user.auth;

import java.util.Map;

/**
 * 权限抽象
 * <p> @Date : 2020/8/12 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
public interface IAuth {

    /**
     * 权限编码
     *
     * @return
     */
    default String code() {
        return "Empty permission code.";
    }

    /**
     * 权限名
     *
     * @return
     */
    default String name() {
        return "Empty permission name.";
    }

    /**
     * 所属模块ID
     *
     * @return
     */
    default String moduleId() {
        return "Empty moduleId.";
    }

    Map toKv();
}
