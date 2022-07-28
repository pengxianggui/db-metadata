package com.github.md.analysis.meta.aop;

/**
 * <p> @Date : 2019/11/25 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
public interface ViewPointCut<T extends AopInvocation> extends IPointCut {

    /**
     * 返回false, 则后续拦截器不会执行
     * @param invocation
     * @return
     */
    default boolean viewBefore(T invocation) {
        return false;
    }

    /**
     * 返回false, 则后续拦截器不会执行
     * @param invocation
     * @return
     */
    default boolean viewAfter(T invocation) {
        return false;
    }
}
