package com.github.md.web.component;

import com.github.md.analysis.component.ComponentType;
import com.github.md.analysis.component.ManualRender;
import com.github.md.analysis.component.ViewContainer;
import com.github.md.analysis.kit.Kv;

/**
 * 搜索面板,目前与表格展示控件一起使用
 * <p> @Date : 2019/11/26 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
public class SearchView extends ViewContainer {

    public SearchView(String name, String label) {
        super(name, label);
        setRender(new ManualRender<SearchView>(this));
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
