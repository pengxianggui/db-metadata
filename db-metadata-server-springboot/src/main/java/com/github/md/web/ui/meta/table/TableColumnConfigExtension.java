package com.github.md.web.ui.meta.table;

import com.github.md.analysis.component.ComponentType;
import com.github.md.analysis.meta.ConfigExtension;
import com.github.md.analysis.meta.IMetaField;
import com.github.md.web.component.attr.AttributeBuilder;

/**
 * @author pengxg
 * @date 2022/4/10 10:02 上午
 */
public class TableColumnConfigExtension implements ConfigExtension<IMetaField, AttributeBuilder.FatAttributeBuilder, ComponentType> {

    @Override
    public void config(IMetaField metaField, AttributeBuilder.FatAttributeBuilder builder, ComponentType type) {
        builder.showOverflowTooltip(true);
        builder.align("center");
        builder.headerAlign("center");
        builder.width("auto");
        builder.minWidth("30px");
        builder.renderFn("");
    }
}
