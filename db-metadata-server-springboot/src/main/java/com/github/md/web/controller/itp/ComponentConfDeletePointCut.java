package com.github.md.web.controller.itp;

import com.github.md.analysis.meta.IMetaObject;
import com.github.md.analysis.meta.aop.DeleteInvocation;
import com.github.md.analysis.meta.aop.DeletePointCut;
import com.github.md.web.ServiceManager;
import com.github.md.web.component.ComponentService;
import com.google.common.base.Joiner;
import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.jfinal.plugin.activerecord.Record;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * 因为前端界面显示的是Meta_object的实例记录,所以级联删除某一元对象的实例配置 需要做额外的工作
 * 删除的实例配置记录 类型META_OBJECT，会级联删除下属所有FIELD；
 * 思路:
 * 1. deleteBefore前将要删除的记录查出,放入Ret
 * 2. deleteAfter中对记录做级联动作;
 * <p> @Date : 2020/12/4 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
@Slf4j
public class ComponentConfDeletePointCut implements DeletePointCut {

    @Override
    public boolean deleteBefore(DeleteInvocation invocation) {
        IMetaObject metaObject = invocation.getMetaObject();
        Preconditions.checkArgument("meta_component_instance".equalsIgnoreCase(metaObject.code()), "该拦截器仅对meta_component_instance元对象启用");

        Object[] ids = invocation.getIds();
        List<String> instanceCodes = Lists.newArrayList();
        for (Object id : ids) {
            Record record = ServiceManager.metaService().findDataByIds(metaObject, id);

            if (ComponentService.INSTANCE.META_OBJECT.toString().equalsIgnoreCase(record.getStr("type"))) {
                instanceCodes.add(record.getStr("code"));
            }
        }
        log.debug("the instanceCodes will be deleted: {}", Joiner.on(",").join(instanceCodes));
        invocation.addContextParam("instanceCodes", instanceCodes);
        return true;
    }

    @Override
    public boolean deleteAfter(DeleteInvocation invocation) {
        List instanceCodes = invocation.getContextParam("instanceCodes", List.class);

        for (Object instanceCode : instanceCodes) {
            ServiceManager.componentService().deleteObjectConfig((String) instanceCode);
        }

        return true;
    }
}
