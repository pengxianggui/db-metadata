package com.github.md.analysis.component;

import com.github.md.analysis.kit.Kv;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * 容器类Component的基类
 *
 * @author konbluesky
 */
public abstract class ViewContainer extends Component {

    private final List<Component> fields = new ArrayList<>(0);

    boolean isPreBuild = false;

    public ViewContainer(String name, String label) {
        this.name = name;
        this.label = label;
        setRender(new ManualRender<ViewContainer>(this));
    }

    @Override
    public ViewContainer getContainer() {
        return this;
    }

    public void add(Component component) {
        fields.add(component);
    }

    public void remove(Component component) {
        fields.remove(component);
    }

    public void remove(Predicate<Component> predicate) {
        fields.removeIf(predicate);
    }

    public List<Component> getFields() {
        return fields;
    }

    protected void renderCustomMeta(Kv meta) {

    }

    protected void renderFieldsMeta(List<Component> fields, Kv meta) {
        List<Kv> fieldsMetas = new ArrayList<>();
        for (Component field : fields) {
            //子元素自身渲染
            Kv fieldConf = field.toKv();
            fieldsMetas.add(fieldConf);
        }
        meta.set("columns", fieldsMetas);
    }

    @Override
    public Kv toKv() {
        renderCustomMeta(meta);

        //        if (!(getViewInject() instanceof ViewInject.DefaultViewInject)) {
        //            getViewInject().inject(this, meta, getFieldInject());
        //        } else {
        //            //如使用了Inject,就覆盖默认逻辑
        //            renderFieldsMeta(fields, meta);
        //        }
        /* 防止重复构建meta */
        if (!isPreBuild) {
            render.render();
        }

        if (!getFields().isEmpty()) {
            meta.set("columns", getFields().stream().map((k) -> k.toKv()).collect(Collectors.toList()));
        }
        return meta;
    }

    /**
     * build中触发构建动作,只生成容器中的Component,不生成meta中的json
     * 用于构建后,单独编辑个别控件
     *
     * @return
     */
    public ViewContainer buildChildren() {
        renderCustomMeta(meta);

        render.render();

        isPreBuild = true;
        return this;
    }
}
