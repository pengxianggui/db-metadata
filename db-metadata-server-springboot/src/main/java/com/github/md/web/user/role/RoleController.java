package com.github.md.web.user.role;

import com.github.md.analysis.kit.Kv;
import com.github.md.web.controller.ControllerAdapter;
import com.github.md.web.res.Res;
import com.github.md.web.user.AuthenticationManager;
import com.github.md.web.user.auth.IAuth;
import com.github.md.web.user.auth.annotations.Authorize;
import com.jfinal.kit.StrKit;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author pengxg
 * @date 2022/2/20 10:49 上午
 */
@RestController
@RequestMapping("role")
public class RoleController extends ControllerAdapter {

    @GetMapping("{roleId}/auths")
    public Res getAuths(@PathVariable("roleId") String roleId) {
        List<IAuth> auths = AuthenticationManager.me().getAuthService().findByRole(roleId);
        return Res.ok(auths.stream().map(IAuth::toKv).collect(Collectors.toList()));
    }

    @Authorize(value = "bind:auths:to-role")
    @PostMapping("{roleId}/auths")
    public Res bindAuths(@PathVariable("roleId") String roleId) {
        Kv kv = parameterHelper().getKv();
        String authId = StrKit.defaultIfBlank(kv.getStr("authId"), "");
        boolean flag = AuthenticationManager.me().getRoleService().bindAuthsForRole(roleId, authId.split(","));
        return flag ? Res.ok() : Res.fail("操作失败，请稍后重试");
    }
}
