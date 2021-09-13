package com.github.md.web.user;

/**
 * <p> @Date : 2019/12/13 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
public class UserController /*extends Controller*/ {

   /* @Clear({MRAuthIntercept.class, UserIntercept.class})
    public void login() {
        LoginService loginService = UserManager.me().loginService();
        String uid = getPara(loginService.loginKey());
        String pwd = getPara(loginService.pwdKey());
        Preconditions.checkNotNull(uid, "用户名必须填写");
        Preconditions.checkNotNull(pwd, "密码必须填写");
        User user = loginService.login(uid, pwd);
        renderJson(user != null ? Ret.ok("data", user) : Ret.fail());
    }

    public void logout() {

    }

    public void info() {
        User user = UserManager.me().getUser(getRequest());
        renderJson(Ret.ok("data", user));
    }

    public void update() {
        String updateAttr = getPara("attrs");
        Kv attrs = UtilKit.getKv(updateAttr);
        User u = UserThreadLocal.getUser();
        u.attrs(attrs);
        UserService userService = UserManager.me().userService();
        renderJson(userService.updateById(u) ? Ret.ok() : Ret.fail());
    }

    public void list() {
        UserService userService = UserManager.me().userService();
        renderJson(Ret.ok("data", userService.findAll()));
    }*/
}
