package com.github.md.web.ui;

import com.alibaba.fastjson.annotation.JSONField;
import com.github.md.analysis.component.Component;
import com.github.md.analysis.meta.IMetaField;
import com.github.md.web.kit.UtilKit;
import com.github.md.analysis.kit.Kv;
import lombok.Getter;
import lombok.Setter;

/**
 * <p> @Date : 2019/11/13 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
public class MetaFieldViewAdapter {

    @Getter
    private final IMetaField metaField;

    @Getter
    @JSONField(serialize = false)
    private final Component component;

    @Getter
    private final Kv fieldConfig;

    @Getter
    private final Kv globalComponentConfig;

    @Getter
    private final Kv fieldInstanceConfig;

    @Setter
    @Getter
    private MetaObjectViewAdapter container;

    MetaFieldViewAdapter(IMetaField metaField, Component component, Kv globalComponentConfig, Kv levelFieldInstanceConfig) {
        this.metaField = metaField;
        this.component = component;
        this.fieldInstanceConfig = levelFieldInstanceConfig;
        this.globalComponentConfig = globalComponentConfig;
        this.fieldConfig = UtilKit.getKv(metaField.config());
    }
}
