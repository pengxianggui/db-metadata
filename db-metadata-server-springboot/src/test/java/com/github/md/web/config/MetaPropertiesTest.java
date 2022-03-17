package com.github.md.web.config;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * <p> @Date : 2021/9/7 </p>
 * <p> @Project : db-metadata-server-springboot</p>
 *
 * <p> @author konbluesky </p>
 */
@SpringBootTest
@Slf4j
class MetaPropertiesTest {

    @Autowired
    MetaProperties metaProperties;

    @Test
    void isDevMode() {
        log.info("{}", metaProperties.isDevMode());
    }
}
