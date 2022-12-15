package com.github.md.web.user;

/**
 * token生成器
 *
 * @author pengxg
 * @date 2022/4/10 2:35 下午
 */
public interface TokenGenerator<U extends User> {

    /**
     * 由user生成token。若覆盖此方法，务必覆盖{@link #parse(String)}
     *
     * @param user
     * @return 返回token
     */
    default String generate(U user) {
        return user.userId();
    }

    /**
     * 由token解析出userId。若覆盖此方法，务必覆盖{@link #generate(User)}
     *
     * @param token
     * @return
     */
    default String parse(String token) {
        return token;
    }
}
