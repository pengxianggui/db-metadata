package com.hthjsj.web.ui;

import com.hthjsj.analysis.component.ComponentType;
import com.hthjsj.analysis.meta.MetaObject;

/**
 * <p> @Date : 2019/11/25 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
public interface MetaViewAdapterFactory {

    MetaObjectViewAdapter createMetaObjectViewAdapter(MetaObject metaObject, ComponentType componentType);
}
