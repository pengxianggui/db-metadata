package com.hthjsj.web.component.form;

import com.hthjsj.web.component.ComponentType;
import com.jfinal.kit.Kv;

/**
 * <p> Class title: </p>
 * <p> @Describe: </p>
 * <p> @Date : 2019/10/16 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
public class DropDown extends FormField {

    private String name;

    private String label;

    public DropDown() {
    }

    public DropDown(String name, String label) {
        this.name = name;
        this.label = label;
    }

    public DropDown dataUrl(String url) {
        meta.setIfNotBlank("data_url", url);
        return this;
    }

    /**
     * other field [name]
     *
     * @param dependency
     *
     * @return
     */
    public DropDown dependency(String dependency) {
        meta.setIfNotBlank("dep", dependency);
        return this;
    }

    @Override
    public Kv toKv() {
        meta.setIfNotBlank("name", name);
        meta.setIfNotBlank("label", label);
        meta.setIfNotBlank("component_name", type());
        getViewInject().inject(this, meta, conf, getFieldInject());
        return meta;
    }

    @Override
    public ComponentType componentType() {
        return ComponentType.DROPDOWN;
    }
}
