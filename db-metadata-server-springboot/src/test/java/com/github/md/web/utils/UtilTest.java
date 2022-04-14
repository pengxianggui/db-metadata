package com.github.md.web.utils;

import cn.hutool.crypto.SecureUtil;
import com.github.md.analysis.db.SnowFlake;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author pengxg
 * @date 2022/2/17 2:28 下午
 */
@Slf4j
public class UtilTest {

    @Test
    public void testEncrypt() {
        final String key = "DB-Metadata is delicious";
        String encryptPass = SecureUtil.aes(key.getBytes()).encryptHex("888888");
        log.info("encryptPass: {}", encryptPass);
        log.info("decryptPass: {}", SecureUtil.aes(key.getBytes()).decryptStr(encryptPass));
    }

    @Test
    public void testSnowFlake () {
        System.out.println(SnowFlake.me().nextId());
    }
}
