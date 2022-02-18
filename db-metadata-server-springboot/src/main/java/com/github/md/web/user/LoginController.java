package com.github.md.web.user;

import com.github.md.analysis.kit.Ret;
import com.github.md.web.controller.ControllerAdapter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

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
    public Ret login() {
        String uid = parameterHelper().getPara(UserManager.me().loginService().loginKey());
        String pwd = parameterHelper().getPara(UserManager.me().loginService().pwdKey());

        User user = UserManager.me().loginService().login(uid, pwd);
        return Objects.nonNull(user) ? Ret.ok("data", user) : Ret.fail();
    }

    @PostMapping("logout")
    public Ret logout() {
        User user = UserManager.me().getUser(getRequest());
        if (Objects.isNull(user)) {
            throw new UserException("未登录");
        }
        UserManager.me().loginService().logout(user);
        return Ret.ok();
    }

    @GetMapping("info")
    public Ret info() {
        User user = UserManager.me().getUser(getRequest());
        return Ret.ok("data", user);
    }

//    public void update() {
//        String updateAttr = getPara("attrs");
//        Kv attrs = UtilKit.getKv(updateAttr);
//        User u = UserThreadLocal.getUser();
//        u.attrs(attrs);
//        UserService userService = UserManager.me().userService();
//        renderJson(userService.updateById(u) ? Ret.ok() : Ret.fail());
//    }
//
//    public void list() {
//        UserService userService = UserManager.me().userService();
//        renderJson(Ret.ok("data", userService.findAll()));
//    }
}
