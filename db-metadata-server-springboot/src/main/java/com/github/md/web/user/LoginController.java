package com.github.md.web.user;

import cn.com.asoco.util.AssertUtil;
import com.github.md.analysis.kit.Ret;
import com.github.md.web.controller.ControllerAdapter;
import com.github.md.web.user.auth.IAuth;
import com.github.md.web.user.role.MRRole;
import com.github.md.web.user.role.UserWithRolesWrapper;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Collectors;

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

        UserWithRolesWrapper user = AuthenticationManager.me().loginService().login(uid, pwd);
        if (user != null) {
            AuthenticationManager.me().loginService().setLogged(user);
            // 见AbstractUserService#getUser(request)，暂时取消cookie支持
//            Cookie cookie = new Cookie(AuthenticationManager.me().loginService().cookieKey(), user.userId());
//            cookie.setMaxAge((int) TimeUnit.HOURS.toSeconds(6));
//            response.addCookie(cookie);

            LoginVO vo = new LoginVO(
                    user.userId(), // TODO token生成并塞入
                    user.userId(),
                    user.userName(),
                    Arrays.stream(user.roles()).map(MRRole::code).collect(Collectors.toSet()),
                    Arrays.stream(user.auths()).map(IAuth::code).collect(Collectors.toSet()),
                    user.attrs()
            );
            return Ret.ok("data", vo);
        } else {
            return Ret.fail().set("msg", "用户名或密码输入错误");
        }
    }

    @PostMapping("logout")
    public Ret logout() {
        UserWithRolesWrapper user = AuthenticationManager.me().getUser(getRequest());
        if (Objects.isNull(user)) {
            throw new UserException("未登录");
        }
        boolean flag = AuthenticationManager.me().loginService().logout(user);
        return flag ? Ret.ok() : Ret.fail();
    }

    @GetMapping("info")
    public Ret info() {
        UserWithRolesWrapper user = AuthenticationManager.me().getUser(getRequest());
        AssertUtil.isTrue(user != null, new UnLoginException("未登录"));

        LoginVO vo = new LoginVO(
                user.userId(), // TODO token生成并塞入
                user.userId(),
                user.userName(),
                Arrays.stream(user.roles()).map(MRRole::code).collect(Collectors.toSet()),
                Arrays.stream(user.auths()).map(IAuth::code).collect(Collectors.toSet()),
                user.attrs()
        );
        return Ret.ok("data", vo);
    }
}
