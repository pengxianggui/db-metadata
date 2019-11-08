package com.hthjsj.analysis.component;

import com.jfinal.kit.Kv;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author konbluesky
 */
public abstract class ViewContainer extends Component {

    @Getter
    @Setter
    ViewInject viewInject = new ViewInject.DefaultViewInject();

    private List<Component> fields = new ArrayList<>(0);

    public ViewContainer(String name, String label) {
        this.name = name;
        this.label = label;
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

    public List<Component> getFields() {
        return fields;
    }

    protected void renderCustomMeta(Kv meta) {

    }

    protected void renderFieldsMeta(List<Component> fields, Kv meta) {
        List<Kv> fieldsMetas = new ArrayList<>();
        for (Component field : fields) {
            field.setFieldInject(getFieldInject());
            //子元素自身渲染
            Kv fieldConf = field.toKv();
            fieldsMetas.add(fieldConf);
        }
        meta.set("columns", fieldsMetas);
    }

    @Override
    public Kv toKv() {
        renderCustomMeta(meta);
        if (!(getViewInject() instanceof ViewInject.DefaultViewInject)) {
            getViewInject().inject(this, meta, getFieldInject());
        } else {
            //如使用了Inject,就覆盖默认逻辑
            renderFieldsMeta(fields, meta);
        }
        return meta;
    }
}
