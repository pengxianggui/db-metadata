package com.github.md.web.user.role;

import com.github.md.analysis.SpringAnalysisManager;
import com.google.common.collect.Lists;
import com.jfinal.plugin.activerecord.DbPro;
import com.jfinal.plugin.activerecord.Record;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * TODO
 * 1.dbmeta提供内置的角色表、用户表，用户-角色关联表、角色-权限关联关系表
 * 2.dbmeta提供用户-角色绑定, 角色-权限绑定界面
 * 3.dbmeta提供角色判定器，并注册到MRManager——注意一个问题，角色判定器和权限判定器的优先级，以及相同类型判定器的优先级
 *
 * @author pengxg
 * @date 2022/2/15 5:04 下午
 */
@Service
public class MetaRoleService {
    private DbPro db() {
        return SpringAnalysisManager.me().dbMain();
    }

    public List<MRRole> findAll() {
        List<MRRole> roles = Lists.newArrayList();
        for (Record record : db().findAll("meta_role")) {
            roles.add(new DefaultRole(record));
        }
        return roles;
    }

    public List<MRRole> findByUser(String userId) {
        List<MRRole> roles = Lists.newArrayList();
        List<Record> records = db().find("select r.* from meta_role r,  meta_user_role_rela rela where r.id = rela.role_id and rela.user_id=?", userId);
        for (Record record : records) {
            roles.add(new DefaultRole(record));
        }
        return roles;
    }
}
