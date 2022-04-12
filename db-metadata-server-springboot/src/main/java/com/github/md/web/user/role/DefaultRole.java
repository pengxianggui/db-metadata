package com.github.md.web.user.role;

import com.alibaba.fastjson.JSONObject;
import com.github.md.analysis.kit.Kv;
import com.github.md.web.user.auth.IAuth;
import com.google.common.collect.Lists;
import com.jfinal.plugin.activerecord.Record;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * <p> @Date : 2020/8/12 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
@Setter
@Getter
@NoArgsConstructor
public class DefaultRole implements MRRole {
    private String id;
    private String code;
    private String name;
    private List<IAuth> auths = new ArrayList<>();

    public DefaultRole(Record record, IAuth... auths) {
        this.id = record.getStr("id");
        this.code = record.getStr("code");
        this.name = record.getStr("name");
        for (IAuth auth : auths) {
            this.auths.add(auth);
        }
    }

    @Override
    public String id() {
        return this.id;
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
        return Kv.create().set(JSONObject.parseObject(JSONObject.toJSONString(this)));
    }

    public void setAuths(List<IAuth> auths) {
        this.auths.clear();
        this.auths.addAll(auths);
    }
}
