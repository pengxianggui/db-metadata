package com.github.md.web.user;

import cn.com.asoco.annotation.Authorize;
import com.github.md.analysis.kit.Kv;
import com.github.md.analysis.kit.Ret;
import com.github.md.analysis.meta.IMetaObject;
import com.github.md.analysis.meta.MetaData;
import com.github.md.web.controller.ControllerAdapter;
import com.github.md.web.query.FormDataFactory;
import com.github.md.web.user.auth.IAuth;
import com.github.md.web.user.auth.annotations.MetaAccess;
import com.github.md.web.user.role.MRRole;
import com.github.md.web.user.role.UserWithRolesWrapper;
import com.jfinal.kit.StrKit;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author pengxg
 * @date 2022/2/18 5:20 下午
 */
@RestController
@RequestMapping("user")
public class UserController extends ControllerAdapter {

    @Authorize(justSign = true)
    @GetMapping("{userId}/roles")
    public Ret getRoles(@PathVariable("userId") String userId) {
        List<MRRole> roles = AuthenticationManager.me().roleService().findByUser(userId);
        return Ret.ok("data", roles.stream().map(MRRole::toKv).collect(Collectors.toList()));
    }

    @Authorize(value = "bind:roles:to-user")
    @PostMapping("{userId}/roles")
    public Ret bindRoles(@PathVariable("userId") String userId) {
        Kv kv = parameterHelper().getKv();
        String roleId = StrKit.defaultIfBlank(kv.getStr("roleId"), "");

        String[] roleIdArr = roleId.split(",");

        boolean flag = AuthenticationManager.me().userService()
                .bindRolesForUser(userId, roleIdArr);
        return flag ? Ret.ok() : Ret.fail();
    }

    @Authorize(justSign = true)
    @PostMapping("update")
    public Ret updateUserData() {
        IMetaObject metaObject = metaService().findByCode(AuthenticationManager.me().userService().userObjectCode());
        MetaData metadata = FormDataFactory.buildFormData(getRequest().getParameterMap(), metaObject, true);
        String userId = UserThreadLocal.getUser().userId();
        metadata.set(metaObject.primaryKey(), userId);
        boolean flag = metaService().updateData(metaObject, metadata);
        return flag ? Ret.ok() : Ret.fail();
    }

    @ApiOperation("获取当前登录用户拥有的角色")
    @MetaAccess
    @GetMapping("roles")
    public Ret getRoles() {
        UserWithRolesWrapper user = AuthenticationManager.me().getUser(getRequest());
        List<MRRole> roles = AuthenticationManager.me().isRoot(user)
                ? AuthenticationManager.me().roleService().findAll()
                : AuthenticationManager.me().roleService().findByUser(user.userId());

        return Ret.ok("data", roles.stream().map(MRRole::toKv).collect(Collectors.toList()));
    }

    @ApiOperation("获取当前登录用户拥有的权限")
    @Authorize(justSign = true)
    @GetMapping("auths")
    public Ret getAuths() {
        UserWithRolesWrapper user = AuthenticationManager.me().getUser(getRequest());
        List<IAuth> auths = AuthenticationManager.me().isRoot(user)
                ? AuthenticationManager.me().authService().findAll()
                : AuthenticationManager.me().authService().findByUser(user.userId());

        return Ret.ok("data", auths.stream().map(IAuth::toKv).collect(Collectors.toList()));
    }

    @MetaAccess
    @ApiOperation("重置用户密码")
    @PostMapping("reset-pass")
    public Ret resetPass() {
        String userId = parameterHelper().get("userId");
        boolean flag = AuthenticationManager.me().userService().resetPass(userId);
        return flag ? Ret.ok().set("msg", "重置成功") : Ret.fail();
    }

    @Authorize(justSign = true)
    @PostMapping("set-pass")
    public Ret setPass() {
        String password = parameterHelper().get("password");
        String userId = UserThreadLocal.getUser().userId();
        boolean flag = AuthenticationManager.me().userService().setPass(userId, password);
        return flag ? Ret.ok().set("msg", "密码修改成功") : Ret.fail();
    }
}
