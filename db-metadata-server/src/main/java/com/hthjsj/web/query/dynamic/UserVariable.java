package com.hthjsj.web.query.dynamic;

import com.hthjsj.web.user.User;
import com.hthjsj.web.user.UserManager;

import javax.servlet.http.HttpServletRequest;

/**
 * <p> @Date : 2020/8/12 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
public class UserVariable implements VariableDefinition {

    private final HttpServletRequest request;

    public UserVariable(HttpServletRequest request) {
        this.request = request;
    }

    @Override
    public String name() {
        return "user";
    }

    @Override
    public Object init() {
        User user = UserManager.me().getUser(request);
        return user;
    }
}
