package com.github.md.web.component.form;

import com.github.md.web.component.ComponentException;
import com.github.md.analysis.component.ComponentType;
import com.github.md.analysis.component.ManualRender;
import com.github.md.analysis.component.ViewContainer;
import com.github.md.analysis.kit.Kv;

/**
 * <p> Class title: </p>
 * <p> @Describe: </p>
 * <p> @Date : 2019/10/14 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
public class FormView extends ViewContainer {

    private static final String ADD = "add";

    private static final String UPDATE = "update";

    private static final String VIEW = "view";

    String action;

    String formType;

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

    /**
     * name -> fieldCode
     *
     * @param name
     *
     * @return
     */
    public FormField getField(String name) {
        FormField field = (FormField) getFields().stream().filter(f -> f.getName().equalsIgnoreCase(name)).findFirst().get();
        if (field == null) {
            throw new ComponentException("FormView 实例中不包含 %s 组件", name);
        }
        return field;
    }

    public FormView action(String url) {
        action = url;
        meta.setIfNotBlank("action", url);
        return this;
    }

    public boolean isAdd() {
        return ADD.equalsIgnoreCase(formType);
    }

    public boolean isUpdate() {
        return UPDATE.equalsIgnoreCase(formType);
    }

    public boolean isView() {
        return VIEW.equalsIgnoreCase(formType);
    }

    public FormView addForm() {
        this.formType = ADD;
        return this;
    }

    public FormView updateForm() {
        this.formType = UPDATE;
        return this;
    }

    public FormView viewForm() {
        this.formType = VIEW;
        return this;
    }

    @Override
    public ComponentType componentType() {
        return ComponentType.FORMVIEW;
    }

    @Override
    protected void renderCustomMeta(Kv meta) {
        meta.putIfAbsent("name", name);
        meta.setIfNotBlank("form_type", formType);
        meta.setIfNotBlank("action", action);
        meta.putIfAbsent("component_name", type());
    }

    public enum FormType {
        ADD, UPDATE, VIEW
    }
}
