package com.github.md.web.event;

import com.github.md.analysis.meta.aop.AopInvocation;
import lombok.extern.slf4j.Slf4j;

/**
 * <p> @Date : 2020/1/16 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
@Slf4j
public class TestFormExtensibleListener implements FormExtensibleListener {

    @Override
    public boolean isHit(AopInvocation invocation) {
        return invocation.getMetaObject().code().equalsIgnoreCase("test_table");
    }

    @Override
    public void handler(AopInvocation invocation) {
        log.error("===============================================");
        log.error("{} {} {} {}", "hello", invocation.getMetaObject().code(), invocation.isPreOperateStatus());
    }
}
