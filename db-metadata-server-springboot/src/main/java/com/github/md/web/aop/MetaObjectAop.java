package com.github.md.web.aop;

import com.github.md.analysis.meta.DbMetaService;
import com.github.md.analysis.meta.IMetaObject;
import com.github.md.analysis.meta.aop.DeleteInvocation;
import com.github.md.analysis.meta.aop.DeletePointCut;
import com.github.md.web.ServiceManager;
import com.github.md.web.ex.WebException;
import com.github.md.web.ex.OprNotSupportException;
import com.github.md.web.kit.AssertKit;
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
    public boolean deleteBefore(DeleteInvocation invocation) {
        check(invocation);

        Object[] idsArr = invocation.getIds();
        DbMetaService dbMetaService = ServiceManager.metaService();

        for (Object ids : idsArr) {
            Object id = ((Object[]) ids)[0];
            IMetaObject metaObjectOfData = dbMetaService.findById(String.valueOf(id));

            AssertKit.isTrue(metaObjectOfData != null, new WebException("元对象不存在, id: %s", String.valueOf(id)));
            AssertKit.isTrue(!metaObjectOfData.buildIn(), new OprNotSupportException("元对象(%s)为系统内置, 不允许删除", metaObjectOfData.code()));

            // 删除元字段
            log.info("删除元对象{}数据", metaObjectOfData.code());
            dbMetaService.deleteMetaFields(metaObjectOfData.code());

            // 删除实例配置
            log.info("删除元对象{}实例配置", metaObjectOfData.code());
            ServiceManager.componentService().deleteObjectAll(metaObjectOfData.code());
        }

        return true;
    }

    private void check(DeleteInvocation invocation) {
        IMetaObject metaObject = invocation.getMetaObject();
        if (!META_OBJECT_CODE.equals(metaObject.code())) {
            throw new RuntimeException(
                    String.format("AOP: %s 只能作用于元对象: %s",
                            this.getClass().getCanonicalName(), metaObject.code()));
        }
    }
}
