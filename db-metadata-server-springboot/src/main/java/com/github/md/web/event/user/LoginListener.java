package com.github.md.web.event.user;

import com.github.md.web.event.ExtensibleListener;
import lombok.extern.slf4j.Slf4j;

/**
 * 登录监听
 * @author pengxg
 * @date 2022/10/31 3:52 下午
 */
@Slf4j
public class LoginListener implements ExtensibleListener<UserStatusChangeMessage> {

    @Override
    public boolean isHit(UserStatusChangeMessage userStatusChangeMessage) {
        return userStatusChangeMessage.getType() == UserStatusChangeMessage.Type.LOGIN;
    }

    @Override
    public void handler(UserStatusChangeMessage userStatusChangeMessage) {
        log.debug("user login success! userId: {}", userStatusChangeMessage.getUserId());
    }
}
