package com.hthjsj.web.user.jeesite;

import com.google.common.collect.Lists;
import com.hthjsj.web.user.AbstractUserService;
import com.hthjsj.web.user.UserIntercept;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;

import java.util.List;

/**
 * <p> @Date : 2019/12/13 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
public class JeesiteUserService extends AbstractUserService<JeesiteUser> {

    private static final String JEESITE = "jeesite";

    @Override
    public String tokenKey() {
        return "jeesite_uid";
    }

    @Override
    public String loginKey() {
        return "username";
    }

    @Override
    public String pwdKey() {
        return "password";
    }

    @Override
    public String cookieKey() {
        return JEESITE;
    }

    @Override
    public JeesiteUser login(String username, String password) {
        Record user = Db.use(JEESITE).findFirst("select * from js_sys_user where user_code=? and password=?", username, password);
        if (user != null) {
            JeesiteUser jeesiteUser = new JeesiteUser(user.getColumns());
            UserIntercept.caches.put(jeesiteUser.userId(), jeesiteUser);
            return jeesiteUser;
        }
        return null;
    }

    @Override
    public boolean logout(JeesiteUser user) {
        UserIntercept.caches.invalidate(user.userId());
        return true;
    }

    @Override
    public boolean logged(JeesiteUser user) {
        return UserIntercept.caches.getIfPresent(user.userId()) != null;
    }

    @Override
    public boolean isExpired(JeesiteUser user) {
        return UserIntercept.caches.getIfPresent(user.userId()) != null;
    }

    @Override
    public List<JeesiteUser> findAll() {
        List<JeesiteUser> users = Lists.newArrayList();
        Db.use(JEESITE).findAll("js_sys_user").forEach(r -> {
            users.add(new JeesiteUser(r.getColumns()));
        });
        return users;
    }

    @Override
    public JeesiteUser findById(Object idValue) {
        Record user = Db.use(JEESITE).findFirst("select * from js_sys_user where user_code=?", idValue);
        return new JeesiteUser(user.getColumns());
    }

    @Override
    public boolean updateById(JeesiteUser user) {
        return false;
    }
}
