package com.github.md.web.event;

import com.github.md.web.event.user.LoginListener;
import com.github.md.web.event.user.LogoutListener;
import com.github.md.web.event.user.UserStatusChangeMessage;
import com.google.common.collect.Lists;
import lombok.Getter;

import java.util.List;

/**
 * 可扩展的监听器注册类。简化和统一配置
 *
 * @author pengxg
 * @date 2022/10/31 6:00 下午
 */
public class ExtensibleListenerRegistry {
    /**
     * 表单事件监听
     */
    @Getter
    private final List<ExtensibleListener<FormMessage>> formListeners = Lists.newLinkedList();

    /**
     * 用户状态变动监听
     */
    @Getter
    private final List<ExtensibleListener<UserStatusChangeMessage>> userStatusChangeListeners = Lists.newLinkedList();

    public ExtensibleListenerRegistry() {
        this.userStatusChangeListeners.add(new LoginListener());
        this.userStatusChangeListeners.add(new LogoutListener());
    }

    /**
     * 配置表单监听器
     *
     * @param extensibleListener
     */
    public void configFormListeners(ExtensibleListener<FormMessage>... extensibleListener) {
        for (ExtensibleListener listener : extensibleListener) {
            this.formListeners.add(listener);
        }
    }

    /**
     * 配置用户状态变更监听器
     *
     * @param extensibleListeners
     */
    public void configUserStatusChangeListeners(ExtensibleListener<UserStatusChangeMessage>... extensibleListeners) {
        for (ExtensibleListener<UserStatusChangeMessage> extensibleListener : extensibleListeners) {
            this.userStatusChangeListeners.add(extensibleListener);
        }
    }
}
