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

    private Kv globalConfig = Kv.create();

    private Kv metaObjectConfig = Kv.create();

    private Kv componentConfig = Kv.create();

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
        kv.putAll(globalConfig);
        kv.putAll(metaObjectConfig);
        kv.putAll(componentConfig);
        kv.put("fields", fields.stream().map((k) -> k.renderMeta()).collect(Collectors.toList()));
        return kv;
    }

    @Override
    public void config(String config) {

    }
}
