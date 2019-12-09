package com.hthjsj.analysis.component;

import com.jfinal.kit.Kv;
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

    @Getter
    @Setter
    FieldInject fieldInject = new FieldInject.DefaultFieldInject() {

        @Override
        public Kv inject(Kv meta) {
            return super.inject(meta);
        }
    };

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

//    public void setRender(ComponentRender<? extends Component> render) {
//        this.render = render;
//        //默认传入Render后 触发构建逻辑;
//        this.render.render();
//    }

    /**
     * 返回组件Kv数据,用来序列json用
     *
     * @return
     */
    public Kv toKv() {
        return meta;
    }
}
