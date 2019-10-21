package com.hthjsj.web.component.form;

import com.hthjsj.analysis.meta.Component;
import com.hthjsj.web.component.ComponentRender;
import com.hthjsj.web.component.ComponentType;

/**
 * <p> Class title: </p>
 * <p> @Describe: </p>
 * <p> @Date : 2019/10/16 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
public abstract class FormField implements Component, ComponentRender {

    @Override
    public String name() {
        return componentType().getName();
    }

    @Override
    public String code() {
        return componentType().getCode();
    }

    @Override
    public String type() {
        return componentType().getCode();
    }

    public abstract ComponentType componentType();
}
