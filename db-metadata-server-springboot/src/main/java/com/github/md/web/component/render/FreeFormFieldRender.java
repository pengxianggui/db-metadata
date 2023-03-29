package com.github.md.web.component.render;

import com.github.md.analysis.component.Component;
import com.github.md.analysis.component.ComponentRender;
import com.github.md.analysis.kit.Kv;
import com.github.md.web.kit.UtilKit;

/**
 * 脱离元字段的自由render。完全依据meta数据来反向构造。
 *
 * @author pengxg
 * @date 2023/3/28 22:24
 */
public class FreeFormFieldRender<C extends Component> implements ComponentRender<C> {
    C component;
    Kv meta;

    public FreeFormFieldRender(C component, Kv meta) {
        this.meta = meta;
        this.component = component;
    }

    @Override
    public C component() {
        return component;
    }

    @Override
    public Kv render() {
        // 将meta反向解析到component中
        UtilKit.deepMerge(component.getMeta(), this.meta, true);
        return component.getMeta();
    }
}
