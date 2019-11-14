package com.hthjsj.web.ui;

import com.alibaba.fastjson.annotation.JSONField;
import com.hthjsj.analysis.component.Component;
import com.hthjsj.analysis.meta.IMetaObject;
import com.hthjsj.web.Utils;
import com.jfinal.kit.Kv;
import lombok.Getter;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * MetaObjectViewAdapter 同时持有metaObject,component,各层config
 * 方便逻辑流转运算;
 * <p> @Date : 2019/11/13 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
public class MetaObjectViewAdapter {

    @Getter
    IMetaObject metaObject;

    @Getter
    @JSONField(serialize = false)
    Component component;

    @Getter
    List<MetaFieldViewAdapter> fields;

    @Getter
    Kv objectConfig;

    @Getter
    Kv globalComponentConfig;

    @Getter
    Kv instanceConfig;

    @Getter
    Map<String, MetaFieldViewAdapter> fieldsMap;

    public MetaObjectViewAdapter(IMetaObject metaObject, Component component, Kv globalComponentConfig, List<MetaFieldViewAdapter> fields) {
        this.metaObject = metaObject;
        this.component = component;
        this.fields = fields;

        //init config
        this.objectConfig = Utils.getKv(metaObject.config());
        this.globalComponentConfig = globalComponentConfig;
        //TODO 来源变更
        this.instanceConfig = component.toKv();
        this.fieldsMap = fields.stream().collect(Collectors.toMap(metaFieldViewAdapter -> metaFieldViewAdapter.metaField.fieldCode(),
                                                                  metaFieldViewAdapter -> metaFieldViewAdapter));
    }

    /**
     * 获取元子段
     *
     * @param fieldCode
     *
     * @return
     */
    public MetaFieldViewAdapter getField(String fieldCode) {
        return fieldsMap.get(fieldCode);
    }
}
