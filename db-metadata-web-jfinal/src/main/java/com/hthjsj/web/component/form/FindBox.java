package com.hthjsj.web.component.form;

import com.hthjsj.analysis.component.ComponentType;

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
}
