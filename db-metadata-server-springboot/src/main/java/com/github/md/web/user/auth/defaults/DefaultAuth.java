package com.github.md.web.user.auth.defaults;

import com.github.md.analysis.kit.Kv;
import com.github.md.web.user.auth.IAuth;
import com.github.md.web.user.auth.annotations.Type;
import com.google.common.base.Objects;
import com.jfinal.plugin.activerecord.Record;

/**
 * 默认的权限，即为meta_auth表。既为资源，也为权限。
 * <p>
 * 兼容元编码的接口资源(meta_auth表)。<br>
 * 一般，Rest接口的鉴权通常由在接口上打上注解标记，标识接口关联的权限编码。从而在接口访问前，判定用户是否具有此权限。
 * <p>
 * 而当前资源标记，不是硬编码在接口上的，而是存储在数据表meta_auth中。而且由于dbmeta一些内置接口的特殊性(不同元对象共享一个接口), 因此扩展出
 * {@link Type}的概念。
 * <p>
 *
 * @author pengxg
 * @date 2021/10/15 9:51 上午
 */
public class DefaultAuth implements IAuth {
    private Record data;

    public DefaultAuth(Record record) {
        this.data = record;
    }

    @Override
    public String code() {
        return data.getStr("code");
    }

    @Override
    public String name() {
        return data.getStr("name");
    }

    @Override
    public Kv toKv() {
        return Kv.create().set(this.data.getColumns());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DefaultAuth that = (DefaultAuth) o;
        return Objects.equal(code(), that.code());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(code());
    }
}
