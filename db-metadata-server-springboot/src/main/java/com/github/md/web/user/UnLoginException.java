package com.github.md.web.user;

import com.github.md.web.ex.WebException;

/**
 * @author pengxg
 * @date 2022/2/21 4:09 下午
 */
public class UnLoginException extends WebException {
    
    public UnLoginException(String message) {
        super(message, 401);
    }
}
