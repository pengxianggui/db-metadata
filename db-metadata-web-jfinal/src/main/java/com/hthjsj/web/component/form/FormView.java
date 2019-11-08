package com.hthjsj.web.component.form;

import com.hthjsj.analysis.component.ComponentType;
import com.hthjsj.analysis.component.ViewContainer;
import com.jfinal.kit.Kv;

/**
 * <p> Class title: </p>
 * <p> @Describe: </p>
 * <p> @Date : 2019/10/14 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
public class FormView extends ViewContainer {

    protected String action;

    protected String methods;

    public FormView(String name, String label) {
        super(name, label);
    }

    public static FormView POST(String action, String name) {
        FormView formView = new FormView(action, name);
        formView.methods = "POST";
        formView.action = action;
        formView.name = name;
        return formView;
    }

    @Override
    public ComponentType componentType() {
        return ComponentType.FORMVIEW;
    }

    @Override
    protected void renderCustomMeta(Kv meta) {
        meta.putIfAbsent("methods", methods);
        meta.putIfAbsent("name", name);
        meta.putIfAbsent("action", action);
        meta.putIfAbsent("component_name", type());
    }

    @Override
    public Kv toKv() {
        //        meta.putIfAbsent("columns", getFields().stream().map((k) -> k.toKv()).collect(Collectors.toList()));
        //        getViewInject().inject(this, meta, getFieldInject());
        return super.toKv();
    }
}
