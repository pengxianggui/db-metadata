package com.github.md.web.user.support.defaults;

import com.github.md.analysis.SpringAnalysisManager;
import com.github.md.web.kit.PassKit;
import com.github.md.web.user.*;
import com.github.md.web.user.role.DefaultUserWithRoles;
import com.github.md.web.user.role.MRRole;
import com.google.common.collect.Lists;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.DbPro;
import com.jfinal.plugin.activerecord.Record;
import org.springframework.util.CollectionUtils;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author pengxg
 * @date 2022/2/18 10:40 上午
 */
public class DefaultUserService extends AbstractUserService<DefaultUser, DefaultUserWithRoles> {

    public DefaultUserService() {
        super(new DefaultTokenGenerator());
    }

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
    public boolean updateById(Object idValue, Map data) {
        Record record = new Record().setColumns(data);
        return updateById(new DefaultUser(record));
    }

    @Override
    public boolean updateById(DefaultUser user) {
        boolean flag = db().update("meta_user", user.getData());
        List<MRRole> roles = AuthenticationManager.me().roleService().findByUser(user.userId());
        MRRole[] roleArr = CollectionUtils.isEmpty(roles) ? new MRRole[0] : roles.toArray(new MRRole[roles.size()]);
        DefaultUserWithRoles defaultUserWithRoles = new DefaultUserWithRoles(user, roleArr);
        setLogged(defaultUserWithRoles); // 更新会话缓存
        return flag;
    }

    @Override
    public boolean bindRolesForUser(String userId, String... roleIds) {
        return Db.tx(() -> {
            db().delete("delete from meta_user_role_rela where user_id=?", userId);

            List<Record> inserts = Arrays.stream(roleIds).filter(roleId -> StrKit.notBlank(roleId))
                    .distinct().map(roleId -> {
                        Record rela = new Record();
                        rela.set("user_id", userId);
                        rela.set("role_id", roleId);
                        return rela;
                    }).collect(Collectors.toList());
            return db().batchSave("meta_user_role_rela", inserts, 20).length == inserts.size();
        });
    }

    @Override
    public DefaultUserWithRoles login(String username, String password) {
        Record record = db().findFirst("select * from meta_user where username=? and password=?",
                username, PassKit.encryptPass(password));

        if (record == null) {
            return null;
        }

        DefaultUser user = new DefaultUser(record);
        List<MRRole> roles = AuthenticationManager.me().roleService().findByUser(user.userId());
        MRRole[] roleArr = CollectionUtils.isEmpty(roles) ? new MRRole[0] : roles.toArray(new MRRole[roles.size()]);
        return new DefaultUserWithRoles(user, roleArr);
    }

    @Override
    public boolean resetPass(Object userId) {
        db().update("update meta_user set password = ? where id=?", PassKit.encryptPass(), userId);
        return true;
    }

    @Override
    public boolean setPass(Object userId, String password) {
        db().update("update meta_user set password=? where id=?", PassKit.encryptPass(password), userId);
        return true;
    }

    @Override
    public DefaultUserWithRoles createRoot(Root root) {
        return new DefaultUserWithRoles(root);
    }

    @Override
    public String userObjectCode() {
        return "meta_user";
    }
}
