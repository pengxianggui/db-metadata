package com.github.md.web.user;

/**
 * token生成器
 *
 * @author pengxg
 * @date 2022/4/10 2:35 下午
 */
public interface TokenGenerator<U extends User> {

    /**
     * 由user生成token, 此方法必须保证幂等。
     *
     * @param user
     * @return
     */
    String generate(U user);
}
