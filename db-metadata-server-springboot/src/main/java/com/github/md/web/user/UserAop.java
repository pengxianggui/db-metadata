package com.github.md.web.user;

import cn.com.asoco.util.AssertUtil;
import com.github.md.analysis.meta.IMetaObject;
import com.github.md.analysis.meta.MetaData;
import com.github.md.analysis.meta.aop.AddPointCut;
import com.github.md.analysis.meta.aop.AopInvocation;
import com.github.md.analysis.meta.aop.FormInvocation;
import com.github.md.analysis.meta.aop.UpdatePointCut;
import com.github.md.web.ServiceManager;
import com.github.md.web.WebException;
import com.github.md.web.config.MetaProperties;
import com.github.md.web.kit.PassKit;
import lombok.extern.slf4j.Slf4j;

/**
 * 内置用户元对象: meta_user AOP
 *
 * @author pengxg
 * @date 2022/2/16 9:58 下午
 */
@Slf4j
public class UserAop implements AddPointCut, UpdatePointCut {
    private final String OBJECT_CODE = "meta_user";

    @Override
    public boolean addBefore(FormInvocation invocation) {
        check(invocation);

        MetaProperties metaProperties = ServiceManager.getAppProperties();
        if (!metaProperties.getApp().getAddable()) {
            throw new UnsupportedOperationException("已配置用户体系不允许新增用户, 如需支持新增, 请开启配置项(md.server.user.addable=true)");
        }

        // 设置默认加密密码
        MetaData formData = invocation.getFormData();
        formData.putIfAbsent("password", PassKit.encryptPass());

        return true;
    }

    @Override
    public boolean updateBefore(FormInvocation invocation) {
        check(invocation);

        return true;
    }

    private void check(AopInvocation invocation) {
        IMetaObject metaObject = invocation.getMetaObject();
        AssertUtil.isTrue(metaObject.code().equals(OBJECT_CODE), new WebException("AOP: %s 只能作用于元对象: %s",
                this.getClass().getCanonicalName(), OBJECT_CODE));
    }
}
