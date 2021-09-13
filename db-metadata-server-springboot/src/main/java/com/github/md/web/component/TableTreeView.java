package com.github.md.web.component;

import com.github.md.analysis.component.ComponentType;
import com.github.md.analysis.component.ManualRender;
import com.github.md.analysis.component.ViewContainer;
import com.github.md.analysis.kit.Kv;

/**
 * <p> @Date : 2020/8/4 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
public class TableTreeView extends ViewContainer {

    public TableTreeView(String name, String label) {
        super(name, label);
        setRender(new ManualRender<TableTreeView>(this));
    }

    @Override
    public ComponentType componentType() {
        return ComponentType.TABLETREEVIEW;
    }

    @Override
    protected void renderCustomMeta(Kv meta) {
        meta.setIfNotBlank("name", name);
        meta.setIfNotBlank("label", label);
        meta.setIfNotBlank("component_name", type());
    }
}
