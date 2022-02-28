package com.github.md.web.user;

import cn.com.asoco.util.AssertUtil;
import com.github.md.web.WebException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <p> @Date : 2019/10/18 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
@Slf4j
public class UserIntercept implements HandlerInterceptor {

    private UserInterceptDoer userInterceptDoer;

    public UserIntercept(UserInterceptDoer userInterceptDoer) {
        AssertUtil.isTrue(userInterceptDoer != null, new WebException("未配置用户认证执行器：configUserInterceptDoer"));
        this.userInterceptDoer = userInterceptDoer;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        return this.userInterceptDoer.preCertify(request, response, handler);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        UserThreadLocal.removeUser();
    }
}
