package com.github.md.analysis.meta.aop;

/**
 * <p> @Date : 2019/11/25 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
public interface AddPointCut extends IPointCut {

    /**
     * @return
     */
    default boolean addBefore(FormInvocation invocation) {
        return true;
    }

    default boolean addAfter(FormInvocation invocation) {
        return true;
    }
}
