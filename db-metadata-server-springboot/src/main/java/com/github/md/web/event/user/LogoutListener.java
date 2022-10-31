package com.github.md.web.event.user;

import com.github.md.web.event.ExtensibleListener;
import lombok.extern.slf4j.Slf4j;

/**
 * 登出监听
 * @author pengxg
 * @date 2022/10/31 3:53 下午
 */
@Slf4j
public class LogoutListener implements ExtensibleListener<UserStatusChangeMessage> {
    @Override
    public boolean isHit(UserStatusChangeMessage userStatusChangeMessage) {
        return userStatusChangeMessage.getType() == UserStatusChangeMessage.Type.LOGOUT;
    }

    @Override
    public void handler(UserStatusChangeMessage userStatusChangeMessage) {
        log.debug("user logout success! userId: {}", userStatusChangeMessage.getUserId());
    }
}
