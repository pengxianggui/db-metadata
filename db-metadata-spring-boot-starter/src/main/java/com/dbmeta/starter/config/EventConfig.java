package com.dbmeta.starter.config;

import com.hthjsj.web.ExtJFinalConfig;
import com.hthjsj.web.jms.EventKit;
import com.hthjsj.web.jms.ExtensibleListenerManager;
import com.hthjsj.web.jms.FormListener;
import com.hthjsj.web.jms.TestFormExtensibleListener;

/**
 * @author pengxg
 * 2021/1/27 2:27 下午
 */
public class EventConfig extends ExtJFinalConfig {
    @Override
    public void onStart() {
        EventKit.init(false, "db-event-bus");
        EventKit.register(new FormListener());
        ExtensibleListenerManager.me().addFormListeners(new TestFormExtensibleListener());
    }
}
