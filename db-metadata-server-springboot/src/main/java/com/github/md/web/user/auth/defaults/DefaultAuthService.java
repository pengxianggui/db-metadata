package com.github.md.web.user.auth.defaults;

import com.github.md.analysis.SpringAnalysisManager;
import com.github.md.web.user.auth.AuthService;
import com.github.md.web.user.auth.IAuth;
import com.google.common.collect.Lists;
import com.jfinal.plugin.activerecord.DbPro;
import com.jfinal.plugin.activerecord.Record;

import java.util.List;

/**
 * @author pengxg
 * @date 2022/2/21 1:12 下午
 */
public class DefaultAuthService implements AuthService {

    protected DbPro db() {
        return SpringAnalysisManager.me().dbMain();
    }

    @Override
    public List<IAuth> findAll() {
        List<IAuth> auths = Lists.newArrayList();
        for (Record record : db().findAll("meta_auth")) {
            auths.add(new DefaultAuth(record));
        }
        return auths;
    }

    @Override
    public List<IAuth> findByRole(String roleId) {
        List<IAuth> auths = Lists.newArrayList();
        List<Record> records = db().find("select a.* from meta_auth a, meta_role_auth_rela r where a.id = r.auth_id and r.role_id=?", roleId);
        for (Record record : records) {
            auths.add(new DefaultAuth(record));
        }
        return auths;
    }

    @Override
    public List<IAuth> findByUser(String userId) {
        List<IAuth> auths = Lists.newArrayList();
        List<Record> records = db().find("select DISTINCT a.* from meta_auth a, meta_role_auth_rela ra, meta_user_role_rela ur, meta_user u where a.id = ra.auth_id and ra.role_id = ur.role_id and ur.user_id = u.id and u.id=?", userId);
        for (Record record : records) {
            auths.add(new DefaultAuth(record));
        }
        return auths;
    }

    @Override
    public IAuth findOne(String id) {
        Record record = db().findById("meta_auth", id);
        if (record == null) {
            return null;
        }
        return new DefaultAuth(record);
    }
}
