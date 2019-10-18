package com.hthjsj.web.component.form;

import com.hthjsj.analysis.meta.Component;
import com.hthjsj.web.component.ComponentRender;

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
        return null;
    }

    @Override
    public String code() {
        return null;
    }

    @Override
    public String type() {
        return getClass().getSimpleName();
    }
}
