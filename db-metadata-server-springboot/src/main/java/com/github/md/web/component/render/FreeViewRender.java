package com.github.md.web.component.render;

import com.github.md.analysis.component.ComponentRender;
import com.github.md.analysis.component.ViewContainer;
import com.github.md.analysis.kit.Kv;
import com.github.md.web.component.form.FormField;
import com.github.md.web.component.form.FormFieldFactory;
import com.github.md.web.kit.UtilKit;

import java.util.List;
import java.util.Map;

/**
 * 脱离元对象的自由render。完全依据meta数据来反向构造
 *
 * @author pengxg
 * @date 2023/3/28 21:50
 */
public class FreeViewRender<C extends ViewContainer> implements ComponentRender<C> {
    C component;
    Kv meta;

    public FreeViewRender(C component, Kv meta) {
        this.component = component;
        this.meta = meta;
    }

    @Override
    public C component() {
        return component;
    }

    @Override
    public Kv render() {
        // 将meta反向解析到component中
        UtilKit.deepMerge(component.getMeta(), this.meta, true);
        component.getFields().clear(); // 避免重复render

        List<Map> columns = this.meta.getAs("columns");
        for (Map column : columns) {
            FormField formField = FormFieldFactory.createFormField(Kv.create().set(column));
            this.component.add(formField);
        }

        return component.getMeta();
    }
}
