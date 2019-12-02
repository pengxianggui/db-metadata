package com.hthjsj.web.ui;

import com.hthjsj.analysis.component.ComponentType;
import com.hthjsj.analysis.meta.IMetaObject;

/**
 * <p> @Date : 2019/11/25 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
public interface MetaViewAdapterFactory {

    MetaObjectViewAdapter createMetaObjectViewAdapter(IMetaObject metaObject, ComponentType componentType);


    /**
     * 属性重算逻辑,分2种情况
     * 1. 已经有过额外自定义配置的属性,重新计算
     * 2. 还未生成的配置,重新计算
     * */

//    void reCompute(IMetaField metaField);
}
