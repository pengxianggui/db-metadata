package com.dbmeta.starter.config;

import com.hthjsj.web.ExtJFinalConfig;
import com.hthjsj.web.kit.Dicts;

/**
 * @author pengxg
 * 2021/1/27 2:26 下午
 */
public class DictConfig extends ExtJFinalConfig {
    @Override
    public void onStart() {
        //dictionary register
        Dicts.me().init();
    }
}
