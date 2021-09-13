package com.github.md.web.component.form;

import com.github.md.analysis.component.ComponentType;
import com.github.md.analysis.kit.Kv;

/**
 * @author pengxg
 * @date 2021/2/2 10:40 上午
 */
public class PassBox extends FormField{

    public PassBox(String name, String label) {
        super(name, label);
    }

    @Override
    public ComponentType componentType() {
        return ComponentType.PASSBOX;
    }

    @Override
    public Kv toKv() {
        return render.render();
    }
}
