package com.hthjsj.web.component.form;

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

    @Getter
    List<FormField> fields = new ArrayList<>();

    private String formName;

    private String action;

    private String methods;

    private Kv meta = Kv.create();

    private Kv conf = Kv.create();

    public static FormView POST(String action, String name) {
        FormView formView = new FormView();
        formView.methods = "POST";
        formView.action = action;
        formView.formName = name;
        return formView;
    }

    @Override
    public String name() {
        return "表单视图";
    }

    @Override
    public String code() {
        return "table";
    }

    @Override
    public String type() {
        return "TableList";
    }

    @Override
    public String config() {
        return meta.toJson();
    }

    @Override
    public Kv toKv() {
        meta.setIfNotBlank("methods", methods);
        meta.setIfNotBlank("form_name", formName);
        meta.setIfNotBlank("action", action);
        meta.set("columns", fields.stream().map((k) -> k.toKv()).collect(Collectors.toList()));
        //        kv.set("btn", Lists.newArrayList(new Button("submit").renderMeta(), new Button("reset").renderMeta()));
        return conf;
    }
}
