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

    protected String cn = "表单视图";

    protected String en = "FormView";

    @Getter
    List<FormField> fields = new ArrayList<>();

    private String formName;

    private String action;

    private String methods;

    private Kv metaObjectConfig = Kv.create();

    private Kv componentConfig = Kv.create();

    public static FormView POST(String action, String formName) {
        FormView formView = new FormView();
        formView.methods = "POST";
        formView.action = action;
        formView.formName = formName;
        return formView;
    }

    @Override
    public String type() {
        return getClass().getSimpleName();
    }

    @Override
    public String config() {
        return renderMeta().toJson();
    }

    @Override
    public Kv renderMeta() {
        Kv kv = Kv.create();
        kv.putAll(metaObjectConfig);
        kv.putAll(componentConfig);
        kv.setIfNotBlank("methods", methods);
        kv.setIfNotBlank("form_name", formName);
        kv.setIfNotBlank("action", action);
        kv.set("columns", fields.stream().map((k) -> k.renderMeta()).collect(Collectors.toList()));
        //        kv.set("btn", Lists.newArrayList(new Button("submit").renderMeta(), new Button("reset").renderMeta()));
        return kv;
    }

    @Override
    public void config(String config) {

    }
}
