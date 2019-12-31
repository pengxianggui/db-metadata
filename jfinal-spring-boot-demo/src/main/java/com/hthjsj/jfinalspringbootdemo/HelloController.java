package com.hthjsj.jfinalspringbootdemo;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p> @Date : 2019/12/31 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
@RestController
public class HelloController {

    @GetMapping("/sp/hello")
    public String hello() {
        return "okokokokok";
    }
}
