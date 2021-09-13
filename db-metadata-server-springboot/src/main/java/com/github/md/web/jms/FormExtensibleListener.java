package com.github.md.web.jms;

import com.github.md.analysis.meta.aop.AopInvocation;

/**
 * <p> @Date : 2020/1/16 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
public interface FormExtensibleListener {

    boolean isHit(AopInvocation invocation);

    void handler(AopInvocation invocation);
}
