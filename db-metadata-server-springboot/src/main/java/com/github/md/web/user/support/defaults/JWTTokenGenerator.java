package com.github.md.web.user.support.defaults;

import cn.hutool.jwt.JWT;
import cn.hutool.jwt.JWTHeader;
import cn.hutool.jwt.JWTUtil;
import com.github.md.web.user.TokenGenerator;
import com.github.md.web.user.role.UserWithRolesWrapper;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * jwt Token生成器，对user使用jwt生成token。注意: 生成的token只包含userId信息，可使用parse进行解析
 *
 * @author pengxg
 * @date 2022/4/10 2:39 下午
 */
public class JWTTokenGenerator implements TokenGenerator<UserWithRolesWrapper> {

    /**
     * jwt生成token时的payload, 取决于{@link UserWithRolesWrapper#toKv()}
     *
     * @param user
     * @return
     */
    @Override
    public String generate(UserWithRolesWrapper user) {
        Map<String, Object> payload = new HashMap<>();
        payload.put("userId", user.userId());
        payload.put("timestamp", new Date().getTime()); // 保证每次生成的token不一样
        return JWTUtil.createToken(payload, user.userId().getBytes());
    }

    @Override
    public String parse(String token) {
        JWT jwt = JWTUtil.parseToken(token);
        jwt.getHeader(JWTHeader.TYPE);
        return (String) jwt.getPayload("userId");
    }
}
