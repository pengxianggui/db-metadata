package com.github.md.web.user.auth;

import com.github.md.analysis.kit.Kv;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * <p> @Date : 2020/8/12 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class StaticAuth implements IAuth {

    private final String code;

    private final String name;

    public static IAuth of(String code, String name) {
        return new StaticAuth(code, name);
    }

    @Override
    public Kv toKv() {
        return Kv.create().set("code", code).set("name", name);
    }
}
