package com.github.md.web.user.role;

import com.github.md.web.user.auth.IAuth;

import java.util.Map;

/**
 * 角色抽象
 * <p> @Date : 2020/8/12 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
public interface MRRole {

    String id();

    /**
     * 角色的编码
     *
     * @return 角色编码
     */
    String code();

    /**
     * 角色的名称
     *
     * @return 角色名
     */
    String name();

    /**
     * 权限列表
     *
     * @return 权限数组
     */
    IAuth[] auths();

    /**
     * 权限判定
     *
     * @param auth 待判定的权限
     * @return 是否拥有此权限
     */
    boolean hasAuth(IAuth auth);

    Map toKv();
}
