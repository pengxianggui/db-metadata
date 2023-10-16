package com.hthjsj.md.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author pengxg
 * @date 2023/10/16 13:59
 */
@SpringBootApplication(scanBasePackages = {"com.github.md.*"})
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
