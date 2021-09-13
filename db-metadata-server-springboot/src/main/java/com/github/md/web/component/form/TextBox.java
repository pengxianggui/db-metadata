package com.github.md.web.component.form;

import com.github.md.analysis.component.ComponentType;
import com.github.md.analysis.kit.Kv;

/**
 * <p> Class title: </p>
 * <p> @Describe: </p>
 * <p> @Date : 2019/10/16 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
public class TextBox extends FormField {

    public TextBox(String name, String label) {
        super(name, label);
    }

    @Override
    public ComponentType componentType() {
        return ComponentType.TEXTBOX;
    }

    @Override
    public Kv toKv() {

        return render.render();
    }
}
