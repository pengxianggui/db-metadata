package com.github.md.web.component.form;

import com.github.md.analysis.component.ComponentType;
import com.github.md.analysis.kit.Kv;

/**
 * <p> @Date : 2019/11/14 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
public class FindBox extends FormField {

    public FindBox(String name, String label) {
        super(name, label);
    }

    @Override
    public ComponentType componentType() {
        return ComponentType.FINDBOX;
    }

    public FindBox dataUrl(String url) {
        meta.setIfNotBlank("data_url", url);
        return this;
    }

    @Override
    public Kv toKv() {

        return render.render();
    }
}
