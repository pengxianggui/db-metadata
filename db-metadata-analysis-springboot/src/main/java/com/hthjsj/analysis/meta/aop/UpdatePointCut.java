package com.hthjsj.analysis.meta.aop;

/**
 * <p> @Date : 2019/11/25 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
public interface UpdatePointCut extends IPointCut {

    default boolean updateBefore(AopInvocation invocation) {
        return true;
    }

    default boolean updateAfter(AopInvocation invocation) {
        return true;
    }
}