package com.github.md.web.component.form;

import com.github.md.web.component.render.FreeFormFieldRender;
import com.github.md.web.component.render.FreeViewRender;
import com.github.md.web.component.render.MetaFormFieldRender;
import com.github.md.analysis.component.ComponentType;
import com.github.md.analysis.component.ViewContainer;
import com.github.md.analysis.meta.IMetaField;
import com.github.md.analysis.kit.Kv;

/**
 * <p> @Date : 2019/10/30 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
public class FormFieldFactory {

    static <T extends FormField> T create(T formField, IMetaField metaField, Kv instanceFieldConfig) {
        formField.setRender(new MetaFormFieldRender<T>(metaField, formField, instanceFieldConfig));
        return formField;
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
     * @param fieldInstanceConfig
     * @return
     */
    public static FormField createFormFieldDefault(IMetaField metaField, Kv fieldInstanceConfig) {
        fieldInstanceConfig.set("name", metaField.fieldCode()).set("label", metaField.cn());
        return createFormField(metaField, fieldInstanceConfig);
    }

    /**
     * 创建带容器的FormField
     *
     * @param metaField
     * @param fieldInstanceConfig
     * @param viewContainer
     * @return
     */
    public static FormField createFormFieldInContainer(IMetaField metaField, Kv fieldInstanceConfig, ViewContainer viewContainer) {
        FormField formField = createFormField(metaField, fieldInstanceConfig);
        formField.setViewContainer(viewContainer);
        return formField;
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
     * @param instanceFieldConfig 假定所有列都有对应存在的配置
     * @return
     */
    public static FormField createFormField(IMetaField metaField, Kv instanceFieldConfig) {
        ComponentType type = ComponentType.V(instanceFieldConfig.getStr("component_name"));
        TextBox textBox = new TextBox(metaField.fieldCode(), metaField.cn());
        switch (type) {
            case TEXTBOX:
                return create(textBox, metaField, instanceFieldConfig);
            case CHECKBOX:
                CheckBox checkBox = new CheckBox(metaField.fieldCode(), metaField.cn());
                return create(checkBox, metaField, instanceFieldConfig);
            case DROPDOWN:
                DropDownBox dropDownBox = new DropDownBox(metaField.fieldCode(), metaField.cn());
                return create(dropDownBox, metaField, instanceFieldConfig);
            case RADIOBOX:
                RadioBox radioBox = new RadioBox(metaField.fieldCode(), metaField.cn());
                return create(radioBox, metaField, instanceFieldConfig);
            case NUMBERBOX:
                NumberBox numberBox = new NumberBox(metaField.fieldCode(), metaField.cn());
                return create(numberBox, metaField, instanceFieldConfig);
            case BOOLBOX:
                BoolBox boolBox = new BoolBox(metaField.fieldCode(), metaField.cn());
                return create(boolBox, metaField, instanceFieldConfig);
            case TEXTAREABOX:
                TextAreaBox textAreaBox = new TextAreaBox(metaField.fieldCode(), metaField.cn());
                return create(textAreaBox, metaField, instanceFieldConfig);
            case DATEBOX:
                DateBox dateBox = new DateBox(metaField.fieldCode(), metaField.cn());
                return create(dateBox, metaField, instanceFieldConfig);
            case TIMEBOX:
                TimeBox timeBox = new TimeBox(metaField.fieldCode(), metaField.cn());
                return create(timeBox, metaField, instanceFieldConfig);
            case DATETIMEBOX:
                DateTimeBox dateTimeBox = new DateTimeBox(metaField.fieldCode(), metaField.cn());
                return create(dateTimeBox, metaField, instanceFieldConfig);
            case JSONBOX:
                JsonBox jsonBox = new JsonBox(metaField.fieldCode(), metaField.cn());
                return create(jsonBox, metaField, instanceFieldConfig);
            case MINIFORMBOX:
                MiniFormBox miniFormBox = new MiniFormBox(metaField.fieldCode(), metaField.cn());
                return create(miniFormBox, metaField, instanceFieldConfig);
            case FILEBOX:
                FileBox fileBox = new FileBox(metaField.fieldCode(), metaField.cn());
                return create(fileBox, metaField, instanceFieldConfig);
            case FINDBOX:
                FindBox findBox = new FindBox(metaField.fieldCode(), metaField.cn());
                return create(findBox, metaField, instanceFieldConfig);
            case IMAGEBOX:
                ImageBox imageBox = new ImageBox(metaField.fieldCode(), metaField.cn());
                return create(imageBox, metaField, instanceFieldConfig);
            default:
                break;
        }
        //if type == unknow  use TextBox
        return create(textBox, metaField, instanceFieldConfig);
    }

    /**
     * 将meta反向构造formField对象
     *
     * @param meta
     * @return
     */
    public static FormField createFormField(Kv meta) {
        FormField formField;

        ComponentType type = ComponentType.V(meta.getStr("component_name"));

        switch (type) {
            case CHECKBOX:
                formField = new CheckBox(meta.getStr("name"), meta.getStr("label"));
                break;
            case DROPDOWN:
                formField = new DropDownBox(meta.getStr("name"), meta.getStr("label"));
                break;
            case RADIOBOX:
                formField = new RadioBox(meta.getStr("name"), meta.getStr("label"));
                break;
            case NUMBERBOX:
                formField = new NumberBox(meta.getStr("name"), meta.getStr("label"));
                break;
            case BOOLBOX:
                formField = new BoolBox(meta.getStr("name"), meta.getStr("label"));
                break;
            case TEXTAREABOX:
                formField = new TextAreaBox(meta.getStr("name"), meta.getStr("label"));
                break;
            case DATEBOX:
                formField = new DateBox(meta.getStr("name"), meta.getStr("label"));
                break;
            case TIMEBOX:
                formField = new TimeBox(meta.getStr("name"), meta.getStr("label"));
                break;
            case DATETIMEBOX:
                formField = new DateTimeBox(meta.getStr("name"), meta.getStr("label"));
                break;
            case JSONBOX:
                formField = new JsonBox(meta.getStr("name"), meta.getStr("label"));
                break;
            case MINIFORMBOX:
                formField = new MiniFormBox(meta.getStr("name"), meta.getStr("label"));
                break;
            case FILEBOX:
                formField = new FileBox(meta.getStr("name"), meta.getStr("label"));
                break;
            case FINDBOX:
                formField = new FindBox(meta.getStr("name"), meta.getStr("label"));
                break;
            case IMAGEBOX:
                formField = new ImageBox(meta.getStr("name"), meta.getStr("label"));
                break;
            case TEXTBOX:
            default:
                formField = new TextBox(meta.getStr("name"), meta.getStr("label"));
                break;
        }

        FreeFormFieldRender render = new FreeFormFieldRender<>(formField, meta);
        render.render(); // 直接渲染将render中的meta数据反向填充到formField
        formField.setRender(render);
        return formField;
    }
}
