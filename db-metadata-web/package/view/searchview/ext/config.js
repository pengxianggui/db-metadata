import {isObject} from "../../../utils/common";

export default {
    "TextBox": {
        "value": "%v%",
        "options": {
            "%v": "lk_l",
            "v%": "lk_r",
            "%v%": "lk"
        }
    },
    "BoolBox": {
        "value": "=",
        "options": {
            "=": "eq"
        }
    },
    "NumBox": {
        "value": "=",
        "optional": true,   // 供用户选择
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
        "options": {
            "in": "in"
        }
    },
    "DateBox": {
        "value": "range",
        "options": {
            "range": "range"
        }
    },
    "TimeBox": {
        "value": "range",
        "options": {
            "range": "range"
        }
    },
    "DateTimeBox": {
        "value": "range",
        "options": {
            "range": "range"
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
            case "in":
                value = Array.isArray(value) ? value.join(',') : value;
                params[name + 'in'] = value;
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
