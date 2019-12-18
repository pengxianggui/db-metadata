package com.hthjsj.web.ui;

import com.alibaba.fastjson.annotation.JSONField;
import com.google.common.collect.Maps;
import com.hthjsj.analysis.component.Component;
import com.hthjsj.analysis.meta.IMetaObject;
import com.hthjsj.web.kit.UtilKit;
import com.jfinal.kit.Kv;
import lombok.Getter;

import java.util.List;
import java.util.Map;

/**
 * MetaObjectViewAdapter 同时持有metaObject,component,各层config 实例
 * 方便逻辑流转运算;
 * <p> @Date : 2019/11/13 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
public class MetaObjectViewAdapter {

    @Getter
    private IMetaObject metaObject;

    @Getter
    @JSONField(serialize = false)
    private Component component;

    @Getter
    private List<MetaFieldViewAdapter> fields;

    @Getter
    private Kv objectConfig;

    @Getter
    private Kv globalComponentConfig;

    @Getter
    private Kv instanceConfig;

    @Getter
    private Map<String, MetaFieldViewAdapter> fieldsMap;

    MetaObjectViewAdapter(IMetaObject metaObject, Component component, Kv globalComponentConfig, Kv levelObjectInstanceConfig, List<MetaFieldViewAdapter> fields) {
        this.metaObject = metaObject;
        this.component = component;
        this.fields = fields;

        //init config
        this.objectConfig = UtilKit.getKv(metaObject.config());
        this.globalComponentConfig = globalComponentConfig;
        this.instanceConfig = levelObjectInstanceConfig;
        this.fieldsMap = Maps.newHashMap();
        for (MetaFieldViewAdapter metaFieldViewAdapter : fields) {
            metaFieldViewAdapter.setContainer(this);
            fieldsMap.put(metaFieldViewAdapter.getMetaField().fieldCode(), metaFieldViewAdapter);
        }
    }

    /**
     * 获取元子段
     *
     * @param fieldCode
     *
     * @return
     */
    public MetaFieldViewAdapter getFieldAdapter(String fieldCode) {
        return fieldsMap.get(fieldCode);
    }
}
