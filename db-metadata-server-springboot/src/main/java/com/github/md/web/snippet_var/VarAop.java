package com.github.md.web.snippet_var;

import com.github.md.analysis.meta.aop.AddPointCut;
import com.github.md.analysis.meta.aop.FormInvocation;
import com.github.md.analysis.meta.aop.UpdatePointCut;
import com.github.md.web.ServiceManager;

public class VarAop implements AddPointCut, UpdatePointCut {

    @Override
    public boolean addAfter(FormInvocation invocation) {
        ServiceManager.getVarService().clearVarCache();
        return true;
    }

    @Override
    public boolean updateAfter(FormInvocation invocation) {
        ServiceManager.getVarService().clearVarCache();
        return true;
    }
}
