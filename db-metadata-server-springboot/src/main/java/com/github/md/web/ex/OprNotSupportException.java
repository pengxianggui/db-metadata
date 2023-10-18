package com.github.md.web.ex;

import cn.hutool.core.util.StrUtil;

/**
 * @author pengxg
 * @date 2022/1/30 9:33 上午
 */
public class OprNotSupportException extends WebException {
    public OprNotSupportException(String msg, Object... args) {
        super(StrUtil.format(msg, args), 5001);
    }

    public OprNotSupportException(int code, String... params) {
        super("操作不允许", code);
    }
}
