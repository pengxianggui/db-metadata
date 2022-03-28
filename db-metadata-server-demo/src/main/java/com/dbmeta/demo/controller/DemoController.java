package com.dbmeta.demo.controller;

import cn.com.asoco.http.HttpResult;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.bind.annotation.*;

/**
 * @author pengxg
 * @date 2022/3/18 4:07 下午
 */
@RestController
@RequestMapping
public class DemoController {

    @GetMapping("hello")
    public HttpResult demo() {
        return HttpResult.success(null, "world");
    }

    @PostMapping("post")
    public HttpResult test(@RequestBody Body body) {
        return HttpResult.success(body);
    }

    @Getter
    @Setter
    public static class Body {
        private String p1;
        private String p2;
    }
}
