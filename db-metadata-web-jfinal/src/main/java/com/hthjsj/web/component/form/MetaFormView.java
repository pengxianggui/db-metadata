package com.hthjsj.web.component.form;

import com.alibaba.fastjson.JSON;
import com.hthjsj.analysis.meta.IMetaField;
import com.hthjsj.analysis.meta.MetaObject;
import com.hthjsj.web.component.FieldDataInject;
import com.hthjsj.web.component.ViewDataInject;
import com.hthjsj.web.ui.AccessBehavior;
import com.jfinal.kit.Kv;

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
        action = "/form/doAdd";

        //2. foreach init fields

        Kv config = null;
        for (IMetaField metaField : metaObject.fields()) {
            config = JSON.parseObject(fieldsConfig.getStr(metaField.fieldCode()), Kv.class);
            FormField formField = FormFieldFactory.createDropDown(metaField, config);
            AccessBehavior accessBehavior = formField.getAccessBehavior();
            if (accessBehavior.isAdd()) {
                fields.add(formField);
            }
        }
        setInject(new ViewDataInject<MetaFormView>() {

            @Override
            public void inject(MetaFormView component, Kv meta, Kv conf) {

            }

            @Override
            public FieldDataInject itemInject() {
                return null;
            }
        });
    }

    @Override
    public Kv toKv() {
        meta.setIfNotBlank("methods", methods);
        meta.setIfNotBlank("name", name);
        meta.setIfNotBlank("action", action);

        getInject().inject(this, meta, conf);
        return meta;
    }
}
