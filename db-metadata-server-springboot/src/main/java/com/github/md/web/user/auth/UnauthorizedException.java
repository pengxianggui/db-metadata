package com.github.md.web.user.auth;

import com.github.md.web.ex.WebException;

/**
 * @author pengxg
 * @date 2022/2/21 4:08 下午
 */
public class UnauthorizedException extends WebException {

    public UnauthorizedException(String message) {
        super(message, 403);
    }
}
