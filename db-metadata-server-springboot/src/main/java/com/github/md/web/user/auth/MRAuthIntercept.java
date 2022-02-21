package com.github.md.web.user.auth;

import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collection;

/**
 * <p> @Date : 2019/12/17 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
@Slf4j
public class MRAuthIntercept implements HandlerInterceptor {

    private Collection<MRAuthInterceptDoer> interceptDoers;

    public MRAuthIntercept(Collection<MRAuthInterceptDoer> interceptDoers) {
        this.interceptDoers = CollectionUtils.isEmpty(interceptDoers) ? Lists.newArrayList() : interceptDoers;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        boolean flag = interceptDoers.stream().allMatch(doer -> doer.preAuth(request, response, handler));
        if (!flag) {
            throw new UnauthorizedException("无权限访问!");
        }
        return true;
    }
}
