package com.github.md.web.controller.itp;

import com.github.md.analysis.meta.aop.DetailQueryInvocation;
import com.github.md.analysis.meta.aop.ViewPointCut;

/**
 * @author pengxg
 * @date 2021/4/9 12:58 下午
 */
public interface FormViewPointCut extends ViewPointCut<DetailQueryInvocation> {

    @Override
    default boolean viewBefore(DetailQueryInvocation invocation) {
        return false;
    }

    @Override
    default boolean viewAfter(DetailQueryInvocation invocation) {
        return false;
    }
}
