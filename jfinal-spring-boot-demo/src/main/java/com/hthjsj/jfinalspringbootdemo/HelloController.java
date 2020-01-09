package com.hthjsj.jfinalspringbootdemo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p> @Date : 2019/12/31 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
@Slf4j
@RestController
public class HelloController {

    @GetMapping("/sp/hello")
    public String hello() {
        log.info("fajsldkjflkas");
        return "okokokokok";
    }
}
