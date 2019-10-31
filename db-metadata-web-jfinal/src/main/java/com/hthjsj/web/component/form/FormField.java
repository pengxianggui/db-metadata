package com.hthjsj.web.component.form;

import com.hthjsj.web.component.ViewComponent;
import com.jfinal.kit.Kv;

/**
 * <p> Class title: </p>
 * <p> @Describe: </p>
 * <p> @Date : 2019/10/16 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
public abstract class FormField extends ViewComponent {

    protected Kv meta = Kv.create();

    protected Kv conf = Kv.create();

    protected String name;

    protected String label;

    public FormField() {
    }

    public FormField(String name, String label) {
        this.name = name;
        this.label = label;
        this.meta.set("component_name", type());
        this.meta.set("name", name);
        this.meta.set("label", label);
    }

    @Override
    public String config() {
        return null;
    }

}
