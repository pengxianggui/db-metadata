package com.github.md.web.user;

import com.github.md.analysis.kit.Kv;
import com.github.md.analysis.kit.Ret;
import com.github.md.web.ServiceManager;
import com.github.md.web.controller.ControllerAdapter;
import com.github.md.web.user.role.MRRole;
import com.jfinal.kit.StrKit;
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

    @GetMapping("{userId}/roles")
    public Ret getRoles(@PathVariable("userId") String userId) {
        List<MRRole> roles = ServiceManager.roleService().findByUser(userId);
        return Ret.ok("data", roles.stream().map(MRRole::toKv).collect(Collectors.toList()));
    }

    @PostMapping("{userId}/roles")
    public Ret bindRoles(@PathVariable("userId") String userId) {
        Kv kv = parameterHelper().getKv();
        String roleId = StrKit.defaultIfBlank(kv.getStr("roleId"), "");

        boolean flag = UserManager.me().getUserFactory().userService()
                .bindRolesForUser(userId, roleId.split(","));
        return flag ? Ret.ok() : Ret.fail();
    }
}
