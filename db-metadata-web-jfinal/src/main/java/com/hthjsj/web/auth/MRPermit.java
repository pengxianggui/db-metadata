package com.hthjsj.web.auth;

import com.hthjsj.web.user.User;

/**
 * <p> @Date : 2019/12/16 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
public interface MRPermit {

    boolean permit(User user, MResource mResource);
}
