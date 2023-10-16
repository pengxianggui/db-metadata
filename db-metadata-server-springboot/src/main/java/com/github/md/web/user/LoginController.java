package com.github.md.web.user;

import com.github.md.web.controller.ControllerAdapter;
import com.github.md.web.kit.AssertKit;
import com.github.md.web.res.Res;
import com.github.md.web.user.role.UserWithRolesWrapper;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

/**
 * <p> @Date : 2019/12/13 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
@ConditionalOnProperty(name = {"md.server.login.ctrl.enable"}, havingValue = "true", matchIfMissing = true)
@RestController("metaLoginController")
@RequestMapping
public class LoginController extends ControllerAdapter {

    @PostMapping("${md.server.login.ctrl.login-path:/user/login}")
    public Res login(HttpServletResponse response) {
        String uid = parameterHelper().getPara(AuthenticationManager.me().getLoginService().loginKey());
        String pwd = parameterHelper().getPara(AuthenticationManager.me().getLoginService().pwdKey());

        UserWithRolesWrapper user = AuthenticationManager.me().getLoginService().login(uid, pwd);
        LoginVO loginVO = AuthenticationManager.me().getLoginService().setLogged(user);
        if (loginVO != null) {
            return Res.ok(loginVO);
        } else {
            return Res.fail("用户名或密码输入错误");
        }
    }

    @PostMapping("${md.server.login.ctrl.logout-path:/user/logout}")
    public Res logout() {
        boolean flag = AuthenticationManager.me().getLoginService().logout(getRequest());
        return flag ? Res.ok() : Res.fail("登出失败");
    }

    @GetMapping("${md.server.login.ctrl.info-path:/user/info}")
    public Res info() {
        LoginVO loginVO = AuthenticationManager.me().getInfo(getRequest());
        AssertKit.isTrue(loginVO != null, new UnLoginException("未登录"));
        return Res.ok(loginVO);
    }

}
