// SearchPanel Conf: 各个字段的展示控件与搜索逻辑
export const SEARCH_PANEL_CONF = {
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