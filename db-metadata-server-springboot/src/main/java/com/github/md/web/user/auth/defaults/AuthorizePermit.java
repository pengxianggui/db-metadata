package com.github.md.web.user.auth.defaults;

import cn.com.asoco.annotation.Authorize;
import com.github.md.web.user.AuthenticationManager;
import com.github.md.web.user.User;
import com.github.md.web.user.auth.IAuth;
import com.github.md.web.user.auth.MRPermit;
import com.github.md.web.user.role.MRRole;
import com.github.md.web.user.role.UserWithRolesWrapper;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 基于 {@link Authorize}的权限判定器。注意:
 * <pre>
 * 1. 若上下文的用户对象附带角色信息(即 instanceof {@link UserWithRolesWrapper}为true)，则取user对象内存中的角色、权限进行判断。否则，调用角色/权限服务获取实时数据
 * 2. 在对待{@link Authorize}时，优先判定其权限，即{@link Authorize#value()}，当为空数组时，再考虑判定{@link Authorize#role()}。
 * </pre>
 *
 * @author pengxg
 * @date 2022/2/21 3:00 下午
 */
@Slf4j
public class AuthorizePermit implements MRPermit<User, AnnotateApiResource> {

    @Override
    public boolean permit(User user, AnnotateApiResource mResource) {
        Authorize authorize = mResource.getAuthorize();
        if (authorize.justSign()) {
            return true;
        }

        if (authorize.value() != null && authorize.value().length >= 1) {
            return authPermit(user, authorize);
        }

        if (authorize.role() != null && authorize.role().length >= 1) {
            return rolePermit(user, authorize);
        }

        log.warn("接口({})权限控制声明注解异常, 用户访问视为无权限, 请考虑配置value或role。", mResource.mResourceId());
        return false;
    }

    private boolean rolePermit(User user, Authorize authorize) {
        List<String> ownRoles;
        if (user instanceof UserWithRolesWrapper) { // 优先使用内存缓存
            ownRoles = Arrays.asList(((UserWithRolesWrapper) user).roles()).stream().map(MRRole::code).collect(Collectors.toList());
        } else {
            List<MRRole> roles = AuthenticationManager.me().roleService().findByUser(user.userId());
            ownRoles = roles.stream().map(MRRole::code).collect(Collectors.toList());
        }

        List<String> needRoles = Arrays.asList(authorize.role());

        return (authorize.matchAllRole() ?
                needRoles.stream().allMatch(a -> ownRoles.contains(a))
                : needRoles.stream().anyMatch(a -> ownRoles.contains(a)));
    }

    private boolean authPermit(User user, Authorize authorize) {
        List<String> ownAuths;
        if (user instanceof UserWithRolesWrapper) { // 优先使用内存缓存
            ownAuths = Arrays.asList(((UserWithRolesWrapper) user).auths()).stream().map(IAuth::code).collect(Collectors.toList());
        } else {
            List<IAuth> auths = AuthenticationManager.me().authService().findByUser(user.userId());
            ownAuths = auths.stream().map(IAuth::code).collect(Collectors.toList());
        }

        List<String> needAuths = Arrays.asList(authorize.value());

        return (authorize.matchAllValue() ?
                needAuths.stream().allMatch(a -> ownAuths.contains(a))
                : needAuths.stream().anyMatch(a -> ownAuths.contains(a)));
    }
}
