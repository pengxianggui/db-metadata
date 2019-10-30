package com.hthjsj.web.component;

import com.hthjsj.analysis.meta.Component;
import com.hthjsj.web.ui.AccessBehavior;
import com.jfinal.kit.Kv;
import lombok.Getter;
import lombok.Setter;

/**
 * @author konbluesky
 */
public abstract class ViewComponent implements Component, ComponentRender {

    @Getter
    @Setter
    ViewInject viewInject = new ViewInject.DefaultViewInject();

    @Getter
    @Setter
    FieldInject fieldInject = new FieldInject.DefaultFieldInject() {

        @Override
        public Kv inject(Kv meta, Kv conf) {
            return super.inject(meta, conf);
        }
    };

    @Getter
    @Setter
    AccessBehavior accessBehavior;

    /**
     * 用于获取Component最终的jsonString;
     *
     * @return
     */
    public abstract String config();

    @Override
    public String name() {
        return componentType().getName();
    }

    @Override
    public String code() {
        return componentType().getCode();
    }

    @Override
    public String type() {
        return componentType().getCode();
    }

    public abstract ComponentType componentType();
}
