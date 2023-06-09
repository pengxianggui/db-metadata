package com.github.md.web.user.auth;

import com.github.md.analysis.kit.Kv;
import com.google.common.base.Objects;
import lombok.*;

/**
 * <p> @Date : 2020/8/12 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class StaticAuth implements IAuth {

    private String code;

    private String name;

    public static IAuth of(String code, String name) {
        return new StaticAuth(code, name);
    }

    @Override
    public String code() {
        return code;
    }

    @Override
    public String name() {
        return name;
    }

    @Override
    public Kv toKv() {
        return Kv.create().set("code", code).set("name", name);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StaticAuth that = (StaticAuth) o;
        return Objects.equal(code(), that.code());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(code);
    }
}
