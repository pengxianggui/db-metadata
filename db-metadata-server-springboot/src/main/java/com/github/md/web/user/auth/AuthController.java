package com.github.md.web.user.auth;

import cn.com.asoco.annotation.Authorize;
import cn.com.asoco.http.HttpResult;
import com.github.md.web.user.AuthenticationManager;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author pengxg
 * @date 2022/8/10 10:57 上午
 */
@RestController("metaAuthController")
@RequestMapping("auth")
public class AuthController {

    @Authorize(justSign = true)
    @GetMapping("list")
    public HttpResult<List<IAuth>> all() {
        return HttpResult.success(AuthenticationManager.me().getAuthService().findAll());
    }
}
