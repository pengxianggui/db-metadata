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

    private String cn;

    private String en;

    private IMetaField metaField;

    @Override
    public String name() {
        return cn;
    }

    @Override
    public String code() {
        return en;
    }

    @Override
    public String type() {
        return getClass().getSimpleName();
    }

    @Override
    public Kv renderMeta() {
        return Kv.create().set("cn", "fasdf").set("en", "djfalskdj");
    }
}
