import utils from "@/utils";
import TextBox from '../../textbox'
import NumBox from "../../numbox"
import BoolBox from "../../boolbox"
import JsonBox from "../../jsonbox"
import MiniFormBox from './MiniFormBox'
import DropDownBox from "@/components/core/dropdownbox/src/DropDownBox";

const type2Component = function (type) {
    switch (type) {
        case "[object String]":
            return TextBox.name;
        case "[object Number]":
            return NumBox.name;
        case "[object Boolean]":
            return BoolBox.name;
        case "[object Object]":
            return MiniFormBox.name;
        case "[object Array]":
            return JsonBox.name;
        default:
            return TextBox.name;
    }
};

const buildMeta = function (componentName, name) {
    // pxg_todo 待完善, 类似于name如果是'component_name', 则给一个完美的meta

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

    let columns = [];
    for (let key in value) {
        let type = utils.typeOf(value[key]);
        let component_name = type2Component(type);

        columns.push(buildMeta(component_name, key));
    }

    this.$set(mergedMeta, 'columns', columns);
    return mergedMeta;
}