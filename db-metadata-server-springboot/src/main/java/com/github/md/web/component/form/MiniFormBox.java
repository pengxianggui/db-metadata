package com.github.md.web.component.form;

import com.github.md.analysis.component.ComponentType;
import com.github.md.analysis.kit.Kv;

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
        return ComponentType.MINIFORMBOX;
    }

    @Override
    public Kv toKv() {

        return render.render();
    }
}
