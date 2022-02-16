package com.github.md.web.user.role;

import com.github.md.web.user.auth.IAuth;

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
    IAuth[] auths();

    /**
     * 权限判定
     *
     * @param auth
     *
     * @return
     */
    boolean hasAuth(IAuth auth);
}
