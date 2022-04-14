package com.github.md.web.user;

import java.util.List;

/**
 * <p> @Date : 2019/12/13 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
public interface UserService<U extends User> {

    List<U> findAll();

    U findById(Object idValue);

    boolean updateById(U user);

    /**
     * 幂等
     *
     * @param userId
     * @param roleIds
     * @return
     */
    boolean bindRolesForUser(String userId, String... roleIds);

    /**
     * 密码重置
     *
     * @return
     */
    boolean resetPass(Object userId);

    /**
     * 用户元对象编码
     *
     * @return
     */
    String userObjectCode();

    /**
     * 为用户设置密码
     * @param userId 用户id
     * @param password 明文密码
     * @return
     */
    boolean setPass(Object userId, String password);
}
