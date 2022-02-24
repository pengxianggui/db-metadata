package com.github.md.web.ex;

import cn.com.asoco.exception.AsocoBusinessException;

/**
 * @author pengxg
 * @date 2022/1/30 9:33 上午
 */
public class OprNotSupportException extends AsocoBusinessException {
    public OprNotSupportException(String msg) {
        super(msg, 5001);
    }

    public OprNotSupportException(int code, String... params) {
        super(code, params);
    }
}
