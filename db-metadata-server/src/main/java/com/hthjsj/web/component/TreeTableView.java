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
public class TreeTableView extends ViewContainer {

    public TreeTableView(String name, String label) {
        super(name, label);
        setRender(new ManualRender<TreeTableView>(this));
    }

    @Override
    public ComponentType componentType() {
        return ComponentType.TREETABLEVIEW;
    }

    @Override
    protected void renderCustomMeta(Kv meta) {
        meta.setIfNotBlank("name", name);
        meta.setIfNotBlank("label", label);
        meta.setIfNotBlank("component_name", type());
    }
}
