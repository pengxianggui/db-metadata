package com.github.md.web.component.form;

import com.github.md.analysis.component.ComponentType;
import com.github.md.analysis.kit.Kv;

/**
 * <p> @Date : 2019/10/31 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
public class RadioBox extends FormField {

    public RadioBox(String name, String label) {
        super(name, label);
    }

    @Override
    public ComponentType componentType() {
        return ComponentType.RADIOBOX;
    }

    @Override
    public Kv toKv() {

        return render.render();
    }
}
