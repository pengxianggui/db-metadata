package com.hthjsj.web.auth;

/**
 * <p> @Date : 2020/8/12 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
public interface Permission {

    default String code() {
        return "Empty permission code.";
    }

    default String name() {
        return "Empty permission name.";
    }
}