package com.hthjsj.web.ui;

import com.hthjsj.analysis.component.ComponentType;
import com.hthjsj.analysis.meta.MetaObject;

/**
 * <p> @Date : 2019/11/13 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
public class UIManager {

    public static MetaObjectViewAdapter getSmartAutoView(MetaObject metaObject, ComponentType componentType) {
        return SmartAssembleFactory.me().createMetaObjectViewAdapter(metaObject, componentType);
    }

    public static MetaObjectViewAdapter getView(MetaObject metaObject, ComponentType componentType) {
        return ViewAssembleFactory.me().createMetaObjectViewAdapter(metaObject, componentType);
    }

    /**
     * 获取计算后的FormView适配器
     *
     * @param metaObject
     *
     * @return
     */
    public static MetaObjectViewAdapter getAutoComputedFrom(MetaObject metaObject) {
        return SmartAssembleFactory.me().createMetaObjectViewAdapter(metaObject, ComponentType.FORMVIEW);
    }

    /**
     * 获取计算后的TableView配器
     *
     * @param metaObject
     *
     * @return
     */
    public static MetaObjectViewAdapter getAutoComputedTable(MetaObject metaObject) {
        return SmartAssembleFactory.me().createMetaObjectViewAdapter(metaObject, ComponentType.TABLEVIEW);
    }

    /**
     * 获取指定元对象的From实例
     *
     * @param metaObject
     *
     * @return
     */
    public static MetaObjectViewAdapter getForm(MetaObject metaObject) {
        return ViewAssembleFactory.me().createMetaObjectViewAdapter(metaObject, ComponentType.FORMVIEW);
    }

    /**
     * 获取指定元对象的Table实例
     *
     * @param metaObject
     *
     * @return
     */
    public static MetaObjectViewAdapter getTable(MetaObject metaObject) {
        return ViewAssembleFactory.me().createMetaObjectViewAdapter(metaObject, ComponentType.TABLEVIEW);
    }
}
