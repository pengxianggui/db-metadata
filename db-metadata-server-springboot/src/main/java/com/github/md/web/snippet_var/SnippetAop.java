package com.github.md.web.snippet_var;

import com.github.md.analysis.meta.aop.AddPointCut;
import com.github.md.analysis.meta.aop.FormInvocation;
import com.github.md.analysis.meta.aop.UpdatePointCut;
import com.github.md.web.ServiceManager;

/**
 * 代码片段的AOP
 */
public class SnippetAop implements AddPointCut, UpdatePointCut {

    @Override
    public boolean addAfter(FormInvocation invocation) {
        ServiceManager.getSnippetService().clearCache();
        return true;
    }

    @Override
    public boolean updateAfter(FormInvocation invocation) {
        ServiceManager.getSnippetService().clearCache();
        return true;
    }
}
