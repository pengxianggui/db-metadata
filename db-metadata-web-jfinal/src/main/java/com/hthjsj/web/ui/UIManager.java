package com.hthjsj.web.ui;

import com.hthjsj.analysis.component.ComponentType;
import com.hthjsj.analysis.meta.IMetaObject;
import com.hthjsj.analysis.meta.MetaObject;

/**
 * <p> @Date : 2019/11/13 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
public class UIManager {

    public static MetaObjectViewAdapter getAuto(MetaObject metaObject, ComponentType componentType) {
        return null;
    }

    /**
     * 获取计算后的FormView适配器
     *
     * @param metaObject
     *
     * @return
     */
    public static MetaObjectViewAdapter getAutoComputedFrom(MetaObject metaObject) {
        return ViewAssembleFactory.fetchObjectAdapter(metaObject, ComponentType.FORMVIEW);
    }

    /**
     * 获取计算后的TableView配器
     *
     * @param metaObject
     *
     * @return
     */
    public static MetaObjectViewAdapter getAutoComputedTable(MetaObject metaObject) {
        return ViewAssembleFactory.fetchObjectAdapter(metaObject, ComponentType.TABLEVIEW);
    }

    /**
     * 获取指定元对象的From实例
     *
     * @param metaObject
     *
     * @return
     */
    public static MetaObjectViewAdapter getForm(IMetaObject metaObject) {
        return null;
    }

    /**
     * 获取指定元对象的Table实例
     *
     * @param metaObject
     *
     * @return
     */
    public static MetaObjectViewAdapter getTable(IMetaObject metaObject) {
        return null;
    }
}
