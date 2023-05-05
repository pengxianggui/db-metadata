package com.github.md.web.user.auth;

import com.github.md.web.kit.AssertKit;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collections;
import java.util.List;

/**
 * <p> @Date : 2019/12/17 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
@Slf4j
public class MRAuthIntercept implements HandlerInterceptor {

    private List<MRAuthInterceptDoer> interceptDoers;

    public MRAuthIntercept(List<MRAuthInterceptDoer> interceptDoers) {
        this.interceptDoers = CollectionUtils.isEmpty(interceptDoers) ? Lists.newArrayList() : interceptDoers;
        Collections.sort(this.interceptDoers); // very important!
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        boolean flag = true;
        for (MRAuthInterceptDoer interceptDoer : interceptDoers) {
            if (!interceptDoer.support(request, response, handler)) {
                continue;
            }

            flag = interceptDoer.preAuth(request, response, handler);
            if (flag == false || interceptDoer.interruptAuthChain(request, response, handler)) {
                break;
            }
        }

        AssertKit.isTrue(flag, new UnauthorizedException("无权限访问!"));

        return true;
    }
}
