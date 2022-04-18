package com.github.md.web.user;

import cn.com.asoco.util.AssertUtil;
import com.github.md.analysis.kit.Ret;
import com.github.md.web.controller.ControllerAdapter;
import com.github.md.web.user.role.UserWithRolesWrapper;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.Objects;

/**
 * <p> @Date : 2019/12/13 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
@ConditionalOnProperty(name = {"md.server.login.ctrl.enable"}, havingValue = "true", matchIfMissing = true)
@RestController
@RequestMapping
public class LoginController extends ControllerAdapter {

    @PostMapping("${md.server.login.ctrl.login-path:/user/login}")
    public Ret login(HttpServletResponse response) {
        String uid = parameterHelper().getPara(AuthenticationManager.me().loginService().loginKey());
        String pwd = parameterHelper().getPara(AuthenticationManager.me().loginService().pwdKey());

        LoginVO loginVO = AuthenticationManager.me().login(uid, pwd);
        if (loginVO != null) {
            return Ret.ok("data", loginVO);
        } else {
            return Ret.fail().set("msg", "用户名或密码输入错误");
        }
    }

    @PostMapping("${md.server.login.ctrl.logout-path:/user/logout}")
    public Ret logout() {
        UserWithRolesWrapper user = AuthenticationManager.me().getUser(getRequest());
        if (Objects.isNull(user)) {
            throw new UserException("未登录");
        }
        boolean flag = AuthenticationManager.me().logout(user);
        return flag ? Ret.ok() : Ret.fail();
    }

    @GetMapping("${md.server.login.ctrl.info-path:/user/info}")
    public Ret info() {
        LoginVO loginVO = AuthenticationManager.me().getInfo(getRequest());
        AssertUtil.isTrue(loginVO != null, new UnLoginException("未登录"));
        return Ret.ok("data", loginVO);
    }

}
