import {isObject} from "../../../utils/common";

export default {
    "TextBox": {
        "value": "全匹配",
        "optional": true, // 是否显示操作符选项，开启时用户配置的 show-symbol-option 才生效
        "optionSlot": "prepend", // 选项位置的slot名
        "options": {
            "前匹配": "lk_r",
            "后匹配": "lk_l",
            "全匹配": "lk"
        }
    },
    "NumBox": {
        "value": "=",
        "optional": true,
        "optionSlot": "prepend",
        "options": {
            "=": "eq",
            "!=": "ne",
            ">": "gt",
            "<": "lt",
            ">=": "ge",
            "<=": "le"
        }
    },
    "DropDownBox": {
        "value": "in",
        "optional": false,
        "optionSlot": "prefix",
        "options": {
            "in": "in",
            "nin": "nin"
        }
    },
    "DateBox": {
        "value": "range",
        "optional": false,
        "options": {
            "range": "range"
        }
    },
    "TimeBox": {
        "value": "range",
        "optional": false,
        "options": {
            "range": "range"
        }
    },
    "DateTimeBox": {
        "value": "range",
        "optional": false,
        "options": {
            "range": "range"
        }
    },
    "BoolBox": {
        "value": "=",
        "optional": false,
        "options": {
            "=": "eq"
        }
    }
};

/**
 * 将搜索面板的对象数据转为表达式对象参数
 * @param model
 * @returns {{}}
 */
export const toParams = function (model= {}) {
    if (!isObject) {
        return {}
    }

    let params = {};
    for (let key in model) {
        let item = model[key];
        let name = key + "_";
        let value = item.value;
        let symbol = item.symbol;

        if (value == null || value.length == 0) continue;

        switch (symbol.value) {
            case "nin":
            case "in":
                value = Array.isArray(value) ? value.join(',') : value;
                params[name + symbol.value] = value;
                break;
            case "range":
                params[name + "gt"] = value[0];
                params[name + "lt"] = value[1];
                break;
            default:
                params[name + symbol.options[symbol.value]] = value;
        }
    }
    return params
}
