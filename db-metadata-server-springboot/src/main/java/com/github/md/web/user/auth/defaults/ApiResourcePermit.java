package com.github.md.web.user.auth.defaults;

import com.github.md.web.user.AuthenticationManager;
import com.github.md.web.user.User;
import com.github.md.web.user.auth.IAuth;
import com.github.md.web.user.auth.MRPermit;
import com.github.md.web.user.auth.annotations.ApiType;
import com.github.md.web.user.role.MRRole;
import com.github.md.web.user.role.UserWithRolesWrapper;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 接口权限判定器。可用于判定接口，甚至dbmeta内置的共用接口(如/table/list，不同元对象是共用此接口的, 但不同元对象的权限编码却不同) 的访问权限。
 * <p>
 * 同样，因为此判定器直接内置注册了，因此可以直接在meta_api_resource表中配置接口类型、路径和鉴权描述，即可实现对用户是否拥有接口权限的判定。这样做的话，
 * 你就无需在接口方法中"打标记"(权限标注注解)了。
 *
 * @author pengxg
 * @date 2021/10/15 9:49 上午
 * @see MetaApiResource
 * @see ApiType
 */
@Slf4j
public class ApiResourcePermit implements MRPermit<User, MetaApiResource> {

    @Override
    public boolean permit(User user, MetaApiResource mResource) {
        if (mResource.justSign()) { // 登录即可访问
            return true;
        }

        List<MRRole> hasRoles;
        List<IAuth> hasAuths;

        if (user instanceof UserWithRolesWrapper) { // 若缓存的用户对象包含角色权限，则就用缓存的
            hasRoles = Arrays.asList(((UserWithRolesWrapper) user).roles());
            hasAuths = Arrays.asList(((UserWithRolesWrapper) user).auths());
        } else {
            hasRoles = AuthenticationManager.me().getRoleService().findByUser(user.userId());
            hasAuths = AuthenticationManager.me().getAuthService().findByUser(user.userId());
        }

        if (CollectionUtils.isEmpty(hasRoles)) hasRoles = Lists.newArrayList();
        if (CollectionUtils.isEmpty(hasAuths)) hasAuths = Lists.newArrayList();

        List<String> hasRoleCodes = hasRoles.stream().map(MRRole::code).collect(Collectors.toList());
        List<String> hasAuthCodes = hasAuths.stream().map(IAuth::code).collect(Collectors.toList());

        return mResource.permitBy() == MetaApiResource.PermitBy.auth
                ? _permit(hasAuthCodes, mResource.needAuth(), mResource.authMatchMode())
                : _permit(hasRoleCodes, mResource.needRole(), mResource.roleMatchMode());
    }

    /**
     * 通过(角色/权限)编码判定。若需要的(角色/权限)编码为空，则视为无需角色/权限也能访问, 返回true
     *
     * @param hasCodes  拥有的(角色/权限)编码
     * @param needCodes 需要具备的角色/权限编码
     * @param matchMode 匹配模式
     * @return
     */
    private boolean _permit(List<String> hasCodes, List<String> needCodes, MetaApiResource.MatchMode matchMode) {
        if (CollectionUtils.isEmpty(needCodes)) {
            return true;
        }

        return matchMode == MetaApiResource.MatchMode.any
                ? needCodes.stream().anyMatch(code -> hasCodes.contains(code))
                : needCodes.stream().allMatch(code -> hasCodes.contains(code));
    }

}
