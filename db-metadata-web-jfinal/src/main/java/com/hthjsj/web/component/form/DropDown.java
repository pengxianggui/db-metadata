package com.hthjsj.web.component.form;

import com.hthjsj.analysis.meta.IMetaField;
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

    public DropDown(String name, String label) {
        this.name = name;
        this.label = label;
    }

    @Override
    public String name() {
        return name;
    }

    @Override
    public String code() {
        return label;
    }

    @Override
    public String type() {
        return "DropDownBox";
    }

    public DropDown dataUrl(String url) {
        toKv().setIfNotBlank("data_url", url);
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
        toKv().setIfNotBlank("dep", dependency);
        return this;
    }

    @Override
    public Kv toKv() {
        Kv kv = Kv.create();
        kv.setIfNotBlank("name", name);
        kv.setIfNotBlank("label", label);
        kv.setIfNotBlank("component_name", type());
        return kv;
    }
}
