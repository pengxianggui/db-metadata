import utils from "../utils";
import TextBox from "../core/textbox";
import NumBox from "../core/numbox";
import BoolBox from "../core/boolbox";
import DropDownBox from "../core/dropdownbox";
import JsonBox from "../core/jsonbox";
import MiniFormBox from '../core/miniformbox'
import DefaultMiniFormBoxMeta from '../core/miniformbox/ui-conf'
import DefaultJsonBoxMeta from '../core/jsonbox/ui-conf'
import IconBox from "../core/iconbox/src/IconBox";
import Codebox from "../core/codebox";

/**
 * 定义各容器组件下的特殊的配置字段(包括容器组件和域组件)
 * @type {{TableTreeView: {render: {component_name: string, name: string, label: string, height: string}}, SearchView: {component_name: {component_name: string, name: string, conf: {filterable: boolean, disabled: boolean}, label: string, data_url: string}}, FormView: {component_name: {component_name: string, name: string, conf: {filterable: boolean, disabled: boolean}, label: string, data_url: string}}, TableView: {render: {component_name: string, name: string, label: string, height: string}}}}
 */
const specials = {
    "SearchView": {
        "component_name": {
            component_name: DropDownBox.name,
            name: 'component_name',
            label: '组件名',
            data_url: "/component/list?view=false",
            conf: {
                filterable: true,
                disabled: true
            }
        },
        "icon": {
            component_name: IconBox.name,
            name: 'icon',
            label: '图标'
        },
        "expand": {
            component_name: BoolBox.name,
            name: 'expand',
            label: '显示全部搜索项',
            explain: '若取消勾选，当搜索项超过1个时, 会收缩为只显示一个(可通过按钮展开)'
        }
    },
    "FormView": {
        "component_name": {
            component_name: DropDownBox.name,
            name: 'component_name',
            label: 'component_name',
            data_url: "/component/list?view=false",
            conf: {
                filterable: true,
                disabled: true
            }
        },
        "icon": {
            component_name: IconBox.name,
            name: 'icon',
            label: '图标'
        }
    },
    "TableView": {
        "render": {
            component_name: DropDownBox.name,
            name: 'render',
            label: '渲染函数',
            data_url: "/snippet/options",
            width: '100%',
            conf: {
                filterable: true,
                style: {
                    width: '100%'
                }
            }
        },
        "icon": {
            component_name: IconBox.name,
            name: 'icon',
            label: '图标'
        }
    },
    "TableTreeView": {
        "render": {
            component_name: DropDownBox.name,
            name: 'render',
            label: '渲染函数',
            data_url: "/snippet/options",
            width: '100%',
            conf: {
                filterable: true,
                style: {
                    width: '100%'
                }
            }
        }
    }
};

/**
 * 正则匹配的特殊
 * @type {{}}
 */
const regexSpecials = [
    {
        regex: /Fn$/,
        config: {
            component_name: Codebox.name,
            name: 'ok',
            label: '点选回调',
            width: '100%',
            height: "120px",
            'show-theme-chose': false,
            "show-mode-chose": false,
            "show-bar": false
        }
    }
]

function buildMetaByString(key, value) {
    return {
        component_name: TextBox.name,
        name: key,
        label: key,
        value: value,
        inline: false
    }
}

function buildMetaByNumber(key, value) {
    return {
        component_name: NumBox.name,
        name: key,
        label: key,
        value: value,
        inline: false
    }
}

function buildMetaByBoolean(key, value) {
    return {
        component_name: BoolBox.name,
        name: key,
        label: key,
        value: value,
        inline: false
    }
}

function buildMetaByObject(key, value, componentCode) {
    let defaultMeta;
    let meta = {
        name: key,
        label: key,
        value: value
    };

    if (utils.isEmpty(value)) { // 空对象采用JsonBox展示
        defaultMeta = DefaultJsonBoxMeta;
        meta.component_name = JsonBox.name;
    } else {
        defaultMeta = DefaultMiniFormBoxMeta;
        meta.component_name = MiniFormBox.name;
        meta.columns = [];
        let keys = Object.keys(value);
        for (let i = 0; i < keys.length; i++) {
            meta.columns.push(buildMeta(keys[i], value[keys[i]], componentCode));
        }
    }
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

/**
 * @param key
 * @param value
 * @param componentCode 组件编码
 * @returns {{inline: boolean, component_name: string, name, label, value}|{component_name: string, name, label, value}|*}
 */
function buildMeta(key, value, componentCode) {
    if (utils.hasProp(specials[componentCode], key)) {
        return utils.deepClone(specials[componentCode][key]);
    }

    const regexItem = regexSpecials.find(item => item.regex.test(key));
    if (regexItem) {
        const config = utils.deepClone(regexItem.config);
        config.label = key
        return config;
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
            return buildMetaByObject(key, value, componentCode);
        default:
            return buildMetaByString(key, value);
    }
}

/**
 * 根据一个值构建适配此值类型的 组件元数据。
 * value在前, 防止只传了value
 * @param value 值，必须
 * @param key 键名，此值对应的name，非必须。
 * @param viewComponentCode 容器组件名，表示构建返回的组件(元数据)是存在于哪个容器组件下。
 * @returns {*|{component_name, name, label, value}}
 */
export default function (value, key, viewComponentCode) {
    return buildMeta(key, value, viewComponentCode)
}
