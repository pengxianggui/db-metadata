package com.github.md.web.user.auth.meta;

import com.github.md.web.user.User;
import com.github.md.web.user.UserException;
import com.github.md.web.user.UserManager;
import com.github.md.web.user.auth.IAuth;
import com.github.md.web.user.auth.MRException;
import com.github.md.web.user.auth.MRPermit;
import com.github.md.web.user.role.UserWithRolesWrapper;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
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
 * @see MetaAccess
 */
@Slf4j
public class MetaAuthPermit implements MRPermit<User, MetaAuthResource> {

    @Override
    public boolean permit(User user, MetaAuthResource mResource) {
        if (Objects.isNull(user)) {
            log.debug("无用户信息! 视为无权限访问.");
            return false;
        }

        if (user instanceof UserWithRolesWrapper) {
            return ((UserWithRolesWrapper) user).hasAuth(mResource);
        }

        List<IAuth> auths = UserManager.me().getMetaAuthService().findByUser(user.userId());
        return auths.contains(mResource);
    }
}
