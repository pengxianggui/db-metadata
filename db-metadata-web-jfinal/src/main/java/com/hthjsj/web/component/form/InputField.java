package com.hthjsj.web.component.form;

import com.hthjsj.web.component.ComponentType;
import com.hthjsj.web.ui.AccessBehavior;
import com.jfinal.kit.Kv;

/**
 * <p> Class title: </p>
 * <p> @Describe: </p>
 * <p> @Date : 2019/10/16 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
public class InputField extends FormField {

    String name;

    String label;

    Kv meta = Kv.create();

    Kv conf = Kv.create();

    public InputField() {
    }

    public InputField(String name, String label) {
        this.name = name;
        this.label = label;
        setAccessBehavior(new AccessBehavior.DefaultAccessBehavior(conf));
    }

    @Override
    public ComponentType componentType() {
        return ComponentType.INPUTFIELD;
    }

    @Override
    public Kv toKv() {
        meta.set("component_name", type());
        meta.set("name", name);
        meta.set("label", label);
        return meta;
    }
}
