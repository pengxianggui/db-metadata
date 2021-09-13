package com.github.md.web.controller.itp;

import com.github.md.web.controller.FormQueryInvocation;
import com.github.md.analysis.meta.aop.ViewPointCut;

/**
 * @author pengxg
 * @date 2021/4/9 12:58 下午
 */
public interface FormViewPointCut extends ViewPointCut<FormQueryInvocation> {

    @Override
    default boolean viewBefore(FormQueryInvocation invocation) {
        return false;
    }

    @Override
    default boolean viewAfter(FormQueryInvocation invocation) {
        return false;
    }
}
