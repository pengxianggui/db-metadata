package com.github.md.web.user.role;

import com.github.md.web.user.User;
import com.github.md.web.user.auth.IAuth;
import com.google.common.collect.Lists;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 携带角色、权限的用户包装
 * <p> @Date : 2020/8/12 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
public interface UserWithRolesWrapper extends User {

    String avatar();

    MRRole[] roles();

    /**
     * @param nameOrCode 角色名或编码
     * @return 拥有此角色或编码则返回true
     */
    default boolean hasRole(String nameOrCode) {
        for (MRRole role : roles()) {
            if (role.name().equalsIgnoreCase(nameOrCode.toLowerCase()) || role.code().equalsIgnoreCase(nameOrCode.toLowerCase())) {
                return true;
            }
        }
        return false;
    }

    default IAuth[] auths() {
        MRRole[] roles = roles();
        if (roles == null || roles.length == 0) {
            return new IAuth[0];
        }

        List<IAuth> auths = Lists.newArrayList();
        for (MRRole role : roles) {
            auths.addAll(Arrays.asList(role.auths()));
        }
        return auths.toArray(new IAuth[auths.size()]);
    }

    /**
     * 全部拥有，则返回true。 匹配权限编码
     *
     * @param authCodes 权限编码
     * @return 拥有所有入参的权限则返回true
     */
    default boolean hasAuth(String... authCodes) {
        IAuth[] all = auths();
        Set<String> allCodes = Arrays.stream(all).map(IAuth::code).collect(Collectors.toSet());

        for (String code : authCodes) {
            if (!allCodes.contains(code)) {
                return false;
            }
        }
        return true;
    }

    /**
     * 匹配权限编码
     *
     * @param auths 权限数组
     * @return 全部拥有，则返回true
     */
    default boolean hasAuth(IAuth... auths) {
        // 依据IAuth.equals
//        Set<IAuth> all = Arrays.stream(auths()).collect(Collectors.toSet());
//        for (IAuth auth : auths) {
//            if (!all.contains(auth)) {
//                return false;
//            }
//        }
//        return true;

        // 依据权限编码
        List<String> codes = Arrays.stream(auths).map(IAuth::code).collect(Collectors.toList());
        return hasAuth(codes.toArray(new String[codes.size()]));
    }

    @Override
    default Map toKv() {
        Map map = new HashMap();
        map.put("id", userId());
        map.put("username", userName());
        map.put("avatar", avatar());
        map.put("roles", Arrays.stream(roles()).map(MRRole::code).collect(Collectors.toSet()));
        map.put("auths", Arrays.stream(auths()).map(IAuth::code).collect(Collectors.toSet()));
        map.put("attrs", attrs());
        return map;
    }
}
