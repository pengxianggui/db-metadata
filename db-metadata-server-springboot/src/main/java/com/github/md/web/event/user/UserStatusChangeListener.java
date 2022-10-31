package com.github.md.web.event.user;

import com.github.md.web.event.ExtensibleListenerManager;
import com.github.md.web.event.MetaEventListener;
import com.google.common.eventbus.Subscribe;

/**
 * 用户状态变更监听器
 * @author pengxg
 * @date 2022/10/31 3:21 下午
 */
public class UserStatusChangeListener implements MetaEventListener<UserStatusChangeMessage> {

    @Subscribe
    @Override
    public void handler(UserStatusChangeMessage eventMessage) {
        ExtensibleListenerManager.me().getRegistry().getUserStatusChangeListeners().forEach(listener -> {
            if (listener.isHit(eventMessage)) {
                listener.handler(eventMessage);
            }
        });
    }
}
