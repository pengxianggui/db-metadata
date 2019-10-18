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

    public DropDown(String name, String label, String config) {
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

    @Override
    public Kv renderMeta() {
        Kv kv = Kv.create();
        kv.setIfNotBlank("name", name);
        kv.setIfNotBlank("label", label);
        kv.setIfNotBlank("component_name", type());
        return kv;
    }
}
