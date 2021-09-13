package com.github.md.analysis.meta.aop;

/**
 * <p> @Date : 2019/11/25 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
public interface DeletePointCut extends IPointCut {

    default boolean deleteBefore(AopInvocation invocation) {
        return true;
    }

    default boolean deleteAfter(AopInvocation invocation) {
        return true;
    }
}