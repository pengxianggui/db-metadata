package com.github.md.web.utils;

import cn.hutool.crypto.SecureUtil;
import com.github.md.analysis.db.SnowFlake;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

/**
 * @author pengxg
 * @date 2022/2/17 2:28 下午
 */
@Slf4j
public class UtilTest {
    final static String KEY = "DB-Metadata is delicious";

    @Test
    public void testEncrypt() {
        String encryptPass = SecureUtil.aes(KEY.getBytes()).encryptHex("2m7lcQUK2X6AbhurgPyl7A==");
        log.info("encryptPass: {}", encryptPass);
        log.info("decryptPass: {}", SecureUtil.aes(KEY.getBytes()).decryptStr(encryptPass));
    }

    @Test
    public void testDecrypt() {
        final String encryptPass = "424cee8178299a325ca27f3de89b6a9c";
        String clearPass = SecureUtil.aes(KEY.getBytes()).decryptStr(encryptPass);
        log.info(clearPass);
    }

    @Test
    public void testSnowFlake () {
        System.out.println(SnowFlake.me().nextId());
    }
}
