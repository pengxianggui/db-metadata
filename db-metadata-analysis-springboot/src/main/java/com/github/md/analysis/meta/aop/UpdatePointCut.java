package com.github.md.analysis.meta.aop;

/**
 * <p> @Date : 2019/11/25 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
public interface UpdatePointCut extends IPointCut {

    default boolean updateBefore(FormInvocation invocation) {
        return true;
    }

    default boolean updateAfter(FormInvocation invocation) {
        return true;
    }
}
