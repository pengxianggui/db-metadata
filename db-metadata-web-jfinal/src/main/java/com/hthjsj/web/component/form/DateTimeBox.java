package com.hthjsj.web.component.form;

import com.hthjsj.analysis.component.ComponentType;
import com.jfinal.kit.Kv;

/**
 * <p> @Date : 2019/10/31 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
public class DateTimeBox extends FormField {

    public DateTimeBox(String name, String label) {
        super(name, label);
    }

    @Override
    public ComponentType componentType() {
        return ComponentType.DATETIMEBOX;
    }

    @Override
    public Kv toKv() {
        getFieldInject().inject(meta);
        return meta;
    }
}
