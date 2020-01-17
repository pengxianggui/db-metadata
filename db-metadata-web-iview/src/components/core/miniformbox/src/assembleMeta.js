import utils from "@/utils";
import TextBox from '../../textbox'
import NumBox from "../../numbox"
import BoolBox from "../../boolbox"
import JsonBox from "../../jsonbox"
import MiniFormBox from './MiniFormBox'
import DropDownBox from "@/components/core/dropdownbox/src/DropDownBox";

const type2Component = function (type, value) {
    switch (type) {
        case "[object String]":
            return TextBox.name;
        case "[object Number]":
            return NumBox.name;
        case "[object Boolean]":
            return BoolBox.name;
        case "[object Object]":
            if (Object.keys(value).length === 0) {    // 空对象, 空对象时, 该值用JsonBox交互
                return JsonBox.name;
            }
            return MiniFormBox.name;
        case "[object Array]":
            return JsonBox.name;
        default:
            return TextBox.name;
    }
};

/**
 * @deprecated
 * @param componentName
 * @param name
 * @returns {{component_name: string, name: *, conf: {filterable: boolean}, label: *, data_url: string}|{mode: string, component_name: string, name: *, label: *}|{component_name: *, name: *, label: *}}
 */
const buildMeta = function (componentName, name) {
    if (name === 'component_name') {
        return {
            component_name: DropDownBox.name,
            name: name,
            label: name,
            data_url: '/component/list',
            conf: {
                filterable: true
            }
        }
    }

    if (name === 'conf') {
        return {
            component_name: MiniFormBox.name,
            name: name,
            label: name,
            mode: 'form'
        }
    }

    if (name === 'rules') {
        return {
            component_name: JsonBox.name,
            name: name,
            label: name,
            mode: 'form'
        }
    }

    return {
        component_name: componentName,
        name: name,
        label: name
    }
};

export default function (mergedMeta) {
    const {value} = this;

    if (!utils.isObject(value)) {
        return mergedMeta;
    }

    if (!utils.isEmpty(mergedMeta.columns)) { // mergedMeta存在对value中键值的meta定义时, 系统不进行干预
        return mergedMeta;
    }

    // 否则, 系统根据value中的键值对, 自行构建控件meta
    let columns = [];
    for (let key in value) {
        let type = utils.typeOf(value[key]);
        let component_name = type2Component(type, value[key]);

        columns.push({
            component_name: component_name,
            name: key,
            label: key
        });
    }

    this.$set(mergedMeta, 'columns', columns);
    return mergedMeta;
}