package com.github.md.web.utils;

import cn.hutool.crypto.SecureUtil;
import cn.hutool.jwt.JWT;
import cn.hutool.jwt.JWTHeader;
import cn.hutool.jwt.JWTUtil;
import com.github.md.analysis.db.SnowFlake;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
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

    @Test
    public void testPathParse() {
//        final String url = "http://localhost/file/preview?path=t_employee%5Cattachement%5C%E7%81%AB%E6%98%9F%E4%BA%BA%E5%85%A8%E5%9F%9F%E8%90%A5%E9%94%80%E9%A1%B9%E7%9B%AE-%E5%AD%97%E6%AE%B5%E6%B8%85%E5%8D%95-V1.0%281%29_20230720_11_24_26_793.xlsx&mode=local";
//        final String url1 = "http://localhost:80/xxx/yyy/zzz/sss";
//        UrlPath urlPath = UrlPath.of(url, Charset.defaultCharset());
//        log.info("urlPath: {}", urlPath);
//
//        UrlQuery urlQuery = UrlQuery.of(url, Charset.defaultCharset());
//        log.info("pathParam: {}, mode:{}, fileName: {}", urlQuery.get("path"), urlQuery.get("mode"), new File((String) urlQuery.get("path")).getName());
//
//        log.info("fileName: {}", new File(url1).getName());

//        UrlBuilder urlBuilder = UrlBuilder.of(url);
//        log.info("path: {}, params:{}", urlBuilder.getPath(), urlBuilder.getQuery().get("path"));

        final String url = "t_employee/xxx/yyy/zzz.zip";
        String finalUrl = Paths.get(null, url).toString();
        log.info("url: {}", finalUrl);
    }

    @Test
    public void testGetFileName() throws IOException {
        final String url = "http://localhost:80/xxx/yyy/zzz/sss.zip";
        log.info("fileName:{}", new File(url).getName());

        File file = File.createTempFile(new File(url).getName(), null);
        log.info("fileName:{}, absoluteName:{}", file.getName(), file.getAbsoluteFile());
    }
}
