package com.hthjsj.web.component.form;

import com.hthjsj.analysis.component.ComponentType;
import com.jfinal.kit.Kv;

/**
 * <p> @Date : 2019/10/31 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
public class BoolBox extends FormField {

    public BoolBox(String name, String label) {
        super(name, label);
    }

    @Override
    public ComponentType componentType() {
        return ComponentType.BOOLBOX;
    }

    @Override
    public Kv toKv() {
        getFieldInject().inject(meta);
        return getRender().render();
    }
}
