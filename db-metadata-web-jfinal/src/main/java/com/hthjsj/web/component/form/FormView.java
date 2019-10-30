package com.hthjsj.web.component.form;

import com.hthjsj.web.component.ComponentType;
import com.hthjsj.web.component.ViewComponent;
import com.jfinal.kit.Kv;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p> Class title: </p>
 * <p> @Describe: </p>
 * <p> @Date : 2019/10/14 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
public class FormView extends ViewComponent {

    protected String name;

    protected String action;

    protected String methods;

    protected Kv meta = Kv.create();

    protected Kv conf = Kv.create();

    @Getter
    List<FormField> fields = new ArrayList<>();

    public static FormView POST(String action, String name) {
        FormView formView = new FormView();
        formView.methods = "POST";
        formView.action = action;
        formView.name = name;
        return formView;
    }

    @Override
    public String config() {
        return meta.toJson();
    }

    @Override
    public ComponentType componentType() {
        return ComponentType.FORMVIEW;
    }

    @Override
    public Kv toKv() {
        meta.setIfNotBlank("methods", methods);
        meta.setIfNotBlank("name", name);
        meta.setIfNotBlank("action", action);
        meta.setIfNotBlank("component_name", type());
        meta.set("columns", fields.stream().map((k) -> k.toKv()).collect(Collectors.toList()));
        getInject().inject(this, meta, conf, getFieldInject());
        return meta;
    }
}
