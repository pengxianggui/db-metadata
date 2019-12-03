package com.hthjsj.web.ui;

import com.alibaba.fastjson.annotation.JSONField;
import com.hthjsj.analysis.component.Component;
import com.hthjsj.analysis.meta.IMetaField;
import com.hthjsj.web.UtilKit;
import com.jfinal.kit.Kv;
import lombok.Getter;

/**
 * <p> @Date : 2019/11/13 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
public class MetaFieldViewAdapter {

    @Getter
    IMetaField metaField;

    @Getter
    @JSONField(serialize = false)
    Component component;

    @Getter
    Kv fieldConfig;

    @Getter
    Kv globalComponentConfig;

    @Getter
    Kv instanceConfig;

    public MetaFieldViewAdapter(IMetaField metaField, Component component, Kv globalComponentConfig, Kv levelFieldInstanceConfig) {
        this.metaField = metaField;
        this.component = component;
        this.instanceConfig = levelFieldInstanceConfig;
        this.globalComponentConfig = globalComponentConfig;
        this.fieldConfig = UtilKit.getKv(metaField.config());
    }
}
