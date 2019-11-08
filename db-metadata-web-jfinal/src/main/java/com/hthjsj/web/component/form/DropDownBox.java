package com.hthjsj.web.component.form;

import com.hthjsj.analysis.component.ComponentType;
import com.jfinal.kit.Kv;

/**
 * <p> Class title: </p>
 * <p> @Describe: </p>
 * <p> @Date : 2019/10/16 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
public class DropDownBox extends FormField {

    public DropDownBox(String name, String label) {
        super(name, label);
    }

    public DropDownBox dataUrl(String url) {
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
    public DropDownBox dependency(String dependency) {
        meta.setIfNotBlank("dep", dependency);
        return this;
    }

    @Override
    public Kv toKv() {
        getFieldInject().inject(meta);
        return meta;
    }

    @Override
    public ComponentType componentType() {
        return ComponentType.DROPDOWN;
    }
}
