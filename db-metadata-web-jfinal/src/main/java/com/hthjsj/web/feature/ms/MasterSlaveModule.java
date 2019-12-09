package com.hthjsj.web.feature.ms;

import com.google.common.collect.Lists;
import com.hthjsj.analysis.meta.IMetaObject;
import com.hthjsj.web.ServiceManager;
import com.hthjsj.web.ui.MetaObjectViewAdapter;
import com.hthjsj.web.ui.UIManager;

import java.util.List;

/**
 * <p> @Date : 2019/12/9 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
public class MasterSlaveModule {

    MetaObjectViewAdapter metaObjectViewAdapter;

    List<MetaObjectViewAdapter> childrens;

    public MasterSlaveModule(String masterObjectCode, String... slaveObjectCode) {
        metaObjectViewAdapter = UIManager.getForm(ServiceManager.metaService().findByCode(masterObjectCode));
        List<IMetaObject> objects = ServiceManager.metaService().findByCodes(slaveObjectCode);
        childrens = Lists.newArrayList();
        objects.forEach(m -> {
            childrens.add(UIManager.getTable(m));
        });
    }
}
