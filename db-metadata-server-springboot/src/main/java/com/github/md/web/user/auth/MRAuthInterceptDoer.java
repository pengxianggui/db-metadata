package com.github.md.web.user.auth;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 鉴权者，用于自定义鉴权逻辑。客户端实现此类，注册bean到spring，则会生效。参考 {@link MRAuthIntercept}
 *
 * @author pengxg
 * @date 2021/10/16 8:22 上午
 */
public interface MRAuthInterceptDoer extends Comparable<MRAuthInterceptDoer> {

    /**
     * 排序。决定鉴权拦截器的先后顺序。
     *
     * @return 返回值越小，优先级越高
     */
    default int order() {
        return Integer.MAX_VALUE;
    }

    boolean preAuth(HttpServletRequest request, HttpServletResponse response, Object handler);

    @Override
    default int compareTo(MRAuthInterceptDoer o) {
        return this.order() - o.order();
    }
}
