import Vue from 'vue';
import {
    Checkbox,
    Input,
    DatePicker,
    Select,
    Upload,
    InputNumber,
    Radio,
    Table,
    Form,
} from 'element-ui'

let mapping = {
    "BoolBox": Checkbox,
    "CheckBox": Checkbox,
    "DateBox": DatePicker,
    "DateTimeBox": DatePicker,
    "DropDownBox": Select,
    "FileBox": Upload,
    "FindBox": Input,
    "ImgBox":Upload,
    "NumBox": InputNumber,
    "PassBox": Input,
    "RadioBox": Radio,
    "TextAreaBox": Input,
    "TextBox": Input,
    "TimeBox": DatePicker,
    "FindPanel": Table,
    "FormTmpl": Form,
    "SearchPanel": Form,
    "TableList": Table,

    // custom...
    // "JsonBox": null,
    // "ZTogglePanel": null,
};


/**
 * 传入自定义组件类型, 获取其基于的element组件的props对象(prop属性和默认值, 参考egs)
 * @param componentName
 * @returns {*}
 * egs:
 * {
 *     placeholder: "",
 *     disabled: false,
 *     ...
 * }
 * @constructor
 */
export default function EleProps(componentName){
    let elProps = {};
    if (!mapping.hasOwnProperty(componentName)) {
        console.warn("组件类型不正确,无法获取element原生属性信息. componentName: %s", componentName);
        return {};
    }
    let elComponent = mapping[componentName];

    if (!elComponent.hasOwnProperty('props')) {
        return {};
    }

    for (let key in elComponent.props) {
        let obj = elComponent.props[key];
        let type = obj['type'];
        let defaultV;

        if (obj.hasOwnProperty('default')) {
             defaultV = obj['default'];
        } else {
            try {
                // a typeof prop must be Function Or Array, if it's array, use first Construct
                defaultV = (typeof type) === 'function' ? type.call(this) : type[0].call(this);
            } catch (e) {
                defaultV = null;
                console.log("element component[%o], the type of prop[%s] is: %o", elComponent, key, type);
            }
        }
        elProps[key] = defaultV;
    }

    return elProps;
}