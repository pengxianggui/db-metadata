package com.hthjsj.web.component.form;

import com.hthjsj.analysis.meta.IMetaField;
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

    private IMetaField metaField;

    private Kv kv = Kv.create();

    public DropDown() {
    }

    public DropDown(String name, String label) {
        this.name = name;
        this.label = label;
    }

    public DropDown dataUrl(String url) {
        kv.setIfNotBlank("data_url", url);
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
        kv.setIfNotBlank("dep", dependency);
        return this;
    }

    @Override
    public Kv toKv() {
        kv.setIfNotBlank("name", name);
        kv.setIfNotBlank("label", label);
        kv.setIfNotBlank("component_name", type());
        return kv;
    }

    @Override
    public ComponentType componentType() {
        return ComponentType.DROPDOWN;
    }
}
