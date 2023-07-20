package com.github.md.web.component.render.table;

import com.github.md.analysis.component.ComponentType;
import com.github.md.analysis.component.ViewContainer;
import com.github.md.analysis.kit.Kv;
import com.github.md.analysis.meta.ConfigExtension;
import com.github.md.analysis.meta.IMetaField;
import com.github.md.web.ServiceManager;
import com.github.md.web.component.attr.AttributeBuilder;
import com.github.md.web.component.form.FormField;
import com.jfinal.kit.StrKit;

/**
 * 表格单元渲染扩展。
 */
public class TableCellRenderExtension implements ConfigExtension<IMetaField, AttributeBuilder.FatAttributeBuilder, FormField> {

    @Override
    public void config(IMetaField metaField, AttributeBuilder.FatAttributeBuilder builder, FormField component) {
        ViewContainer container = component.getContainer();
        if (container.componentType() != ComponentType.TABLEVIEW
                && container.componentType() != ComponentType.TABLETREEVIEW) {
            return;
        }

        Kv meta = builder.getConfig();
        String render = meta.getStr("render");
        if (!meta.containsKey("render") || StrKit.isBlank(render)) {
            return;
        }

        String snippet = ServiceManager.getSnippetService().getSnippet(render, true);
        builder.renderFn(snippet);
    }
}
