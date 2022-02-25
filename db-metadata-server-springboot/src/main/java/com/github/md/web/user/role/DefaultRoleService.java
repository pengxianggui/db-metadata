package com.github.md.web.user.role;

import com.github.md.analysis.SpringAnalysisManager;
import com.github.md.web.user.AuthenticationManager;
import com.github.md.web.user.auth.IAuth;
import com.google.common.collect.Lists;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.DbPro;
import com.jfinal.plugin.activerecord.Record;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * TODO
 * 1.dbmeta提供内置的角色表、用户表，用户-角色关联表、角色-权限关联关系表
 * 2.dbmeta提供用户-角色绑定, 角色-权限绑定界面
 * 3.dbmeta提供角色判定器，并注册到MRManager——注意一个问题，角色判定器和权限判定器的优先级，以及相同类型判定器的优先级
 *
 * @author pengxg
 * @date 2022/2/15 5:04 下午
 */
public class DefaultRoleService implements RoleService {
    private DbPro db() {
        return SpringAnalysisManager.me().dbMain();
    }

    @Override
    public List<MRRole> findAll() {
        List<MRRole> roles = Lists.newArrayList();
        for (Record record : db().findAll("meta_role")) {
            roles.add(new DefaultRole(record));
        }
        return roles;
    }

    @Override
    public List<MRRole> findByUser(String userId) {
        List<MRRole> roles = Lists.newArrayList();
        List<Record> records = db().find("select r.* from meta_role r,  meta_user_role_rela rela where r.id = rela.role_id and rela.user_id=?", userId);
        for (Record data : records) {
            DefaultRole role = new DefaultRole(data);
            List<IAuth> auths = AuthenticationManager.me().authService().findByRole(role.id());
            role.setAuths(auths);
            roles.add(role);
        }
        return roles;
    }

    @Override
    public boolean bindAuthsForRole(String roleId, String... authIds) {
        return Db.tx(() -> {
            db().delete("delete from meta_role_auth_rela where role_id=?", roleId);

            List<Record> inserts = Arrays.stream(authIds).filter(authId -> StrKit.notBlank(authId))
                    .distinct().map(authId -> {
                        Record rela = new Record();
                        rela.set("role_id", roleId);
                        rela.set("auth_id", authId);
                        return rela;
                    }).collect(Collectors.toList());
            return db().batchSave("meta_role_auth_rela", inserts, 20).length == inserts.size();
        });
    }

    @Override
    public MRRole findOne(String id) {
        Record record = db().findById("meta_role", id);
        if (record == null) {
            return null;
        }
        return new DefaultRole(record);
    }
}
