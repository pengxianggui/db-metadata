package com.hthjsj.web.user.local;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hthjsj.web.UtilKit;
import com.hthjsj.web.user.AbstractUserService;

import java.util.ArrayList;
import java.util.List;

/**
 * <p> @Date : 2019/12/13 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
public class LocalUserService extends AbstractUserService<LocalUser> {

    private static List<LocalUser> users = new ArrayList<>();

    @Override
    public String tokenKey() {
        return "uid";
    }

    @Override
    public String loginKey() {
        return "uid";
    }

    @Override
    public String pwdKey() {
        return "pwd";
    }

    @Override
    public String cookieKey() {
        return "db-meta-serve";
    }

    @Override
    public LocalUser login(String username, String password) {
        return findAll().stream().filter(l -> l.userName().equalsIgnoreCase(username)).findFirst().get();
    }

    @Override
    public boolean logout(LocalUser user) {
        return false;
    }

    @Override
    public boolean logged(LocalUser user) {
        return false;
    }

    @Override
    public boolean isExpired(LocalUser user) {
        return false;
    }

    @Override
    public List<LocalUser> findAll() {
        if (users.isEmpty()) {
            String userJson = UtilKit.loadConfigByFile("user.json");
            JSONArray userObjs = JSON.parseObject(userJson).getJSONArray("users");
            for (int i = 0; i < userObjs.size(); i++) {
                JSONObject j = (JSONObject) userObjs.get(i);
                users.add(new LocalUser(j.getInnerMap()));
            }
        }
        return users;
    }

    @Override
    public LocalUser findById(Object idValue) {
        String userJson = UtilKit.loadConfigByFile("user.json");
        JSONArray userObjs = JSON.parseObject(userJson).getJSONArray("users");
        LocalUser user = null;
        for (int i = 0; i < userObjs.size(); i++) {
            JSONObject j = (JSONObject) userObjs.get(i);
            if (j.getString("userId").equalsIgnoreCase(String.valueOf(idValue))) {
                user = new LocalUser(j.getInnerMap());
            }
        }
        return user;
    }

    @Override
    public void updateById(LocalUser user) {

    }
}
