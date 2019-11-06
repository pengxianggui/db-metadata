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

    static DropDownBox createDropDownBox(IMetaField metaField, Kv instanceFieldConfig) {
        DropDownBox dropDownBox = new DropDownBox(metaField.fieldCode(), metaField.cn());

        dropDownBox.setFieldInject(new FieldInject.DefaultFieldInject<IMetaField>() {

            @Override
            public Kv inject(Kv meta, Kv conf) {
                Kv kv = Kv.create().set(instanceFieldConfig);
                kv.forEach((k, v) -> meta.merge(k, v, (oldValue, newValue) -> oldValue));
                return kv;
            }
        });
        return dropDownBox;
    }

    static TextBox createTextBox(IMetaField metaField, Kv instanceFieldConfig) {
        TextBox textBox = new TextBox(metaField.fieldCode(), metaField.cn());
        textBox.setFieldInject(new FieldInject.DefaultFieldInject<IMetaField>() {

            @Override
            public Kv inject(Kv meta, Kv conf) {
                Kv kv = Kv.create().set(instanceFieldConfig);
                kv.forEach((k, v) -> meta.merge(k, v, (oldValue, newValue) -> oldValue));
                return kv;
            }
        });
        return textBox;
    }

    static RadioBox createRadioBox(IMetaField metaField, Kv instanceFieldConfig) {
        RadioBox radioBox = new RadioBox(metaField.fieldCode(), metaField.cn());
        radioBox.setFieldInject(new FieldInject.DefaultFieldInject<IMetaField>() {

            @Override
            public Kv inject(Kv meta, Kv conf) {
                Kv kv = Kv.create().set(instanceFieldConfig);
                kv.forEach((k, v) -> meta.merge(k, v, (oldValue, newValue) -> oldValue));
                return kv;
            }
        });
        return radioBox;
    }

    static NumberBox createNumberBox(IMetaField metaField, Kv instanceFieldConfig) {
        NumberBox numberBox = new NumberBox(metaField.fieldCode(), metaField.cn());
        numberBox.setFieldInject(new FieldInject.DefaultFieldInject<IMetaField>() {

            @Override
            public Kv inject(Kv meta, Kv conf) {
                Kv kv = Kv.create().set(instanceFieldConfig);
                kv.putAll(meta);
                return kv;
            }
        });
        return numberBox;
    }

    static BoolBox createBoolBox(IMetaField metaField, Kv instanceFieldConfig) {
        BoolBox boolBox = new BoolBox(metaField.fieldCode(), metaField.cn());
        boolBox.setFieldInject(new FieldInject.DefaultFieldInject<IMetaField>() {

            @Override
            public Kv inject(Kv meta, Kv conf) {
                Kv kv = Kv.create().set(instanceFieldConfig);
                kv.putAll(meta);
                return kv;
            }
        });
        return boolBox;
    }

    static TextAreaBox createTextAreaBox(IMetaField metaField, Kv instanceFieldConfig) {
        TextAreaBox textAreaBox = new TextAreaBox(metaField.fieldCode(), metaField.cn());
        textAreaBox.setFieldInject(new FieldInject.DefaultFieldInject<IMetaField>() {

            @Override
            public Kv inject(Kv meta, Kv conf) {
                Kv kv = Kv.create().set(instanceFieldConfig);
                kv.forEach((k, v) -> meta.merge(k, v, (oldValue, newValue) -> oldValue));
                return kv;
            }
        });
        return textAreaBox;
    }

    static DateBox createDateBox(IMetaField metaField, Kv instanceFieldConfig) {
        DateBox dateBox = new DateBox(metaField.fieldCode(), metaField.cn());
        dateBox.setFieldInject(new FieldInject.DefaultFieldInject<IMetaField>() {

            @Override
            public Kv inject(Kv meta, Kv conf) {
                Kv kv = Kv.create().set(instanceFieldConfig);
                kv.putAll(meta);
                return kv;
            }
        });
        return dateBox;
    }

    static TimeBox createTimeBox(IMetaField metaField, Kv instanceFieldConfig) {
        TimeBox timeBox = new TimeBox(metaField.fieldCode(), metaField.cn());
        timeBox.setFieldInject(new FieldInject.DefaultFieldInject<IMetaField>() {

            @Override
            public Kv inject(Kv meta, Kv conf) {
                Kv kv = Kv.create().set(instanceFieldConfig);
                kv.forEach((k, v) -> meta.merge(k, v, (oldValue, newValue) -> oldValue));
                return kv;
            }
        });
        return timeBox;
    }

    static DateTimeBox createDateTimeBox(IMetaField metaField, Kv instanceFieldConfig) {
        DateTimeBox dateTimeBox = new DateTimeBox(metaField.fieldCode(), metaField.cn());
        dateTimeBox.setFieldInject(new FieldInject.DefaultFieldInject<IMetaField>() {

            @Override
            public Kv inject(Kv meta, Kv conf) {
                Kv kv = Kv.create().set(instanceFieldConfig);
                kv.forEach((k, v) -> meta.merge(k, v, (oldValue, newValue) -> oldValue));
                return kv;
            }
        });
        return dateTimeBox;
    }

    /**
     * <pre>
     * 说明: 区别于createFormField 传入的配置 为全局
     *
     * instanceFieldConfig 数据格式
     *         [key]        [value]
     *      fieldCode -> meta_component.config
     * </pre>
     *
     * @param metaField
     * @param globalConfig
     *
     * @return
     */
    public static FormField createFormFieldDefault(IMetaField metaField, Kv globalConfig) {
        return createFormField(metaField, globalConfig);
    }

    /**
     * <pre>
     * 说明:
     * instanceFieldConfig 数据格式
     *         [key]        [value]
     *      fieldCode -> meta_component_instance.config
     * </pre>
     *
     * @param metaField
     * @param instanceFieldConfig
     *
     * @return
     */
    public static FormField createFormField(IMetaField metaField, Kv instanceFieldConfig) {
        //TODO bad small;
        if (instanceFieldConfig == null) {
            instanceFieldConfig = Kv.create();
        }
        ComponentType type = ComponentType.V(instanceFieldConfig.getStr("component_name"));
        switch (type) {
            case TEXTBOX:
                return createTextBox(metaField, instanceFieldConfig);
            case DROPDOWN:
                return createDropDownBox(metaField, instanceFieldConfig);
            case RADIOBOX:
                return createRadioBox(metaField, instanceFieldConfig);
            case NUMBERBOX:
                return createNumberBox(metaField, instanceFieldConfig);
            case BOOLBOX:
                return createBoolBox(metaField, instanceFieldConfig);
            case TEXTAREABOX:
                return createTextAreaBox(metaField, instanceFieldConfig);
            case DATEBOX:
                return createDateBox(metaField, instanceFieldConfig);
            case TIMEBOX:
                return createTimeBox(metaField, instanceFieldConfig);
            case DATETIMEBOX:
                return createDateTimeBox(metaField, instanceFieldConfig);
            default:
                break;
        }
        //if type == unknow  use TextBox
        return createTextBox(metaField, instanceFieldConfig);
    }
    //    class InstanceConfigFieldInject extends FieldInject.DefaultFieldInject{
    //        Kv instanceFieldConfig;
    //
    //        public InstanceConfigFieldInject(Kv instanceFieldConfig) {
    //            this.instanceFieldConfig = instanceFieldConfig;
    //        }
    //
    //        @Override
    //        public Kv inject(Kv meta, Kv conf) {
    //            Kv kv = Kv.create().set(instanceFieldConfig);
    //            kv.putAll(meta);
    //            return kv;
    //        }
    //    }
}
