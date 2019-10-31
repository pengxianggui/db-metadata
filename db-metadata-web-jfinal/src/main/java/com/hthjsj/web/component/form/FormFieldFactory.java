package com.hthjsj.web.component.form;

import com.hthjsj.analysis.meta.IMetaField;
import com.hthjsj.web.component.ComponentType;
import com.hthjsj.web.component.FieldInject;
import com.jfinal.kit.Kv;

/**
 * <p> @Date : 2019/10/30 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
public class FormFieldFactory {

    static DropDown createDropDown(IMetaField metaField, Kv instanceFieldConfig) {
        DropDown dropDown = new DropDown(metaField.fieldCode(), metaField.cn());

        dropDown.setFieldInject(new FieldInject.DefaultFieldInject<IMetaField>() {

            @Override
            public Kv inject(Kv meta, Kv conf) {
                Kv kv = Kv.create();
                kv.set("conf", instanceFieldConfig.getOrDefault(metaField.en(), new Object()));
                return kv;
            }
        });
        return dropDown;
    }

    static TextBox createInputField(IMetaField metaField, Kv instanceFieldConfig) {
        TextBox textBox = new TextBox(metaField.fieldCode(), metaField.cn());
        textBox.setFieldInject(new FieldInject.DefaultFieldInject() {

            @Override
            public Kv inject(Kv meta, Kv conf) {
                Kv kv = Kv.create();
                kv.set("conf", instanceFieldConfig.getOrDefault(metaField.en(), new Object()));
                return kv;
            }
        });
        return textBox;
    }

    static FormField createFormField(IMetaField metaField, Kv instanceFieldConfig) {
        ComponentType type = ComponentType.V(instanceFieldConfig.getStr("component_name"));
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
