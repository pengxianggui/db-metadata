package com.github.md.web.component;

import com.github.md.analysis.component.ComponentType;
import com.github.md.analysis.component.ManualRender;
import com.github.md.analysis.component.ViewContainer;
import com.github.md.analysis.kit.Kv;

/**
 * @author pengxg
 * @date 2022/3/18 10:21 下午
 */
public class TreeView extends ViewContainer {

    public TreeView(String name, String label) {
        super(name, label);
        setRender(new ManualRender<>(this));
    }

    @Override
    public ComponentType componentType() {
        return ComponentType.TREEVIEW;
    }

    @Override
    protected void renderCustomMeta(Kv meta) {
        meta.setIfNotBlank("name", name);
        meta.setIfNotBlank("label", label);
        meta.setIfNotBlank("component_name", type());
    }
}
