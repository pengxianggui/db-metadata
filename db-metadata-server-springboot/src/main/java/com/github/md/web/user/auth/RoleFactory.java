package com.github.md.web.user.auth;

/**
 * <pre>
 * 角色构建工厂:
 * 使用默认DefaultRole作为实现,初始化后不能显示得增加权限;
 * </pre>
 * <p> @Date : 2020/8/12 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
public class RoleFactory {

    public static MRRole createRole(String code, String name, Permission... permissions) {
        DefaultRole role = new DefaultRole(code, name);
        for (Permission permission : permissions) {
            role.addPermission(permission);
        }
        return role;
    }
}
