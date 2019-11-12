import {PAGE_NUM_AREA} from './constant'
// 组件的默认UI配置
const DEFAULT = {
    "BoolBox": {
        "component_name": "BoolBox",
        "name": "BoolBox",
        "label": "布尔框",
        "default_value": false,
        "inline": true,
        "conf": {}
    },
    "TextBox": {
        "component_name": "TextBox",
        "name": "TextBox",
        "label": "文本框",
        "inline": false,
        "conf": {
            "placeholder": "请输入内容..",
            "clearable": true
        }
    },
    "PassBox": {
        "component_name": "PassBox",
        "name": "PassBox",
        "label": "密码框",
        "inline": false,
        "conf": {
            "placeholder": "请输入密码..",
            "clearable": true
        }
    },
    "TextAreaBox": {
        "component_name": "TextAreaBox",
        "name": "TextAreaBox",
        "label": "文本域",
        "inline": false,
        "conf": {
            "placeholder": "请输入文本内容.."
        }
    },
    "DropDownBox": {
        "component_name": "DropDownBox",
        "name": "DropDownBox",
        "label": "下拉框",
        "inline": true,
        "group": false,
        "conf": {
            "clearable": true
        }
    },
    "TimeBox": {
        "component_name": "TimeBox",
        "name": "TimeBox",
        "label": "时间框",
        "inline": true,
        "conf": {
            "value-format": "HH:mm:ss",
            "clearable": true
        }
    },
    "DateBox": {
        "component_name": "DateBox",
        "name": "DateBox",
        "label": "日期框",
        "inline": true,
        "conf": {
            "value-format": "yyyy-MM-dd",
            "clearable": true
        }
    },
    "DateTimeBox": {
        "component_name": "DateTimeBox",
        "name": "DateTimeBox",
        "label": "日期时间框",
        "inline": true,
        "conf": {
            "value-format": "yyyy-MM-dd HH:mm:ss",
            "clearable": true
        }
    },
    "NumBox": {
        "component_name": "NumBox",
        "name": "NumBox",
        "label": "数字框",
        "inline": true,
        "default_value": 0,
        "conf": {
            "controls": false,
            "placeholder": "请输入数值.."
        }
    },
    "RadioBox": {
        "component_name": "RadioBox",
        "name": "RadioBox",
        "label": "单选框",
        "inline": true,
        "data_url": "/dict/yn",
        "conf": {}
    },
    "CheckBox": {
        "component_name": "CheckBox",
        "name": "CheckBox",
        "label": "多选框",
        "inline": true,
        "data_url": "/dict/yn",
        "conf": {}
    },
    "JsonBox": {
        "component_name": "JsonBox",
        "name": "JsonBox",
        "label": "Json框",
        "inline": false,
        "default_value": {},
        "conf": {
            "mode": "code",
            "modes": [
                "code",
                "tree",
                "text",
                "view",
                "form"
            ]
        }
    },
    "ImgBox": {
        "component_name": "ImgBox",
        "name": "ImgBox",
        "label": "图片上传框",
        "inline": false,
        "conf": {
            "action": "/upload/img",
            "drag": false,
            "list-type": "picture-card",
            "auto-upload": false,
            "accept": "image"
        }
    },
    "FileBox": {
        "component_name": "FileBox",
        "name": "FileBox",
        "label": "文件上传框",
        "inline": false,
        "conf": {
            "action": "/upload/file",
            "limit": 5,
            "tip": "上传文件限制不超过2M",
            "auto-upload": false
        }
    },
    "ZTogglePanel": {
        "component_name": "ZTogglePanel",
        "name": "ZTogglePanel",
        "label": "收缩面板",
        "default_open": false,
        "olabel": "收起",
        "clabel": "展开"
    },
    "DialogBox": {
        "component_name": "DialogBox",
        "name": "DialogBox",
        "label": "弹出框",
        "conf": {
            "title": "标题",
            "fullscreen": false,
            "width": "50%",
            "modal": true,
            "lock-scroll": true,
            "show-close": true,
            "center": false,
            "destroy-on-close": true
        }
    },
    "FormTmpl": {
        "component_name": "FormTmpl",
        "name": "FormTmpl",
        "label": "表单模板",
        "action": "/form/doAdd/{objectCode}", // form action (url)
        "conf": {
            "label-width": '100px',
            "size": 'medium', // medium|small|mini
            "disabled": false,
            "inline": false,
            "rules": {
                // eg:
                // "id": [{required: true, message: "必填字段", trigger: "blur"}],
                // ...
            },
            // ...
        },
        "columns": [],
        // eg:
        // {
        //     name: 'id',
        //     label: 'ID',
        //     component_name: 'TextBox',
        //
        //     inline: false,
        //     showable: true,  // 表单中是否隐藏此域
        //     index: 1,        // 表单中字段先后顺序
        //     default_value: 1,// 默认值
        //
        //     conf: {
        //         clearable: true,
        //         placeholder: "请输入..",
        //         readonly: true,  // 只读
        //         disabled: false, // 禁用
        //         required: false, // 是否必填
        //         // ...
        //     }
        // }
        "btns": {
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
    "TableList": {
        "component_name": "TableList",
        "name": "TableList",
        "label": "表格模板",
        "data_url": "/table/list/{objectCode}", // required
        "delete_url": '/table/delete?objectCode={objectCode}&ids={ids}',
        "multi_select": true, //多选
        "editable": false,
        "conf": {
            "default-sort": {"prop": "id", "order": "descending"}, // descending, ascending
            "highlight-current-row": true,
            "size": "medium", // medium, small, mini
            // "max-height": 500,
        },
        "columns": [
            // {
            //     name: 'name',
            //     label: 'label',
            //     component_name: '',
            //     editable: false,     // 表格内可编辑
            //     searchable: true,    // 搜索面板支持该字段搜索
            //     index: 1,            // 排序号, 决定表格中列的先后顺序
            //     conf: {
            //         width: '',
            //         sortable: true,  // 表格中可否根据此列进行排序
            //         //...
            //     }
            // }
        ], // 字段元数据
        "pagination": { // element ui配置
            "page-size": PAGE_NUM_AREA[0],
            "page-sizes": PAGE_NUM_AREA,
            "current-page": 1,
            "layout": "total, sizes, prev, pager, next, jumper"
        }
    },
};

export default DEFAULT
