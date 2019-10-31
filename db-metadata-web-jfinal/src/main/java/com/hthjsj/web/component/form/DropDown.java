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

    public DropDown() {
    }

    public DropDown(String name, String label) {
        super(name, label);
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
        getViewInject().inject(this, meta, conf, getFieldInject());
        return meta;
    }

    @Override
    public ComponentType componentType() {
        return ComponentType.DROPDOWN;
    }
}
