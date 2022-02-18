package com.github.md.web.user.role;

import com.github.md.analysis.kit.Kv;
import com.github.md.web.user.auth.IAuth;
import com.google.common.collect.Lists;
import com.jfinal.plugin.activerecord.Record;

import java.util.List;

/**
 * <p> @Date : 2020/8/12 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
public class DefaultRole implements MRRole {

    private Record data;
    private List<IAuth> auths;

    public DefaultRole(Record record) {
        this.data = record;
        this.auths = Lists.newArrayList();
    }

    @Override
    public String id() {
        return null;
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
    public IAuth[] auths() {
        return this.auths.toArray(new IAuth[this.auths.size()]);
    }

    @Override
    public boolean hasAuth(IAuth auth) {
        for (IAuth p : auths) {
            if (p.code().equalsIgnoreCase(auth.code().toLowerCase()) || p.name().equalsIgnoreCase(auth.name().toLowerCase())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Kv toKv() {
        return Kv.create().set(this.data.getColumns());
    }

    public void addPermission(IAuth auth) {
        this.auths.add(auth);
    }
}
