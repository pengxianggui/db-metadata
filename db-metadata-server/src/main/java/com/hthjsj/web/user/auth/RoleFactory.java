package com.hthjsj.web.user.auth;

/**
 * <p> @Date : 2020/8/12 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
public class RoleFactory {

    public static MRRole createAdmin() {
        DefaultRole mrRole = new DefaultRole("admin", "管理员");
        mrRole.addPermission(StaticPermission.of("site-admin-login", "门户登录权限"));
        return mrRole;
    }

    public static MRRole createNormalRole() {
        DefaultRole role = new DefaultRole("normal", "普通用户");
        return role;
    }
}
