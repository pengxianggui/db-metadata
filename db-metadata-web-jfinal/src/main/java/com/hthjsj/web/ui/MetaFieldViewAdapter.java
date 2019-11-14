package com.hthjsj.web.ui;

import com.alibaba.fastjson.annotation.JSONField;
import com.hthjsj.analysis.component.Component;
import com.hthjsj.analysis.meta.IMetaField;
import com.hthjsj.web.Utils;
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
    Kv componentGlobalConfig;

    @Getter
    Kv instanceConfig;

    public MetaFieldViewAdapter(IMetaField metaField, Component component) {
        this.metaField = metaField;
        this.component = component;
        // init config
        //TODO instanceConfig的来源 要变更
        this.instanceConfig = component.toKv();
        this.componentGlobalConfig = Kv.create();
        this.fieldConfig = Utils.getKv(metaField.config());
    }
}
