package com.github.md.web.user;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author pengxg
 * @date 2021/10/18 4:44 下午
 */
public interface UserInterceptDoer {

    boolean preCertify(HttpServletRequest request, HttpServletResponse response, Object handler);

    /**
     * 游客。当用户不存在时，默认提供一个游客用户
     *
     * @param request
     * @return
     */
    default User getDefaultVisitor(HttpServletRequest request) {
        return null;
    }
}
