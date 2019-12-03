package com.hthjsj.web.ui;

import com.hthjsj.analysis.component.ComponentType;
import com.hthjsj.analysis.meta.IMetaObject;
import com.hthjsj.web.WebException;

/**
 * <p> @Date : 2019/11/13 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
public class UIManager {

    public static MetaObjectViewAdapter getSmartAutoView(IMetaObject metaObject, ComponentType componentType) {
        return SmartAssembleFactory.me().createMetaObjectViewAdapter(metaObject, componentType);
    }

    public static MetaObjectViewAdapter getView(IMetaObject metaObject, ComponentType componentType) {
        return ViewAssembleFactory.me().createMetaObjectViewAdapter(metaObject, componentType);
    }

    /**
     * 获取计算后的FormView适配器
     *
     * @param metaObject
     *
     * @return
     */
    public static MetaObjectViewAdapter getAutoComputedFrom(IMetaObject metaObject) {
        return SmartAssembleFactory.me().createMetaObjectViewAdapter(metaObject, ComponentType.FORMVIEW);
    }

    /**
     * 获取计算后的TableView配器
     *
     * @param metaObject
     *
     * @return
     */
    public static MetaObjectViewAdapter getAutoComputedTable(IMetaObject metaObject) {
        return SmartAssembleFactory.me().createMetaObjectViewAdapter(metaObject, ComponentType.TABLEVIEW);
    }

    /**
     * 获取指定元对象的From实例
     *
     * @param metaObject
     *
     * @return
     */
    public static MetaObjectViewAdapter getForm(IMetaObject metaObject) {
        return ViewAssembleFactory.me().createMetaObjectViewAdapter(metaObject, ComponentType.FORMVIEW);
    }

    /**
     * 获取指定元对象的Table实例
     *
     * @param metaObject
     *
     * @return
     */
    public static MetaObjectViewAdapter getTable(IMetaObject metaObject) {
        return ViewAssembleFactory.me().createMetaObjectViewAdapter(metaObject, ComponentType.TABLEVIEW);
    }

    public static boolean update(MetaObjectViewAdapter metaObjectViewAdapter) {
        throw new WebException("not finished");
    }

    public static boolean update(MetaFieldViewAdapter metaFieldViewAdapter) {
        return ViewAssembleFactory.me().reCompute(metaFieldViewAdapter);
    }
}
