package com.github.md.web.utils;

import com.github.md.web.kit.PassKit;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author pengxg
 * @date 2022/4/14 9:39 下午
 */
@SpringBootTest
@Slf4j
public class PassKitTest {
    @Test
    public void test() {
        String pass1 = PassKit.encryptPass();
        String pass2 = PassKit.encryptPass("888888");
        log.info(pass1);
        log.info(pass2);
    }
}
