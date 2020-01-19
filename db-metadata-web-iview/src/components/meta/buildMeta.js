import utils from "@/utils";
import {DEFAULT} from "@/constant";
import TextBox from "@/components/core/textbox";
import NumBox from "@/components/core/numbox";
import BoolBox from "@/components/core/boolbox";
import DropDownBox from "@/components/core/dropdownbox";
import CodeBox from "@/components/core/codebox";
import JsonBox from "@/components/core/jsonbox";
import MiniFormBox from '@/components/core/miniformbox'

const specials = {
    "component_name": {
        component_name: DropDownBox.name,
        name: 'component_name',
        label: 'component_name',
        data_url: "/component/list",
        conf: {
            filterable: true
        }
    },
    "render": {
        component_name: CodeBox.name,
        name: 'render',
        label: 'render'
    }
};

function buildMetaByString(key, value) {
    return {
        component_name: TextBox.name,
        name: key,
        label: key,
        value: value
    }
}

function buildMetaByNumber(key, value) {
    return {
        component_name: NumBox.name,
        name: key,
        label: key,
        value: value
    }
}

function buildMetaByBoolean(key, value) {
    return {
        component_name: BoolBox.name,
        name: key,
        label: key,
        value: value
    }
}

function buildMetaByObject(key, value) {
    let defaultMeta;
    let meta = {
        name: key,
        label: key,
        value: value
    };

    // if (utils.isEmpty(value)) { // 空对象采用JsonBox展示
    //     defaultMeta = DEFAULT.JsonBox;
    //     meta.component_name = JsonBox.name;
    // } else {
    defaultMeta = DEFAULT.MiniFormBox;
    meta.component_name = MiniFormBox.name;
    meta.columns = [];
    let keys = Object.keys(value);
    for (let i = 0; i < keys.length; i++) {
        meta.columns.push(buildMeta(keys[i], value[keys[i]]));
    }
    // }
    return utils.merge(meta, defaultMeta);
}

function buildMetaByArray(key, value) {
    return {
        component_name: JsonBox.name,
        name: key,
        label: key,
        value: value
    }
}

function buildMeta(key, value) {
    if (utils.hasProp(specials, key)) {
        return specials[key];
    }
    const type = utils.typeOf(value);
    switch (type) {
        case "[object String]":
            return buildMetaByString(key, value);
        case "[object Number]":
            return buildMetaByNumber(key, value);
        case "[object Boolean]":
            return buildMetaByBoolean(key, value);
        case "[object Array]":
            return buildMetaByArray(key, value);
        case "[object Object]":
            return buildMetaByObject(key, value);
        default:
            return buildMetaByString(key, value);
    }
}

/**
 * value在前, 防止只传了value
 * @param value
 * @param key
 * @returns {*|{component_name, name, label, value}}
 */
export default function (value, key) {
    return buildMeta(key, value);
}