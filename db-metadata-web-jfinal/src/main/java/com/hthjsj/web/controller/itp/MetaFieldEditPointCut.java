package com.hthjsj.web.controller.itp;

import com.hthjsj.analysis.component.ComponentType;
import com.hthjsj.analysis.meta.IMetaObject;
import com.hthjsj.analysis.meta.aop.AopInvocation;
import com.hthjsj.analysis.meta.aop.UpdatePointCut;
import com.hthjsj.web.ServiceManager;
import com.hthjsj.web.ui.MetaFieldViewAdapter;
import com.hthjsj.web.ui.MetaObjectViewAdapter;
import com.hthjsj.web.ui.UIManager;
import com.jfinal.kit.Kv;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * 元子段编辑拦截器
 * <p> @Date : 2019/12/3 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
@Slf4j
public class MetaFieldEditPointCut implements UpdatePointCut {

    @Override
    public boolean updateBefore(AopInvocation invocation) {
        return true;
    }

    /**
     * 级联动作
     *
     * @param result
     * @param invocation
     */
    @Override
    public boolean updateAfter(boolean result, AopInvocation invocation) {
        if (result) {
            log.info("MetaFieldEditPointCut.updateAfter run");
            Kv updateData = invocation.getMetaData();
            String fieldCode = updateData.getStr("field_code");
            IMetaObject metaObject = invocation.getMetaObject();
            List<ComponentType> existTypes = ServiceManager.componentService().loadTypesByObjectCode(metaObject.code());
            for (ComponentType type : existTypes) {
                MetaObjectViewAdapter metaObjectViewAdapter = UIManager.getView(metaObject, type);
                MetaFieldViewAdapter metaFieldViewAdapter = metaObjectViewAdapter.getFieldAdapter(fieldCode);
                UIManager.update(metaFieldViewAdapter);
            }
        }
        return true;
    }
}
