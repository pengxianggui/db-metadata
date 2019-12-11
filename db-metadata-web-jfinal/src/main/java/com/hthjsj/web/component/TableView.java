package com.hthjsj.web.component;

import com.hthjsj.analysis.component.ComponentType;
import com.hthjsj.analysis.component.ManualRender;
import com.hthjsj.analysis.component.ViewContainer;
import com.jfinal.kit.Kv;
import lombok.Data;

/**
 * <p> Class title: 表格显示控件</p>
 * <p> @Describe: </p>
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
