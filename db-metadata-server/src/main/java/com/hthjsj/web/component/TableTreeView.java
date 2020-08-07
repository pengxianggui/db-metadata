package com.hthjsj.web.component;

import com.hthjsj.analysis.component.ComponentType;
import com.hthjsj.analysis.component.ManualRender;
import com.hthjsj.analysis.component.ViewContainer;
import com.jfinal.kit.Kv;

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
