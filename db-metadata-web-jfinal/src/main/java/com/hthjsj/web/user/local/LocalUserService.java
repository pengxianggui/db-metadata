package com.hthjsj.web.user.local;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Joiner;
import com.google.common.io.Files;
import com.hthjsj.web.kit.UtilKit;
import com.hthjsj.web.user.AbstractUserService;
import com.hthjsj.web.user.UserAuthIntercept;
import com.jfinal.kit.Kv;
import com.jfinal.server.undertow.PathKitExt;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * <p> @Date : 2019/12/13 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
@Slf4j
public class LocalUserService extends AbstractUserService<LocalUser> {

    private static List<LocalUser> users = new ArrayList<>();

    private String fileName;

    public LocalUserService(String fileName) {
        this.fileName = fileName;
    }

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
        LocalUser user = findAll().stream().filter(l -> l.userName().equalsIgnoreCase(username)).findFirst().get();
        if (user != null) {
            UserAuthIntercept.caches.put(user.userId(), user);
        }
        return user;
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
            String userJson = UtilKit.loadConfigByFile(fileName);
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
        String userJson = UtilKit.loadConfigByFile(fileName);
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

    /**
     * FIXME 存在线程安全问题
     *
     * @param user
     */
    @Override
    public boolean updateById(LocalUser user) {
        String locationPath = PathKitExt.getLocationPath();
        try {
            Iterator<LocalUser> it = findAll().iterator();
            while (it.hasNext()) {
                LocalUser u = it.next();
                if (user.userId().equalsIgnoreCase(u.userId())) {
                    u.attrs = user.attrs;
                }
            }
            String destFilePath = Joiner.on(File.separator).join(locationPath, "config", fileName);
            File f = new File(destFilePath);
            if (!f.exists()) {
                Files.createParentDirs(f);
                String json = JSON.toJSONString(Kv.by("users", findAll()), true);
                Files.write(json.getBytes(), f);
            }
        } catch (IOException e) {
            log.error(e.getMessage(), e);
            return false;
        }
        return true;
    }
}
