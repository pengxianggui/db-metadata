package com.hthjsj.web.controller.itp;

import com.hthjsj.analysis.meta.aop.ViewPointCut;
import com.hthjsj.web.controller.FormQueryInvocation;

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
