import {camelCaseTo} from '@/utils/common.js'
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
    "BoolBox": {
        "ele": Checkbox,
        "includes": []
    },
    "CheckBox": {
        "ele": Checkbox,
        "includes": []
    },
    "DateBox": {
        "ele": DatePicker,
        "includes": []
    },
    "DateTimeBox": {
        "ele": DatePicker,
        "includes": []
    },
    "DropDownBox": {
        "ele": Select,
        "includes": []
    },
    "FileBox": {
        "ele": Upload,
        "includes": []
    },
    "FindBox": {
        "ele": Input,
        "includes": []
    },
    "ImgBox": {
        "ele": Upload,
        "includes": []
    },
    "NumBox": {
        "ele": InputNumber,
        "includes": []
    },
    "PassBox": {
        "ele": Input,
        "includes": []
    },
    "RadioBox": {
        "ele": Radio,
        "includes": []
    },
    "TextAreaBox": {
        "ele": Input,
        "includes": []
    },
    "TextBox": {
        "ele": Input,
        "includes": []
    },
    "TimeBox": {
        "ele": DatePicker,
        "includes": []
    },
    "FindPanel": {
        "ele": Table,
        "includes": []
    },
    "FormTmpl": {
        "ele": Form,
        "includes": []
    },
    "SearchPanel": {
        "ele": Form,
        "includes": []
    },
    "TableList": {
        "ele": Table,
        "includes": []
    },

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
export default function EleProps(componentName) {
    let elProps = {};
    if (!mapping.hasOwnProperty(componentName)) {
        console.warn("组件类型不正确,无法获取element原生属性信息. componentName: %s", componentName);
        return {};
    }
    let element = mapping[componentName];
    let elComponent = element['ele'];
    let includes = element['includes'];

    if (!elComponent.hasOwnProperty('props')) {
        return {};
    }

    for (let key in elComponent.props) {
        if (includes.indexOf(key) < 0)
            continue;
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
        elProps[camelCaseTo(key, '-')] = defaultV;
    }

    return elProps;
}