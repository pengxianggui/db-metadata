package com.github.md.web.query.dynamic;

import com.github.md.web.user.User;
import com.github.md.web.user.AuthenticationManager;

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
        User user = AuthenticationManager.me().getUser(request);
        return user;
    }
}
