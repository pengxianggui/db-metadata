package com.hthjsj.analysis.meta.aop;

/**
 * <p> @Date : 2019/11/25 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
public interface ViewPointCut extends IPointCut {

    void viewBefore(AopInvocation invocation);

    void viewAfter(AopInvocation invocation);
}