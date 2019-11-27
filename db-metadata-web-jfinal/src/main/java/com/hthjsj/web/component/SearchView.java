package com.hthjsj.web.component;

import com.hthjsj.analysis.component.ComponentType;
import com.hthjsj.analysis.component.ViewContainer;
import com.jfinal.kit.Kv;

/**
 * <p> @Date : 2019/11/26 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
public class SearchView extends ViewContainer {

    public SearchView(String name, String label) {
        super(name, label);
    }

    @Override
    protected void renderCustomMeta(Kv meta) {
        meta.setIfNotBlank("name", name);
        meta.setIfNotBlank("label", label);
        meta.setIfNotBlank("component_name", type());
    }

    @Override
    public ComponentType componentType() {
        return ComponentType.SEARCHVIEW;
    }

}
