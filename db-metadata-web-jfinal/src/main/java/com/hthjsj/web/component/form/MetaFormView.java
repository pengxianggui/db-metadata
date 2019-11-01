package com.hthjsj.web.component.form;

import com.alibaba.fastjson.JSON;
import com.hthjsj.analysis.meta.IMetaField;
import com.hthjsj.analysis.meta.MetaObject;
import com.hthjsj.web.ui.AccessBehavior;
import com.jfinal.kit.Kv;

import java.util.stream.Collectors;

/**
 * <p> @Date : 2019/10/30 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
public class MetaFormView extends FormView {

    MetaObject metaObject;

    Kv objectConfig;

    Kv fieldsConfig;

    public MetaFormView(MetaObject object, Kv objectConfig, Kv fieldsConfig) {
        this.metaObject = object;
        this.objectConfig = objectConfig;
        this.fieldsConfig = fieldsConfig;
        setAccessBehavior(new AccessBehavior.DefaultAccessBehavior(objectConfig));
        init();
    }

    private void init() {
        //1. init object
        name = metaObject.name();
        action = "/form/doAdd/" + metaObject.code();
        meta.set("name", "fuck");
        Kv kv = JSON.parseObject(objectConfig.getStr("config"), Kv.class);

        //https://blog.csdn.net/tangyaya8/article/details/91399650
        kv.forEach((k, v) -> meta.merge(k, v, (oldVal, newVal) -> oldVal));

        //2. foreach init fields
        Kv config = null;
        for (IMetaField metaField : metaObject.fields()) {
            config = JSON.parseObject(fieldsConfig.getStr(metaField.fieldCode()), Kv.class);
            FormField formField = FormFieldFactory.createFormField(metaField, config);
            //            AccessBehavior accessBehavior = formField.getAccessBehavior();
            //            if (accessBehavior.isAdd()) {
            //                fields.add(formField);
            //            }

            fields.add(formField);
        }
    }

    @Override
    public Kv toKv() {
        meta.putIfAbsent("methods", methods);
        meta.putIfAbsent("name", name);
        meta.putIfAbsent("action", action);
        meta.putIfAbsent("component_name", type());
        meta.putIfAbsent("columns", fields.stream().map(f -> f.toKv()).collect(Collectors.toList()));
        getViewInject().inject(this, meta, conf, getFieldInject());
        return meta;
    }
}
