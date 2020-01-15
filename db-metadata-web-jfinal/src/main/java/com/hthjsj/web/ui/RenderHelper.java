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
     *         fieldsMap:{
     *              meta_field1:{config object},
     *              meta_field2:{config object},
     *              meta_field3:{config object}
     *         }
     *     }
     * </pre>
     *
     * @param metaObjectViewAdapter
     *
     * @return
     */
    public static ComponentInstanceConfig renderComponentInstanceConfig(MetaObjectViewAdapter metaObjectViewAdapter) {
        Kv objectConfig = Kv.by(metaObjectViewAdapter.getMetaObject().code(), metaObjectViewAdapter.getInstanceConfig().toJson());
        Kv fieldsMap = Kv.create();
        metaObjectViewAdapter.getFieldsMap().forEach((key, value) -> {
            fieldsMap.set(key, value.getFieldInstanceConfig().toJson());
        });
        return new ComponentInstanceConfig(objectConfig, fieldsMap);
    }
}
