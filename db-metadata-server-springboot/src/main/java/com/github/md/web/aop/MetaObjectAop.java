package com.github.md.web.aop;

import com.github.md.analysis.meta.DbMetaService;
import com.github.md.analysis.meta.IMetaObject;
import com.github.md.analysis.meta.aop.AopInvocation;
import com.github.md.analysis.meta.aop.DeletePointCut;
import com.github.md.web.ServiceManager;
import com.github.md.web.query.QueryHelper;
import lombok.extern.slf4j.Slf4j;

/**
 * @author pengxg
 * @date 2022/3/14 11:19 上午
 */
@Slf4j
public class MetaObjectAop implements DeletePointCut {

    private final static String META_OBJECT_CODE = "meta_object";

    @Override
    public boolean deleteAfter(AopInvocation invocation) {
        check(invocation);

        QueryHelper queryHelper = new QueryHelper(invocation.getRequest());
        IMetaObject metaObject = invocation.getMetaObject();
        Object[] ids = queryHelper.getPks(metaObject);

        // TODO 2.2 删除元字段、实例配置。 此处应当取 params做删除。
        DbMetaService dbMetaService = ServiceManager.metaService();
        ServiceManager.businessService().findDataByIds(metaObject, ids);




//        IMetaObject metaObject = dbMetaService.findByCode(objectCode);
//
//        if (metaObject.isSystem()) {
//            throw new RuntimeException(String.format("该对象属于系统元对象,不能删除, code: %s", objectCode));
//        }
//
//        log.info("删除元对象{}数据", metaObject.code());
//        dbMetaService.deleteMetaObject(metaObject.code());
//
//        log.info("删除元对象{}实例配置", metaObject.code());
//        ServiceManager.componentService().deleteObjectAll(objectCode);
        return false;
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
