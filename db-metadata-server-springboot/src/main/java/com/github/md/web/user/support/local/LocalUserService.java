package com.github.md.web.user.support.local;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.md.analysis.kit.Kv;
import com.github.md.web.ex.OprNotSupportException;
import com.github.md.web.kit.UtilKit;
import com.github.md.web.user.AbstractUserService;
import com.github.md.web.user.AuthenticationManager;
import com.github.md.web.user.LoginVO;
import com.github.md.web.user.role.MRRole;
import com.github.md.web.user.role.RoleFactory;
import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import com.google.common.io.Files;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.system.ApplicationHome;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p> @Date : 2019/12/13 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
@Slf4j
public class LocalUserService extends AbstractUserService<LocalUser, LocalUser> {

    private static final List<LocalUser> users = new ArrayList<>();

    private final String fileName;

    public LocalUserService(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public LocalUser login(String username, String password) {
        LocalUser user = null;
        List<LocalUser> userList = findAll().stream().filter(l -> l.userName().equalsIgnoreCase(username) && l.password().equals(password)).collect(Collectors.toList());

        if (!userList.isEmpty()) {
            user = userList.get(0);
        }

        if (user != null) {
            AuthenticationManager.me().getLoginUsers().put(user.userId(), user);
        }
        return user;
    }

    @Override
    public LoginVO getInfo(HttpServletRequest request) {
        return getInfo(request);
    }

    @Override
    public LoginVO setLogged(LocalUser user) {
        AuthenticationManager.me().getLoginUsers().put(user.userId(), user);
        return user;
    }

    @Override
    public boolean logout(LocalUser user) {
        AuthenticationManager.me().getLoginUsers().invalidate(user.userId());
        return !logged(user);
    }

    @Override
    public boolean logged(LocalUser user) {
        return AuthenticationManager.me().getLoginUsers().getIfPresent(user.userId()) != null;
    }

    @Override
    public List<LocalUser> findAll() {
        if (users.isEmpty()) {
            String userJson = UtilKit.loadConfigByFile(fileName);
            JSONArray userObjs = JSON.parseObject(userJson).getJSONArray("users");
            List<MRRole> roleList = Lists.newArrayList();

            for (int i = 0; i < userObjs.size(); i++) {
                JSONObject j = (JSONObject) userObjs.get(i);
                if (j.containsKey("roles")) {
                    JSONArray roleObjs = j.getJSONArray("roles");
                    for (int i1 = 0; i1 < roleObjs.size(); i1++) {
                        JSONObject roleObj = (JSONObject) roleObjs.get(i1);
                        roleList.add(RoleFactory.createRole(roleObj.getString("code"), roleObj.getString("name")));
                    }
                }
                users.add(new LocalUser(j.getInnerMap(), roleList.toArray(new MRRole[roleList.size()])));
            }
        }
        return users;
    }

    @Override
    public LocalUser findById(Object idValue) {
        List<LocalUser> userList = findAll();
        return userList.stream().filter(u -> String.valueOf(idValue).equals(u.userId())).findFirst().orElse(null);
    }

    @Override
    public boolean updateById(Object idValue, Map data) {
        data.put(LocalUser.ID_KEY, idValue);
        LocalUser user = new LocalUser(data);
        return updateById(user);
    }

    /**
     * FIXME 存在线程安全问题
     *
     * @param user
     */
    @Override
    public boolean updateById(LocalUser user) {
        String locationPath = new ApplicationHome().getDir().toString();
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

    @Override
    public boolean bindRolesForUser(String userId, String... roleIds) {
        return false;
    }

    @Override
    public boolean resetPass(Object userId) {
        throw new OprNotSupportException("本地文件用户服务不支持重置密码");
    }

    @Override
    public boolean setPass(Object userId, String password) {
        throw new OprNotSupportException("本地文件用户服务不支持设置密码");
    }

    @Override
    public String userObjectCode() {
        throw new OprNotSupportException("本地文件用户服务不支持获取用户元对象编码");
    }
}
