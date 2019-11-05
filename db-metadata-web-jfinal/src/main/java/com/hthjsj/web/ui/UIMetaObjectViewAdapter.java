package com.hthjsj.web.ui;

import com.google.common.collect.Lists;
import com.hthjsj.analysis.meta.IMetaField;
import com.hthjsj.analysis.meta.IMetaObject;
import com.hthjsj.web.component.ViewComponent;
import com.jfinal.kit.Kv;

import java.util.List;

/**
 * <p> @Date : 2019/11/5 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
public class UIMetaObjectViewAdapter implements IViewAdapter<IMetaObject> {

    IMetaObject metaObject;

    Kv instanceConfig;

    ViewComponent component;

    List<IViewAdapter<IMetaField>> fields = Lists.newArrayList();

    @Override
    public IMetaObject getMeta() {
        return metaObject;
    }

    @Override
    public Kv instanceConfig() {
        return instanceConfig;
    }

    @Override
    public ViewComponent getComponent() {
        return component;
    }

    @Override
    public List<IViewAdapter<IMetaField>> fields() {
        return fields;
    }
}
