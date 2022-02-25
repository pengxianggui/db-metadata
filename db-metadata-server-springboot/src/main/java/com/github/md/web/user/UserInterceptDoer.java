package com.github.md.web.user;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author pengxg
 * @date 2021/10/18 4:44 下午
 */
public interface UserInterceptDoer {

    boolean preCertify(HttpServletRequest request, HttpServletResponse response, Object handler);

}
