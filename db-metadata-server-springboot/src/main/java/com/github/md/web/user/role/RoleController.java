package com.github.md.web.user.role;

import cn.com.asoco.annotation.Authorize;
import com.github.md.analysis.kit.Kv;
import com.github.md.analysis.kit.Ret;
import com.github.md.web.controller.ControllerAdapter;
import com.github.md.web.user.AuthenticationManager;
import com.github.md.web.user.auth.IAuth;
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
    public Ret getAuths(@PathVariable("roleId") String roleId) {
        List<IAuth> auths = AuthenticationManager.me().getAuthService().findByRole(roleId);
        return Ret.ok("data", auths.stream().map(IAuth::toKv).collect(Collectors.toList()));
    }

    @Authorize(value = "bind:auths:to-role")
    @PostMapping("{roleId}/auths")
    public Ret bindAuths(@PathVariable("roleId") String roleId) {
        Kv kv = parameterHelper().getKv();
        String authId = StrKit.defaultIfBlank(kv.getStr("authId"), "");
        boolean flag = AuthenticationManager.me().getRoleService().bindAuthsForRole(roleId, authId.split(","));
        return flag ? Ret.ok() : Ret.fail();
    }
}
