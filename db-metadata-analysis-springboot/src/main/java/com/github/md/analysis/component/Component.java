package com.github.md.analysis.component;

import com.github.md.analysis.kit.Kv;
import lombok.Getter;
import lombok.Setter;

/**
 * @author konbluesky
 */
public abstract class Component {

    @Getter
    protected Kv meta = Kv.create();

    @Getter
    protected String name;

    @Getter
    protected String label;

    @Setter
    protected ComponentRender<? extends Component> render;

    @Setter
    protected ViewContainer viewContainer;

    public Component() {
    }

    public Component(String name, String label) {
        this.name = name;
        this.label = label;
    }

    public String name() {
        return componentType().getName();
    }

    public String code() {
        return componentType().getCode();
    }

    public String type() {
        return componentType().getCode();
    }

    public abstract ComponentType componentType();

    public abstract ViewContainer getContainer();

    /**
     * 返回组件Kv数据,用来序列json用
     *
     * @return
     */
    public Kv toKv() {
        return meta;
    }
}
