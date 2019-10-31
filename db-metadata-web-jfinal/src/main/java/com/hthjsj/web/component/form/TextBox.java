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
public class TextBox extends FormField {

    public TextBox() {
    }

    public TextBox(String name, String label) {
        super(name, label);
        setAccessBehavior(new AccessBehavior.DefaultAccessBehavior(conf));
    }

    @Override
    public ComponentType componentType() {
        return ComponentType.INPUTFIELD;
    }

    @Override
    public Kv toKv() {

        return meta;
    }
}
