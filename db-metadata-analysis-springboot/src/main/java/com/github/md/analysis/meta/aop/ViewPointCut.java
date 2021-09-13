package com.github.md.analysis.meta.aop;

/**
 * <p> @Date : 2019/11/25 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
public interface ViewPointCut<T extends AopInvocation> extends IPointCut {

    default boolean viewBefore(T invocation) {
        return false;
    }

    default boolean viewAfter(T invocation) {
        return false;
    }
}