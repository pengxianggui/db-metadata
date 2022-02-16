package com.github.md.web.user.role;

import com.github.md.web.user.auth.IAuth;

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

    public static MRRole createRole(String code, String name, IAuth... auths) {
        DefaultRole role = new DefaultRole(code, name);
        for (IAuth auth : auths) {
            role.addPermission(auth);
        }
        return role;
    }
}
