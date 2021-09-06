package com.hthjsj.web.ui;

import com.hthjsj.analysis.component.Component;
import com.hthjsj.analysis.component.ComponentType;
import com.hthjsj.analysis.meta.IMetaField;
import com.hthjsj.analysis.meta.IMetaObject;
import com.jfinal.kit.Kv;

/**
 * <p> @Date : 2019/11/25 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
public interface MetaViewAdapterFactory {

    MetaObjectViewAdapter createMetaObjectViewAdapter(IMetaObject metaObject, ComponentType componentType);

    /**
     * 以实例code来构建对象;
     *
     * @param instanceCode
     *
     * @return
     */
    MetaObjectViewAdapter createMetaObjectViewAdapter(String instanceCode);

    /**
     * <pre>
     * 属性重算逻辑,分2种情况
     * 1. 已经有过额外自定义配置的属性,重新计算
     * 2. 还未生成的配置,重新计算
     * </pre>
     *
     * @param metaField
     * @param fieldComponent
     * @param toBeUpdatedFieldInstanceConfig 准备更新的配置,在外部与老配置merge好后传入
     */
    boolean reCompute(IMetaField metaField, Component fieldComponent, Kv toBeUpdatedFieldInstanceConfig);

    boolean reCompute(MetaFieldViewAdapter metaFieldViewAdapter, Kv toBeUpdatedFieldInstanceConfig);
}
