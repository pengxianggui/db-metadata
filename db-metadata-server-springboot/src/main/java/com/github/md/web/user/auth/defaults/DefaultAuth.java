package com.github.md.web.user.auth.defaults;

import com.alibaba.fastjson.JSONObject;
import com.github.md.analysis.kit.Kv;
import com.github.md.web.user.auth.IAuth;
import com.github.md.web.user.auth.annotations.Type;
import com.google.common.base.Objects;
import com.jfinal.plugin.activerecord.Record;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Map;

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
@Getter
@Setter
@NoArgsConstructor
public class DefaultAuth implements IAuth {
    private String id;
    private String code;
    private String name;
    private String moduleId;
    private Map<String, Object> attrs;

    public DefaultAuth(Record record) {
        this.id = record.getStr("id");
        this.code = record.getStr("code");
        this.name = record.getStr("name");
        this.moduleId = record.getStr("module_id");
        this.attrs = record.getColumns();
    }

    @Override
    public String code() {
        return this.code;
    }

    @Override
    public String name() {
        return this.name;
    }

    @Override
    public Kv toKv() {
        return Kv.create().set(this.attrs);
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
