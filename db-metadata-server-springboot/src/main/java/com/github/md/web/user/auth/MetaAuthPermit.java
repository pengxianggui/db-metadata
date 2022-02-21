package com.github.md.web.user.auth;

import com.github.md.analysis.meta.AuthTypeRefered;
import com.github.md.web.ServiceManager;
import com.github.md.web.user.User;
import com.github.md.web.user.UserException;
import com.github.md.web.user.role.UserWithRolesWrapper;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;

/**
 * 接口权限判定器。可用于判定dbmeta内置接口，甚至共用接口，如/table/list，不同元对象是共用此接口的, 但不同元对象的权限编码却不同。
 * 此时可以使用此判定器,结合dbmeta内置表meta_auth进行判定。
 * <p>
 * 同样，因为此判定器直接内置注册了，因此可以直接在meta_auth表中配置接口路径和权限编码，即可实现对用户是否拥有接口权限的判定。这样做的话，
 * 你就无需在接口方法中"打标记"(权限标注注解)了。
 *
 * @author pengxg
 * @date 2021/10/15 9:49 上午
 * @see MetaAuthResource
 * @see AuthTypeRefered
 */
@Slf4j
class MetaAuthPermit implements MRPermit<User, MetaAuthResource> {

    @Override
    public boolean permit(User user, MetaAuthResource mResource) {
        if (!mResource.needPermit()) {
            return true;
        }

        if (Objects.isNull(user)) {
            throw new UserException("无用户信息!");
        }

        if (!(user instanceof UserWithRolesWrapper)) {
            log.error("从上下文获取的用户对象不是{}类型，无法判断用户拥有的权限。默认为无权限", UserWithRolesWrapper.class.toString());
            throw new MRException("无法获取用户权限，请确保用户对象是一个%s实例", UserWithRolesWrapper.class.toString());
        }
        return ((UserWithRolesWrapper) user).hasAuth(mResource);
    }
}
