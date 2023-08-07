package com.github.md.web.component.form;

import com.github.md.analysis.component.ComponentType;
import com.github.md.analysis.kit.Kv;

public class CodeBox extends FormField {

    public CodeBox(String name, String label) {
        super(name, label);
    }

    @Override
    public ComponentType componentType() {
        return ComponentType.CODEBOX;
    }

    @Override
    public Kv toKv() {
        return render.render();
    }
}
