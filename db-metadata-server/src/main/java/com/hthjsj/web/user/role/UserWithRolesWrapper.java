package com.hthjsj.web.user.role;

import com.hthjsj.web.user.User;
import com.hthjsj.web.user.auth.MRRole;

/**
 * <p> @Date : 2020/8/12 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
public interface UserWithRolesWrapper extends User {

    MRRole[] roles();

    boolean hasRole(String nameOrCode);
}
