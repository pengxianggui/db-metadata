package com.github.md.web.user.auth;

import com.github.md.analysis.kit.Ret;
import com.github.md.web.user.AuthenticationManager;
import com.github.md.web.user.auth.annotations.Authorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author pengxg
 * @date 2022/8/10 10:57 上午
 */
@RestController("metaAuthController")
@RequestMapping("auth")
public class AuthController {

    @Authorize(justSign = true)
    @GetMapping("list")
    public Ret all() {
        return Ret.ok("data", AuthenticationManager.me().getAuthService().findAll());
    }
}
