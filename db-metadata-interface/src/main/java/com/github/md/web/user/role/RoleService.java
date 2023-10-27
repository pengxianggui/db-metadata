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
     * @return 所有角色
     */
    List<MRRole> findAll();

    /**
     * 获取用户拥有的角色
     *
     * @param userId 用户id
     * @return 此用户拥有的角色
     */
    List<MRRole> findByUser(String userId);

    /**
     * 为角色绑定权限。必须幂等。
     *
     * @param roleId  角色id
     * @param authIds 权限id数组
     * @return 绑定是否成功
     */
    boolean bindAuthsForRole(String roleId, String... authIds);

    MRRole findOne(String id);
}
