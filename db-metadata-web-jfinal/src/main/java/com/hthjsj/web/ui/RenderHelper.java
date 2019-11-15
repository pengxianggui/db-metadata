package com.hthjsj.web.ui;

import com.jfinal.kit.Kv;

/**
 * <p> @Date : 2019/11/14 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
public class RenderHelper {

    public static Kv renderObjectFieldsMap(MetaObjectViewAdapter metaObjectViewAdapter) {
        Kv kv = Kv.create();

        kv.set(metaObjectViewAdapter.getMetaObject().code(), metaObjectViewAdapter.getInstanceConfig().toJson());
        Kv fields = Kv.create();
        metaObjectViewAdapter.getFieldsMap().forEach((key, value) -> {
            fields.set(key, value.getInstanceConfig().toJson());
        });
        kv.set("fields", fields);
        return kv;
    }

    public static Kv renderObjectFlatMap(MetaObjectViewAdapter metaObjectViewAdapter) {
        Kv kv = Kv.create();

        kv.set(metaObjectViewAdapter.getMetaObject().code(), metaObjectViewAdapter.getInstanceConfig().toJson());
        metaObjectViewAdapter.getFieldsMap().forEach((key, value) -> {
            kv.set(key, value.getInstanceConfig().toJson());
        });
        return kv;
    }
}
