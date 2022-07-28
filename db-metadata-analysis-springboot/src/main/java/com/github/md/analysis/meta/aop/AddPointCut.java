package com.github.md.analysis.meta.aop;

/**
 * <p> @Date : 2019/11/25 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
public interface AddPointCut extends IPointCut {

    /**
     * 返回false, 则后续拦截器不会执行
     * @param invocation
     * @return
     */
    default boolean addBefore(FormInvocation invocation) {
        return true;
    }

    /**
     * 返回false, 则后续拦截器不会执行
     * @param invocation
     * @return
     */
    default boolean addAfter(FormInvocation invocation) {
        return true;
    }
}
