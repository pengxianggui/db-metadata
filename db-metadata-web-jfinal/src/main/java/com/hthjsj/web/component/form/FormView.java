package com.hthjsj.web.component.form;

import com.hthjsj.analysis.component.ComponentType;
import com.hthjsj.analysis.component.ManualRender;
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

    public FormView(String name, String label) {
        super(name, label);
        setRender(new ManualRender<FormView>(this));
    }

    public static FormView POST(String action, String name) {
        FormView formView = new FormView(action, name);
        formView.action = action;
        formView.name = name;
        return formView;
    }

    public FormView action(String url) {
        meta.setIfNotBlank("action", url);
        return this;
    }

    @Override
    public ComponentType componentType() {
        return ComponentType.FORMVIEW;
    }

    @Override
    protected void renderCustomMeta(Kv meta) {
        meta.putIfAbsent("name", name);
        //TODO
        meta.setIfNotBlank("action", action);
        meta.putIfAbsent("component_name", type());
    }

}
