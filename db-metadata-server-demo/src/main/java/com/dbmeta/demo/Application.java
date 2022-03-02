package com.dbmeta.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author pengxg
 * @date 2022/3/2 8:55 上午
 */
@SpringBootApplication(scanBasePackages = {"com.github.md.*", "com.dbmeta.demo"})
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
