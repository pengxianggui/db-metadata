package com.hthjsj.web.user.jeesite;

import com.hthjsj.web.user.AbstractUserService;
import com.hthjsj.web.user.UserAuthIntercept;
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
        return "jeesite";
    }

    @Override
    public JeesiteUser login(String username, String password) {
        Record user = Db.use("jeesite").findFirst("select * from js_sys_user where user_code=? and password=?", username, password);
        if (user != null) {
            JeesiteUser jeesiteUser = new JeesiteUser(user.getColumns());
            UserAuthIntercept.caches.put(jeesiteUser.userId(), jeesiteUser);
            return jeesiteUser;
        }
        return null;
    }

    @Override
    public boolean logout(JeesiteUser user) {
        UserAuthIntercept.caches.invalidate(user.userId());
        return false;
    }

    @Override
    public boolean logged(JeesiteUser user) {
        return false;
    }

    @Override
    public boolean isExpired(JeesiteUser user) {
        return false;
    }

    @Override
    public List<JeesiteUser> findAll() {
        return null;
    }

    @Override
    public JeesiteUser findById(Object idValue) {
        Record user = Db.use("jeesite").findFirst("select * from js_sys_user where user_code=?", idValue);
        return new JeesiteUser(user.getColumns());
    }

    @Override
    public boolean updateById(JeesiteUser user) {
        return false;
    }
}
