package com.hthjsj.web.controller.itp;

import com.google.common.base.Preconditions;
import com.hthjsj.analysis.meta.IMetaObject;
import com.hthjsj.analysis.meta.aop.AopInvocation;
import com.hthjsj.analysis.meta.aop.DeletePointCut;
import com.hthjsj.web.ServiceManager;
import com.hthjsj.web.query.QueryHelper;
import com.jfinal.plugin.activerecord.Record;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
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
    public boolean deleteBefore(AopInvocation invocation) {
        QueryHelper queryHelper = new QueryHelper(null);
        IMetaObject metaObject = invocation.getMetaObject();
        Preconditions.checkArgument("meta_component_instance".equalsIgnoreCase(metaObject.code()), "该拦截器仅对meta_component_instance元对象启用");

        Object[] ids = queryHelper.getPks(metaObject);
        List<Record> objectInstances = new ArrayList<Record>();
        for (Object id : ids) {
            Preconditions.checkArgument(id instanceof Object[] && ((Object[]) id).length == 1, "组件配置删除动作,不支持联合主键");
            Record record = ServiceManager.metaService().findDataByIds(metaObject, id);
            String compType = record.getStr("comp_code");

            if ("META_OBJECT".equalsIgnoreCase(record.getStr("type"))) {
                log.info("Pre delete [{}] instance config , ComponentType is [{}] ", record.getStr("dest_object"), compType);
                objectInstances.add(record);
            }
        }
        invocation.getRet().set("objectInstances", objectInstances);
        return true;
    }

    @Override
    public boolean deleteAfter(AopInvocation invocation) {
        List<Record> objectInstances = (List<Record>) invocation.getRet().get("objectInstances");

        for (Record record : objectInstances) {
            ServiceManager.componentService().deleteObjectConfig(record.getStr("comp_code"), record.getStr("dest_object"), false);
        }

        return true;
    }
}
