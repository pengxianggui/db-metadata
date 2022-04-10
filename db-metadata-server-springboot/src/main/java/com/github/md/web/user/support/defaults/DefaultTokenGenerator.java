package com.github.md.web.user.support.defaults;

import cn.hutool.crypto.SecureUtil;
import com.github.md.web.user.TokenGenerator;
import com.github.md.web.user.role.DefaultUserWithRoles;

/**
 * 默认的Token生成仅对用户id进行md5加密。
 *
 * @author pengxg
 * @date 2022/4/10 2:39 下午
 */
public class DefaultTokenGenerator implements TokenGenerator<DefaultUserWithRoles> {

    @Override
    public String generate(DefaultUserWithRoles user) {
        return SecureUtil.md5(user.userId());
    }
}
