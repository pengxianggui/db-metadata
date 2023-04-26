package com.github.md.web.user.auth;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 鉴权执行者，用于自定义鉴权逻辑。客户端实现此类，注册bean到spring，则会生效。参考 {@link MRAuthIntercept}
 *
 * @author pengxg
 * @date 2021/10/16 8:22 上午
 */
public interface MRAuthInterceptDoer extends Comparable<MRAuthInterceptDoer> {

    /**
     * 排序。决定鉴权拦截器的先后顺序。
     *
     * @return 返回值越小，优先级越高, 默认最低优先级
     */
    default int order() {
        return Integer.MAX_VALUE;
    }

    /**
     * 判断是否支持。对于返回false的，则拦截器不生效
     *
     * @param request
     * @param response
     * @param handler
     * @return
     */
    default boolean support(HttpServletRequest request, HttpServletResponse response, Object handler) {
        return true;
    }

    /**
     * 鉴权执行
     *
     * @param request
     * @param response
     * @param handler
     * @return
     */
    boolean preAuth(HttpServletRequest request, HttpServletResponse response, Object handler);

    /**
     * 是否中断后续鉴权链。若返回true，则 优先级{@link #order()} 小于当前鉴权执行器的 则不会执行。
     *
     * @param request
     * @param response
     * @param handler
     * @return
     */
    default boolean interruptAuthChain(HttpServletRequest request, HttpServletResponse response, Object handler) {
        return false;
    }

    @Override
    default int compareTo(MRAuthInterceptDoer o) {
        return this.order() - o.order();
    }
}
