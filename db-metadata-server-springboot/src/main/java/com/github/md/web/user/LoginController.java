package com.github.md.web.user;

import com.github.md.analysis.kit.Ret;
import com.github.md.web.controller.ControllerAdapter;
import com.github.md.web.user.role.UserWithRolesWrapper;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * <p> @Date : 2019/12/13 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
@ConditionalOnProperty(name = {"md.server.login.ctrl.enable"}, havingValue = "true", matchIfMissing = true)
@RestController
@RequestMapping("user")
public class LoginController extends ControllerAdapter {

    @PostMapping("login")
    public Ret login(HttpServletResponse response) {
        String uid = parameterHelper().getPara(AuthenticationManager.me().loginService().loginKey());
        String pwd = parameterHelper().getPara(AuthenticationManager.me().loginService().pwdKey());

        User user = AuthenticationManager.me().loginService().login(uid, pwd);
        if (user != null) {
            Cookie cookie = new Cookie(AuthenticationManager.me().loginService().cookieKey(), user.userId());
            cookie.setMaxAge((int) TimeUnit.HOURS.toSeconds(6));
            response.addCookie(cookie);
            // TODO token生成并塞入
            return Ret.ok("data", user.toKv());
        } else {
            return Ret.fail();
        }
    }

    @PostMapping("logout")
    public Ret logout() {
        UserWithRolesWrapper user = AuthenticationManager.me().getUser(getRequest());
        if (Objects.isNull(user)) {
            throw new UserException("未登录");
        }
        AuthenticationManager.me().loginService().logout(user);
        return Ret.ok();
    }

    @GetMapping("info")
    public Ret info() {
        User user = AuthenticationManager.me().getUser(getRequest());
        return Ret.ok("data", user);
    }

//    public void update() {
//        String updateAttr = getPara("attrs");
//        Kv attrs = UtilKit.getKv(updateAttr);
//        User u = UserThreadLocal.getUser();
//        u.attrs(attrs);
//        UserService userService = AuthenticationManager.me().userService();
//        renderJson(userService.updateById(u) ? Ret.ok() : Ret.fail());
//    }
//
//    public void list() {
//        UserService userService = AuthenticationManager.me().userService();
//        renderJson(Ret.ok("data", userService.findAll()));
//    }
}
