package com.github.md.web.controller;

import com.github.md.analysis.kit.Ret;
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
    public Ret options() {
        return Ret.ok("data", ServiceManager.getSnippetService().listForOptions());
    }
}
