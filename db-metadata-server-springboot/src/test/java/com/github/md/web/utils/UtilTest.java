package com.github.md.web.utils;

import cn.hutool.crypto.SecureUtil;
import cn.hutool.jwt.JWT;
import cn.hutool.jwt.JWTHeader;
import cn.hutool.jwt.JWTUtil;
import com.github.md.analysis.db.SnowFlake;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

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
    public void testSnowFlake() {
        System.out.println(SnowFlake.me().nextId());
    }


    @Test
    public void testJwt() {
        Map<String, Object> payload = new HashMap<>();
        String userId = "1234567890";
        payload.put("userId", userId);
        payload.put("timestamp", new Date().getTime());
        final String token = JWTUtil.createToken(payload, userId.getBytes());
        log.info("token: {}", token);

        JWT jwt = JWTUtil.parseToken(token);
        jwt.getHeader(JWTHeader.TYPE);
        String expectUserId = (String) jwt.getPayload("userId");
        log.info("userId: {}, expectUserId: {}", userId, expectUserId);
        Assertions.assertEquals(userId, expectUserId);
    }
}
