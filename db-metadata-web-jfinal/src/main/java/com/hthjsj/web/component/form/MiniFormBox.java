package com.hthjsj.web.component.form;

import com.hthjsj.analysis.component.ComponentType;
import com.jfinal.kit.Kv;

/**
 * @author pengxg
 * @date
 */
public class MiniFormBox extends FormField {

    public MiniFormBox(String name, String label) {
        super(name, label);
    }

    @Override
    public ComponentType componentType() {
        return ComponentType.MiniFormBox;
    }

    @Override
    public Kv toKv() {
        getFieldInject().inject(meta);
        return render.render();
    }

}
