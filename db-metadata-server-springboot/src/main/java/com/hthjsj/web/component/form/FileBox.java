package com.hthjsj.web.component.form;

import com.hthjsj.analysis.component.ComponentType;
import com.jfinal.kit.Kv;

/**
 * <p> @Date : 2019/12/4 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
public class FileBox extends FormField {

    public FileBox(String name, String label) {
        super(name, label);
    }

    @Override
    public ComponentType componentType() {
        return ComponentType.FILEBOX;
    }

    @Override
    public Kv toKv() {

        return render.render();
    }
}
