package com.github.md.web.user.support.defaults;

import com.github.md.analysis.SpringAnalysisManager;
import com.github.md.web.kit.PassKit;
import com.github.md.web.user.*;
import com.github.md.web.user.auth.IAuth;
import com.github.md.web.user.role.DefaultUserWithRoles;
import com.github.md.web.user.role.MRRole;
import com.github.md.web.user.role.UserWithRolesWrapper;
import com.google.common.collect.Lists;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.DbPro;
import com.jfinal.plugin.activerecord.Record;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author pengxg
 * @date 2022/2/18 10:40 上午
 */
@Slf4j
public class DefaultUserService extends AbstractUserService<DefaultUser, DefaultUserWithRoles> {

    protected final TokenGenerator tokenGenerator;

    public DefaultUserService() {
        this.tokenGenerator = new JWTTokenGenerator();
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
        return db().update("meta_user", user.getData());
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
    public void refresh(String userId) {
        Map.Entry<String, DefaultUserWithRoles> hitEntry = getAllLoggedUsers().entrySet().stream()
                .filter(entry -> entry.getValue().userId().equals(userId)).findFirst().orElse(null);
        if (hitEntry == null) {
            log.warn("user is not logged! can refresh. userId: {}", userId);
            return;
        }

        final String token = hitEntry.getKey();
        Record record = db().findFirst("select * from meta_user where id=?", userId);
        if (record == null) {
            log.warn("user is not exist! can refresh. userId: {}", userId);
            return;
        }

        DefaultUser user = new DefaultUser(record);
        List<MRRole> roles = AuthenticationManager.me().getRoleService().findByUser(user.userId());
        MRRole[] roleArr = CollectionUtils.isEmpty(roles) ? new MRRole[0] : roles.toArray(new MRRole[roles.size()]);
        AuthenticationManager.me().getLoginUsers().put(token, new DefaultUserWithRoles(user, roleArr)); // 缓存到内存中, 保持token不变
    }

    @Override
    public DefaultUserWithRoles login(String identity, String password) {
        Record record = db().findFirst("select * from meta_user where username=? and password=?",
                identity, PassKit.encryptPass(password));

        if (record == null) {
            throw new UnLoginException("认证错误");
        }

        DefaultUser user = new DefaultUser(record);
        List<MRRole> roles = AuthenticationManager.me().getRoleService().findByUser(user.userId());
        MRRole[] roleArr = CollectionUtils.isEmpty(roles) ? new MRRole[0] : roles.toArray(new MRRole[roles.size()]);
        return new DefaultUserWithRoles(user, roleArr);
    }

    @Override
    public LoginVO getInfo(HttpServletRequest request) {
        UserWithRolesWrapper user = getUser(request);
        String token = tokenGenerator.generate(user);
        return createLoginVO(token, user);
    }

    @Override
    public LoginVO setLogged(DefaultUserWithRoles user) {
        String token = tokenGenerator.generate(user);
        AuthenticationManager.me().getLoginUsers().put(token, user); // 缓存到内存中
        return createLoginVO(token, user);
    }

    private LoginVO createLoginVO(String token, UserWithRolesWrapper user) {
        return DefaultLoginVO.builder()
                .token(token)
                .root(user.isRoot())
                .id(user.userId())
                .username(user.userName())
                .avatar(user.avatar())
                .roles(Arrays.stream(user.roles()).map(MRRole::code).collect(Collectors.toSet()))
                .auths(Arrays.stream(user.auths()).map(IAuth::code).collect(Collectors.toSet()))
                .attrs(user.attrs()).build();
    }

    @Override
    public boolean logged(DefaultUserWithRoles user) {
        return logged(user.userId());
    }

    @Override
    public boolean logout(DefaultUserWithRoles user) {
        String token = tokenGenerator.generate(user);
        AuthenticationManager.me().getLoginUsers().invalidate(token);
        return !logged(user);
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
    public String userObjectCode() {
        return "meta_user";
    }
}
