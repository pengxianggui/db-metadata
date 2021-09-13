package com.github.md.web.component.form;

import com.github.md.analysis.component.ComponentType;
import com.github.md.analysis.kit.Kv;

/**
 * <p> @Date : 2019/11/6 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
public class JsonBox extends FormField {

    public JsonBox(String name, String label) {
        super(name, label);
    }

    @Override
    public ComponentType componentType() {
        return ComponentType.JSONBOX;
    }

    @Override
    public Kv toKv() {

        return render.render();
    }
}
