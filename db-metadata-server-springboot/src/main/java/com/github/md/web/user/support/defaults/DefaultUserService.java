package com.github.md.web.user.support.defaults;

import com.github.md.analysis.SpringAnalysisManager;
import com.github.md.web.kit.PassKit;
import com.github.md.web.user.AbstractUserService;
import com.google.common.collect.Lists;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.DbPro;
import com.jfinal.plugin.activerecord.Record;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author pengxg
 * @date 2022/2/18 10:40 上午
 */
public class DefaultUserService extends AbstractUserService<DefaultUser> {

    private DbPro db() {
        return SpringAnalysisManager.me().dbMain();
    }

    @Override
    public List<DefaultUser> findAll() {
        List<DefaultUser> users = Lists.newArrayList();
        db().findAll("meta_user").forEach(r -> {
            users.add(new DefaultUser(r));
        });
        return users;
    }

    @Override
    public DefaultUser findById(Object idValue) {
        Record record = db().findFirst("select * from meta_user where id=?", idValue);
        if (record == null) {
            return null;
        }
        return new DefaultUser(record);
    }

    @Override
    public boolean updateById(DefaultUser user) {
        return db().update("meta_user", user.getData());
    }

    @Override
    public boolean bindRolesForUser(String userId, String... roleIds) {
        return Db.tx(() -> {
            db().delete("delete from meta_user_role_rela where user_id=?", userId);

            List<Record> inserts = Arrays.stream(roleIds).filter(roleId -> StrKit.notBlank(roleId))
                    .map(roleId -> {
                        Record rela = new Record();
                        rela.set("user_id", userId);
                        rela.set("role_id", roleId);
                        return rela;
                    }).collect(Collectors.toList());
            return db().batchSave("meta_user_role_rela", inserts, 20).length == inserts.size();
        });
    }

    @Override
    public DefaultUser login(String username, String password) {
        Record record = db().findFirst("select * from meta_user where username=? and password=?",
                username, PassKit.encryptPass(password));

        if (record == null) {
            return null;
        }
        return new DefaultUser(record);
    }
}
