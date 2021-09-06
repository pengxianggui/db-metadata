package com.hthjsj.web.user.auth;

/**
 * 角色抽象
 * <p> @Date : 2020/8/12 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
public interface MRRole {

    /**
     * 角色的编码
     *
     * @return
     */
    String code();

    /**
     * 角色的名称
     *
     * @return
     */
    String name();

    /**
     * 权限列表
     *
     * @return
     */
    Permission[] permissions();

    /**
     * 权限判定
     *
     * @param permission
     *
     * @return
     */
    boolean hasPermission(Permission permission);
}
