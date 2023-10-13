package com.github.md.web;

import com.github.md.web.ex.WebException;

/**
 * dbmeta配置异常
 *
 * @author pengxg
 * @date 2022/4/25 9:59 上午
 */
public class DbMetaConfigException extends WebException {
    public DbMetaConfigException(String msg) {
        super(msg);
    }
}
