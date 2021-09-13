package com.github.md.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * <p> @Date : 2021/9/7 </p>
 * <p> @Project : db-metadata-server-springboot</p>
 *
 * <p> @author konbluesky </p>
 */
@SpringBootApplication(scanBasePackages = "com.github.md.*")
public class MetaServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(MetaServerApplication.class, args);
    }
}
