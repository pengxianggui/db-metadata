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

    static DropDownBox createDropDown(IMetaField metaField, Kv instanceFieldConfig) {
        DropDownBox dropDownBox = new DropDownBox(metaField.fieldCode(), metaField.cn());

        dropDownBox.setFieldInject(new FieldInject.DefaultFieldInject<IMetaField>() {

            @Override
            public Kv inject(Kv meta, Kv conf) {
                Kv kv = Kv.create();
                kv.set("conf", instanceFieldConfig.getOrDefault(metaField.en(), new Object()));
                return kv;
            }
        });
        return dropDownBox;
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

    static RadioBox createRadioBox(IMetaField metaField, Kv instanceFieldConfig) {
        RadioBox radioBox = new RadioBox(metaField.fieldCode(), metaField.cn());
        radioBox.setFieldInject(new FieldInject.DefaultFieldInject() {

            @Override
            public Kv inject(Kv meta, Kv conf) {
                Kv kv = Kv.create();
                kv.set("conf", instanceFieldConfig.getOrDefault(metaField.en(), new Object()));
                return kv;
            }
        });
        return radioBox;
    }

    static FormField createFormField(IMetaField metaField, Kv instanceFieldConfig) {
        ComponentType type = ComponentType.V(instanceFieldConfig.getStr("component_name"));
        switch (type) {
        case TEXTBOX:
            return createInputField(metaField, instanceFieldConfig);
        case DROPDOWN:
            return createDropDown(metaField, instanceFieldConfig);
        case RADIOBOX:
            return createRadioBox(metaField, instanceFieldConfig);
        default:
            break;
        }
        return null;
    }
}
