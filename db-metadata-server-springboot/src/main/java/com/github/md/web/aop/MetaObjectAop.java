package com.github.md.web.aop;

import cn.com.asoco.util.AssertUtil;
import com.github.md.analysis.meta.DbMetaService;
import com.github.md.analysis.meta.IMetaObject;
import com.github.md.analysis.meta.aop.AopInvocation;
import com.github.md.analysis.meta.aop.DeletePointCut;
import com.github.md.web.ServiceManager;
import com.github.md.web.WebException;
import com.github.md.web.query.QueryHelper;
import lombok.extern.slf4j.Slf4j;

/**
 * 元对象的执行器
 *
 * @author pengxg
 * @date 2022/3/14 11:19 上午
 */
@Slf4j
public class MetaObjectAop implements DeletePointCut {
    private final static String META_OBJECT_CODE = "meta_object";

    /**
     * 元对象的删除前置操作:
     * <pre>
     * 1. 校验是否为系统内置，若是，则不允许删除。
     * 2. 删除关联数据:
     *  - 元字段
     *  - 实例配置
     * </pre>
     *
     * @param invocation
     * @return
     */
    @Override
    public boolean deleteBefore(AopInvocation invocation) {
        check(invocation);

        QueryHelper queryHelper = new QueryHelper(invocation.getRequest());
        IMetaObject metaObject = invocation.getMetaObject();

        Object[] ids = queryHelper.getPks(metaObject);
        DbMetaService dbMetaService = ServiceManager.metaService();

        for (Object jointId : ids) {
            Object id = ((Object[]) jointId)[0];
            IMetaObject metaObjectOfData = dbMetaService.findById(String.valueOf(id));

            AssertUtil.isTrue(metaObjectOfData != null, new WebException("元对象不存在, id: %s", String.valueOf(id)));
            AssertUtil.isTrue(!metaObjectOfData.isSystem(), new WebException("元对象(%s)为系统内置, 不允许删除", metaObjectOfData.code()));

            // 删除元字段
            log.info("删除元对象{}数据", metaObjectOfData.code());
            dbMetaService.deleteMetaFields(metaObjectOfData.code());

            // 删除实例配置
            log.info("删除元对象{}实例配置", metaObjectOfData.code());
            ServiceManager.componentService().deleteObjectAll(metaObjectOfData.code());
        }

        return true;
    }

    private void check(AopInvocation invocation) {
        IMetaObject metaObject = invocation.getMetaObject();
        if (!META_OBJECT_CODE.equals(metaObject.code())) {
            throw new RuntimeException(
                    String.format("AOP: %s 只能作用于元对象: %s",
                            this.getClass().getCanonicalName(), metaObject.code()));
        }
    }
}
