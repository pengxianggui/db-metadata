package com.github.md.web.user.auth;

import java.util.List;

/**
 * @author pengxg
 * @date 2022/2/21 1:01 下午
 */
public interface AuthService {
    List<IAuth> findAll();

    List<IAuth> findByRole(String roleId);

    List<IAuth> findByUser(String userId);

    IAuth findOne(String id);
}
