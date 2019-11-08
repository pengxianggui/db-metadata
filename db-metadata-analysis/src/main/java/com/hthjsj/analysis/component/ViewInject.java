package com.hthjsj.analysis.component;

import com.hthjsj.analysis.meta.IMetaField;
import com.jfinal.kit.Kv;

/**
 * <p> @Date : 2019/10/21 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
public interface ViewInject<T> {

    void inject(T component, Kv meta, FieldInject<IMetaField> fieldInject);

    class DefaultViewInject<T> implements ViewInject<T> {

        @Override
        public void inject(T component, Kv meta, FieldInject<IMetaField> fieldInject) {
            meta.set(fieldInject.inject(meta));
        }
    }
}
