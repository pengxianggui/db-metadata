package com.github.md.web.event.user;

import com.github.md.web.event.EventMessage;
import lombok.Getter;

/**
 * 用户状态变动消息。用户状态变动，触发此消息
 *
 * @author pengxg
 * @date 2022/10/31 3:08 下午
 */
public class UserStatusChangeMessage implements EventMessage {
    @Getter
    private String userId;
    @Getter
    private Type type;

    public UserStatusChangeMessage(String userId, Type type) {
        this.userId = userId;
        this.type = type;
    }

    public static UserStatusChangeMessage create(String userId, Type type) {
        return new UserStatusChangeMessage(userId, type);
    }

    /**
     * 用户变动类型
     */
    public enum Type {
        /**
         * 用户登录
         */
        LOGIN,
        /**
         * 用户登出
         */
        LOGOUT,
        /**
         * 用户角色变动
         */
        ROLE,
    }
}
