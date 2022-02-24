package com.github.md.web.user.auth.defaults;

import com.github.md.web.user.auth.MResource;
import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Record;

import java.util.List;

/**
 * 基于表数据的api接口资源。
 * <p>
 * 表: meta_api_resource
 *
 * @author pengxg
 * @date 2022/2/23 5:29 下午
 */
public class MetaApiResource implements MResource {
    private Record data;

    public MetaApiResource(Record data) {
        this.data = data;
    }

    @Override
    public String mResourceId() {
        return this.data.getStr("id");
    }

    @Override
    public String mResourceName() {
        return this.data.getStr("name");
    }

    @Override
    public boolean needPermit() {
        return this.data.getBoolean("need_permit");
    }

    public PermitBy permitBy() {
        return PermitBy.valueOf(this.data.getStr("permit_by"));
    }

    public boolean justSign() {
        return this.data.getBoolean("just_sign");
    }

    public List<String> needAuth() {
        String authCodeStr = this.data.getStr("need_auths");
        if (StrKit.isBlank(authCodeStr)) {
            return Lists.newArrayList();
        }

        return Splitter.on(",").splitToList(authCodeStr);
    }

    public MatchMode authMatchMode() {
        return MatchMode.valueOf(this.data.getStr("auth_match_mode"));
    }

    public MatchMode roleMatchMode() {
        return MatchMode.valueOf(this.data.getStr("role_match_mode"));
    }

    public List<String> needRole() {
        String roleCodeStr = this.data.getStr("need_roles");
        if (StrKit.isBlank(roleCodeStr)) {
            return Lists.newArrayList();
        }
        return Splitter.on(",").splitToList(roleCodeStr);
    }


    public enum PermitBy {
        auth, // 依据权限
        role // 依据角色
    }

    public enum MatchMode {
        any, // 任一匹配即可
        all // 全部匹配
    }
}
