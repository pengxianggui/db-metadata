package com.github.md.web.user;

import com.github.md.web.kit.AssertKit;
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
        AssertKit.isTrue(userInterceptDoer != null, "未配置用户认证执行器：configUserInterceptDoer");
        this.userInterceptDoer = userInterceptDoer;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        User user = this.userInterceptDoer.preCertify(request, response, handler);
        if (user != null) {
            UserThreadLocal.setUser(user);
        }

        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        UserThreadLocal.removeUser();
    }
}
