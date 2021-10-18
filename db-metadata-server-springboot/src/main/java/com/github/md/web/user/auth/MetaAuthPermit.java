package com.github.md.web.user.auth;

import com.github.md.web.ServiceManager;
import com.github.md.web.user.User;
import com.github.md.web.user.role.UserWithRolesWrapper;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;

/**
 * 权限鉴别器
 *
 * @author pengxg
 * @date 2021/10/15 9:49 上午
 */
@Slf4j
class MetaAuthPermit implements MRPermit<User, MetaAuthResource> {
    private MetaAuthService metaAuthService = ServiceManager.authService();

    @Override
    public boolean permit(User user, MetaAuthResource mResource) {
        if (!mResource.needPermit()) {
            return true;
        }

        String authCode = metaAuthService.findAuthCode(mResource.getType(), mResource.getMetaCode(), mResource.getUri());
        if (Objects.isNull(authCode)) {
            log.warn("无针对此资源的权限配置: type:{}, meta_code:{},uri:{}, 将裸奔",
                    mResource.getType(), mResource.getMetaCode(), mResource.getUri());
            return true;
        }

        if (!(user instanceof UserWithRolesWrapper)) {
            log.error("从上下文获取的用户对象不是{}类型，无法判断用户拥有的权限。默认为无权限", UserWithRolesWrapper.class.toString());
            throw new MRException("无法获取用户权限，请确保用户对象是一个%s实例", UserWithRolesWrapper.class.toString());
        }
        return ((UserWithRolesWrapper) user).hasPermission(authCode);
    }
}
