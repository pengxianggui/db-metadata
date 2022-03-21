import utils from '../utils'
import {Checkbox, DatePicker, Form, Input, InputNumber, Radio, Select, Table, Tree, Upload,} from 'element-ui'
import {printWarn} from "../utils/common";

const mapping = {
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
    // custom...
    "JsonBox": {
        "ele": null,
        "includes": []
    },

    "FormView": {
        "ele": Form,
        "includes": []
    },
    "SearchView": {
        "ele": Form,
        "includes": []
    },
    "TableView": {
        "ele": Table,
        "includes": []
    },
    "TreeView": {
        "ele": Tree,
        "includes": []
    },
    "TableTreeView": {
        "ele": Table,
        "includes": []
    }
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
    if (!utils.isString(componentName)) return {};
    if (!utils.hasProp(mapping, componentName)) {
        printWarn(`组件类型不正确, 不存在此组件类型. componentName: ${componentName}`)
        return {};
    }

    let element = mapping[componentName];
    let {ele: elComponent} = element;
    if (utils.isEmpty(elComponent)) { // custom component
        return {};
    }

    let finalProps = {}, {props: elProps = {}} = elComponent;
    let includes = element['includes'];

    for (let key in elProps) {
        if (includes.indexOf(key) < 0)
            continue;

        let obj = elProps[key];
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
        finalProps[utils.camelCaseTo(key, '-')] = defaultV;
    }

    return finalProps;
}
