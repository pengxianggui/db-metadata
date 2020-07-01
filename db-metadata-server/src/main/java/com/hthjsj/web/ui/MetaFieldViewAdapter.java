package com.hthjsj.web.ui;

import com.alibaba.fastjson.annotation.JSONField;
import com.hthjsj.analysis.component.Component;
import com.hthjsj.analysis.meta.IMetaField;
import com.hthjsj.web.kit.UtilKit;
import com.jfinal.kit.Kv;
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
    private IMetaField metaField;

    @Getter
    @JSONField(serialize = false)
    private Component component;

    @Getter
    private Kv fieldConfig;

    @Getter
    private Kv globalComponentConfig;

    @Getter
    private Kv fieldInstanceConfig;

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
