package com.hthjsj.web.ui;

import com.jfinal.kit.Kv;

/**
 * <p> @Date : 2019/11/14 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
public class RenderHelper {

    /**
     * <pre>
     *     Data structure:
     *     {
     *         meta_object_code_abc:{config object},
     *         fields:[
     *              meta_field1:{config object},
     *              meta_field2:{config object},
     *              meta_field3:{config object},
     *         ]
     *     }
     * </pre>
     *
     * @param metaObjectViewAdapter
     *
     * @return
     */
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

    /**
     * <pre>
     *     Data structure:
     *     {
     *         meta_object_code_abc:{config object},
     *         meta_field1:{config object},
     *         meta_field2:{config object},
     *         meta_field3:{config object},
     *     }
     * </pre>
     *
     * @param metaObjectViewAdapter
     *
     * @return
     */
    public static Kv renderObjectFlatMap(MetaObjectViewAdapter metaObjectViewAdapter) {
        Kv kv = Kv.create();

        kv.set(metaObjectViewAdapter.getMetaObject().code(), metaObjectViewAdapter.getInstanceConfig().toJson());
        metaObjectViewAdapter.getFieldsMap().forEach((key, value) -> {
            kv.set(key, value.getInstanceConfig().toJson());
        });
        return kv;
    }
}
