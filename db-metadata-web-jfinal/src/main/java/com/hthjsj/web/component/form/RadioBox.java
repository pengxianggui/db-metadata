package com.hthjsj.web.component.form;

import com.hthjsj.web.component.ComponentType;

/**
 * <p> @Date : 2019/10/31 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
public class RadioBox extends FormField {

    public RadioBox() {
    }

    public RadioBox(String name, String label) {
        super(name, label);
    }

    @Override
    public ComponentType componentType() {
        return ComponentType.RADIOBOX;
    }
}
