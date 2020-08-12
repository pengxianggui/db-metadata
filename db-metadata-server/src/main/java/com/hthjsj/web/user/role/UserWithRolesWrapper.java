package com.hthjsj.web.user.role;

import com.hthjsj.web.auth.MRRole;
import com.hthjsj.web.user.User;

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
