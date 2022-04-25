package com.github.md.web;

import cn.com.asoco.exception.UserInVisibleException;

/**
 * @author pengxg
 * @date 2022/4/25 9:59 上午
 */
public class DbMetaConfigException extends UserInVisibleException {
    public DbMetaConfigException(String msg) {
        super(msg);
    }
}
