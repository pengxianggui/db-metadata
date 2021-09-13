package com.github.md.web.user.auth;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * <p> @Date : 2020/8/12 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
@Getter
@AllArgsConstructor
public class StaticPermission implements Permission {

    private final String code;

    private final String name;

    public static Permission of(String code, String name) {
        return new StaticPermission(code, name);
    }
}
