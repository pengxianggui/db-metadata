package com.hthjsj.web.component;

import com.hthjsj.web.ui.AccessBehavior;
import com.jfinal.kit.Kv;

/**
 * <p> @Date : 2019/10/21 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
public interface ViewDataInject {

    void inject(Kv meta, Kv conf);

    FieldDataInject itemInject();

    AccessBehavior getBehavior();

    class DefaultViewDataInject implements ViewDataInject {

        @Override
        public void inject(Kv meta, Kv conf) {

        }

        @Override
        public FieldDataInject itemInject() {
            return null;
        }

        @Override
        public AccessBehavior getBehavior() {
            return null;
        }
    }
}
