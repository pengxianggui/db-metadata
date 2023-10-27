package com.github.md.web.controller;

import com.github.md.web.res.Res;
import com.github.md.web.ServiceManager;
import com.github.md.web.user.auth.annotations.ApiType;
import com.github.md.web.user.auth.annotations.Authorize;
import com.github.md.web.user.auth.annotations.Type;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/snippet")
public class SnippetController {

    @ApiType(Type.API)
    @Authorize(justSign = true)
    @GetMapping("options")
    public Res options() {
        return Res.ok(ServiceManager.getSnippetService().listForOptions());
    }
}
