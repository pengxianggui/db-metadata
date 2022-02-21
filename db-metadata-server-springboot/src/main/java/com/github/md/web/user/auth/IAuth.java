package com.github.md.web.user.auth;

import com.github.md.analysis.kit.Kv;

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

    Kv toKv();
}
