import {PAGE_NUM_AREA} from './constant'
// 组件的默认UI配置
const DEFAULT = {
    BoolBox: {
        name: "BoolBox",
        label: "布尔框",
        component_name: "BoolBox",
        conf: {
        }
    },
    TextBox: {
        name: "TextBox",
        label: "文本框",
        component_name: "TextBox",
        conf: {
            "placeholder": "请输入内容..",
            "clearable": true
        }
    },
    PassBox: {
        name: "PassBox",
        label: "密码框",
        component_name: "PassBox",
        conf: {
            "placeholder": "请输入密码..",
            "clearable": true
        }
    },
    TextAreaBox: {
        name: "TextAreaBox",
        label: "文本域",
        component_name: "TextAreaBox",
        conf: {
            "placeholder": "请输入文本内容..",
            "clearable": true
        }
    },
    DropDownBox: {
        name: "DropDownBox",
        label: "下拉框",
        component_name: "DropDownBox",
        group: false,
        conf: {
            "clearable": true
        }
    },
    TimeBox: {
        name: "TimeBox",
        label: "时间框",
        component_name: "TimeBox",
        conf: {
            "value-format": "HH:mm:ss",
            "clearable": true
        }
    },
    DateBox: {
        name: "DateBox",
        label: "日期框",
        component_name: "DateBox",
        conf: {
            "value-format": "yyyy-MM-dd",
            "clearable": true
        }
    },
    DateTimeBox: {
        name: "DateTimeBox",
        label: "日期时间框",
        component_name: "DateTimeBox",
        conf: {
            "value-format": "yyyy-MM-dd HH:mm:ss",
            "clearable": true
        }
    },
    NumBox: {
        name: "NumBox",
        label: "数字框",
        component_name: "NumBox",
        conf: {
            "controls": false, // 是否使用控制按钮
            "placeholder": "请输入数值..",
        }
    },
    RadioBox: {
        name: "RadioBox",
        label: "单选框",
        component_name: "RadioBox",
        group: false,
        data_url: "", // 字典url(返回 [{key:value}, ..])
        conf: {
        }
    },
    CheckBox: {
        name: "CheckBox",
        label: "多选框",
        component_name: "CheckBox",
        data_url: "", // 字典url(返回 [{key:value}, ..])
        conf: {
        }
    },
    JsonBox: {
        name: "JsonBox",
        label: "Json框",
        component_name: "JsonBox",
        mode: "text",
        modes: ["code", "tree", "text", "view", "form"],
        conf: {
        }
    },
    ImgBox: {
        name: "ImgBox",
        label: "图片上传框",
        component_name: "ImgBox",
        conf: {
            "action": "/upload/img",
            "drag": false,
            "list-type": "picture-card",
            "auto-upload": false,
            "accept": "image"
        }
    },
    FileBox: {
        name: "FileBox",
        label: "文件上传框",
        conf: {
            "action": "/upload/file",
            "limit": 5,
            "tip": "上传文件限制不超过2M",
            "auto-upload": false
        }
    },
    ZTogglePanel: {
        name: "ZTogglePanel",
        label: "收缩面板",
        component_name: "ZTogglePanel",
        default_open: false,
        olabel: "收起",
        clabel: "展开",
    },
    FormTmpl: {
        name: "FormTmpl",
        label: "表单模板",
        component_name: "FormTmpl",
        action: "/form/doAdd/{objectCode}", // form action (url)
        conf: {
            "label-width": '100px',
            "size": 'medium', // medium|small|mini
            "rules": {
                // eg:
                // "id": [{required: true, message: "必填字段", trigger: "blur"}],
                // ...
            },
            // ...
        },
        columns: [
        ],
        // eg:
        // {
        //     name: 'id',
        //     label: 'ID',
        //     component_name: 'TextBox',
        //     inline: false,
        //     conf: {
        //         clearable: true,
        //         placeholder: "请输入..",
        //         // ...
        //     }
        // }
        btns: {
            "submit": {
                "label": "提交",
                "conf": {
                    // ... support conf of el-button
                    "type": "primary"
                }
            },
            "cancel": {
                "label": '取消',
                "conf": {
                    // ... support conf of el-button
                }
            }
        }
    },
    TableList: {
        name: "TableList",
        label: "表格模板",
        component_name: "TableList",
        methods: "GET",
        data_url: "/table/list", // required
        // ...
        conf: {
            "default-sort": {prop: "id", order: "descending"}, // descending, ascending
            "highlight-current-row": true,
            "size": "medium", // medium, small, mini
            // "max-height": 500,
        },
        columns: [], // 字段元数据
        pagination: { // element ui配置
            "page-size": PAGE_NUM_AREA[0],
            "page-sizes": PAGE_NUM_AREA,
            "current-page": 1,
            "layout": "total, sizes, prev, pager, next, jumper"
        }
    },
};

export default DEFAULT
