package com.github.md.web.user;

import com.github.md.analysis.kit.Kv;
import com.github.md.analysis.meta.IMetaObject;
import com.github.md.analysis.meta.MetaData;
import com.github.md.web.controller.ControllerAdapter;
import com.github.md.web.event.EventKit;
import com.github.md.web.event.user.UserStatusChangeMessage;
import com.github.md.web.query.FormDataFactory;
import com.github.md.web.res.Res;
import com.github.md.web.user.auth.IAuth;
import com.github.md.web.user.auth.annotations.ApiType;
import com.github.md.web.user.auth.annotations.Authorize;
import com.github.md.web.user.role.MRRole;
import com.github.md.web.user.role.UserWithRolesWrapper;
import com.jfinal.kit.StrKit;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author pengxg
 * @date 2022/2/18 5:20 下午
 */
@RestController("metaUserController")
@RequestMapping("user")
public class UserController extends ControllerAdapter {

    // optimize 虽然dbmeta内置了此基于注解校验(解决了带参接口path的鉴权问题)，但动态数据鉴权还是需要支持path参数的，只需更新buildInSysData.sql即可——————？？？带参数，关键点在于检索匹配数据库里动态接口资源
    @Authorize(justSign = true)
    @GetMapping("{userId}/roles")
    public Res getRoles(@PathVariable("userId") String userId) {
        List<MRRole> roles = AuthenticationManager.me().getRoleService().findByUser(userId);
        return Res.ok(roles.stream().map(MRRole::toKv).collect(Collectors.toList()));
    }

    @Authorize(value = "bind:roles:to-user")
    @PostMapping("{userId}/roles")
    public Res bindRoles(@PathVariable("userId") String userId) {
        Kv kv = parameterHelper().getKv();
        String roleId = StrKit.defaultIfBlank(kv.getStr("roleId"), "");

        String[] roleIdArr = roleId.split(",");

        boolean flag = AuthenticationManager.me().getUserService()
                .bindRolesForUser(userId, roleIdArr);

        if (flag) {
            EventKit.post(UserStatusChangeMessage.create(userId, UserStatusChangeMessage.Type.ROLE));
            return Res.ok();
        }
        return Res.fail("操作失败，请稍后重试");
    }

    @Authorize(justSign = true)
    @PostMapping("update")
    public Res updateUserData() {
        IMetaObject metaObject = metaService().findByCode(AuthenticationManager.me().getUserService().userObjectCode());
        MetaData metadata = FormDataFactory.buildFormData(getRequest().getParameterMap(), metaObject, true);
        String userId = UserThreadLocal.getUser().userId();
        metadata.set(metaObject.primaryKey(), userId);
        boolean flag = AuthenticationManager.me().getUserService().updateById(userId, metadata);
        return flag ? Res.ok() : Res.fail("操作失败，请稍后重试");
    }

    /**
     * 获取当前登录用户拥有的角色
     *
     * @return
     */
    @ApiType
    @GetMapping("roles")
    public Res getRoles() {
        UserWithRolesWrapper user = AuthenticationManager.me().getLoginService().getUser(getRequest());
        List<MRRole> roles = user.isRoot()
                ? AuthenticationManager.me().getRoleService().findAll()
                : AuthenticationManager.me().getRoleService().findByUser(user.userId());

        return Res.ok(roles.stream().map(MRRole::toKv).collect(Collectors.toList()));
    }

    /**
     * 获取当前登录用户拥有的权限
     * @return
     */
    @Authorize(justSign = true)
    @GetMapping("auths")
    public Res getAuths() {
        UserWithRolesWrapper user = AuthenticationManager.me().getLoginService().getUser(getRequest());
        List<IAuth> auths = user.isRoot()
                ? AuthenticationManager.me().getAuthService().findAll()
                : AuthenticationManager.me().getAuthService().findByUser(user.userId());

        return Res.ok(auths.stream().map(IAuth::toKv).collect(Collectors.toList()));
    }

    /**
     * 重置用户密码
     * @return
     */
    @ApiType
    @PostMapping("reset-pass")
    public Res resetPass() {
        String userId = parameterHelper().get("userId");
        boolean flag = AuthenticationManager.me().getUserService().resetPass(userId);
        return flag ? Res.ok(null, "重置成功") : Res.fail("操作失败，请稍后重试");
    }

    @Authorize(justSign = true)
    @PostMapping("set-pass")
    public Res setPass() {
        String password = parameterHelper().get("password");
        String userId = UserThreadLocal.getUser().userId();
        boolean flag = AuthenticationManager.me().getUserService().setPass(userId, password);
        return flag ? Res.ok(null, "密码修改成功") : Res.fail("操作失败，请稍后重试");
    }
}
