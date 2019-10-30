package com.hthjsj.web.component.form;

import com.hthjsj.analysis.meta.IMetaField;
import com.hthjsj.web.component.ComponentType;
import com.jfinal.kit.Kv;

/**
 * <p> @Date : 2019/10/30 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
public class FormFieldFactory {

    static DropDown createDropDown(IMetaField metaField, Kv instanceFieldConfig) {
        return new DropDown();
    }

    static InputField createInputField(IMetaField metaField, Kv instanceFieldConfig) {
        return new InputField();
    }

    static FormField createFormField(String componentName, IMetaField metaField, Kv instanceFieldConfig) {
        ComponentType type = ComponentType.V(componentName);
        switch (type) {
        case INPUTFIELD:
            return createInputField(metaField, instanceFieldConfig);
        case DROPDOWN:
            return createDropDown(metaField, instanceFieldConfig);
        default:
            break;
        }
        return null;
    }
}
