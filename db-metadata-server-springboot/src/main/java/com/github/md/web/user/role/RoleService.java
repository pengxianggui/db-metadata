package com.github.md.web.user.role;

import java.util.List;

/**
 * 角色服务
 *
 * @author pengxg
 * @date 2022/2/21 12:58 下午
 */
public interface RoleService {
    /**
     * 返回所有角色
     *
     * @return
     */
    List<MRRole> findAll();

    /**
     * 获取用户拥有的角色
     *
     * @param userId
     * @return
     */
    List<MRRole> findByUser(String userId);

    /**
     * 为角色绑定权限。必须幂等。
     *
     * @param roleId  角色id
     * @param authIds 权限id数组
     * @return
     */
    boolean bindAuthsForRole(String roleId, String... authIds);

    MRRole findOne(String id);
}
