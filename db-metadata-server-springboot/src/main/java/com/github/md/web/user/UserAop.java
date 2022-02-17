package com.github.md.web.user;

import com.github.md.analysis.meta.aop.AddPointCut;
import com.github.md.analysis.meta.aop.AopInvocation;

/**
 * 内置用户元对象: meta_user AOP
 *
 * @author pengxg
 * @date 2022/2/16 9:58 下午
 */
public class UserAop implements AddPointCut {

    @Override
    public boolean addBefore(AopInvocation invocation) {
        final String defaultPass = "888888"; // TODO 默认密码明文可配, 采用合适的加密算法加密入库
        invocation.getFormData().putIfAbsent("password", defaultPass);
        return true;
    }
}
