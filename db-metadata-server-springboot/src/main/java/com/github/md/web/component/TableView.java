package com.github.md.web.component;

import com.github.md.analysis.component.ComponentType;
import com.github.md.analysis.component.ManualRender;
import com.github.md.analysis.component.ViewContainer;
import com.github.md.analysis.kit.Kv;
import lombok.Data;

/**
 * <p> 表格显示控件</p>
 * <p> @Date : 2019/10/9 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
@Data
public class TableView extends ViewContainer {

    public TableView(String name, String label) {
        super(name, label);
        setRender(new ManualRender<TableView>(this));
    }

    @Override
    public ComponentType componentType() {
        return ComponentType.TABLEVIEW;
    }

    public TableView dataUrl(String url) {
        meta.setIfNotBlank("data_url", url);
        return this;
    }

    @Override
    protected void renderCustomMeta(Kv meta) {
        meta.setIfNotBlank("name", name);
        meta.setIfNotBlank("label", label);
        meta.setIfNotBlank("component_name", type());
    }
}
