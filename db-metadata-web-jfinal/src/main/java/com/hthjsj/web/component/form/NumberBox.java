package com.hthjsj.web.component.form;

import com.hthjsj.web.component.ComponentType;
import com.jfinal.kit.Kv;

/**
 * <p> @Date : 2019/10/31 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
public class NumberBox extends FormField {

    public NumberBox() {
    }

    public NumberBox(String name, String label) {
        super(name, label);
    }

    @Override
    public ComponentType componentType() {
        return ComponentType.NUMBERBOX;
    }

    @Override
    public Kv toKv() {
        getViewInject().inject(this, meta, conf, getFieldInject());
        return meta;
    }
}
