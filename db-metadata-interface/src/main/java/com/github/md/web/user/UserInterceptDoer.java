package com.github.md.web.user;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author pengxg
 * @date 2021/10/18 4:44 下午
 */
public interface UserInterceptDoer {

    /**
     * 用户预识别：从请求中解析用户并返回。
     *
     * @param request
     * @param response
     * @param handler
     * @return 若返回null， 则视为未识别到用户。但注意, 即使未识别用户，系统也会放行，因为有些资源是无需用户登录的，因此拦截并阻止访问，是
     * 权限执行器的任务。
     */
    User preCertify(HttpServletRequest request, HttpServletResponse response, Object handler);

}
