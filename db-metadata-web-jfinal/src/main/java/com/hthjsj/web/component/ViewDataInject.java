package com.hthjsj.web.component;

import com.jfinal.kit.Kv;

/**
 * <p> @Date : 2019/10/21 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
public interface ViewDataInject<T> {

    void inject(T component, Kv meta, Kv conf);

    FieldDataInject itemInject();

    class DefaultViewDataInject<T> implements ViewDataInject<T> {

        @Override
        public void inject(T component, Kv meta, Kv conf) {

        }

        @Override
        public FieldDataInject itemInject() {
            return null;
        }
    }
}
