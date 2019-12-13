package com.hthjsj.web.user;

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

    void updateById(U user);
}
