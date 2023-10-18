package com.github.md.web.controller.itp;

import com.github.md.analysis.meta.IMetaObject;
import com.github.md.analysis.meta.aop.DeleteInvocation;
import com.github.md.analysis.meta.aop.DeletePointCut;
import com.github.md.web.ServiceManager;
import com.github.md.web.ex.WebException;
import com.github.md.web.ex.OprNotSupportException;
import com.github.md.web.kit.AssertKit;
import com.jfinal.plugin.activerecord.Record;

/**
 * 系统内置数据防删除AOP. 此AOP作用的元对象必须有build_in字段
 *
 * @author pengxg
 * @date 2022/2/24 10:58 上午
 */
public class BuildInDataPointCut implements DeletePointCut {

    public static final String BUILD_IN_FIELD = "build_in";

    @Override
    public boolean deleteBefore(DeleteInvocation invocation) {
        IMetaObject metaObject = invocation.getMetaObject();
        Object[] ids = invocation.getIds();

        for (Object id : ids) {
            Record record = ServiceManager.businessService().findDataByIds(metaObject, id);
            AssertKit.isTrue(record != null, new WebException("数据不存在，请尝试刷新页面"));
            AssertKit.isTrue(!record.getBoolean(BUILD_IN_FIELD), new OprNotSupportException("内建数据不允许删除"));
        }

        return true;
    }
}
