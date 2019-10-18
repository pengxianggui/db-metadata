import DICT from './dict'
// 组件的默认UI配置
const DEFAULT = {
    // TableList: { // TableList的默认ui_config
        // table: {
        //     "default-sort": {prop: 'id', order: 'descending'}, // descending, ascending
        //     "size": 'medium',
        //     "max-height": 360
        // },
        // pagination: {
        //     "page-size": DICT.PAGE_NUM_AREA[0],
        //     "page-sizes": DICT.PAGE_NUM_AREA,
        //     "current-page": 1,
        //     "layout": "total, sizes, prev, pager, next, jumper"
        // }
    // },
    TableList: {
        table_name: "",
        table_cn_name: "",
        component_name: 'TableList',
        data_url: "",
        methods: "GET",
        // ...
        conf: {
            "default-sort": {prop: 'username', order: 'descending'}, // descending, ascending
            "size": 'medium',
            "max-height": 360,
        },
        columns: [{
            component_name: 'TextBox',
            name: 'username',
            label: '用户名',
            conf: {
                sortable: true,
                // ...
            }
        }],
        pagination: {
            "page-size": DICT.PAGE_NUM_AREA[0],
            "page-sizes": DICT.PAGE_NUM_AREA,
            "current-page": 1,
            "layout": "total, sizes, prev, pager, next, jumper"
        }
    },

    BoolBox: {},
    TextBox: {
        // custom ..
        conf: {
            placeholder: "请输入内容..",
            clearable: true
        }
    },
    PassBox: {
        placeholder: "请输入密码..",
        clearable: true
    },
    TextAreaBox: {
        placeholder: "请输入文本内容.."
    },
    DropDownBox: {
        "data-url": "/api/options", // todo reset
        "conf": {
            clearable: true
        }
    },
    TimeBox: {
        "value-format": "HH:mm:ss"
    },
    DateBox: {
        "value-format": "yyyy-MM-dd"
    },
    DateTimeBox: {
        "value-format": "yyyy-MM-dd HH:mm:ss"
    },
    NumBox: {
        placeholder: '输入数字..',
    },
    RadioBox: {
        "data-url": "", // todo filling
    },
    CheckBox: {
        "data-url": "" // todo filling
    },
    JsonBox: {
        mode: 'text',
        modes: ['code', 'tree', 'text', 'view', 'form']
    },
    ImgBox: {
        action: '/upload/img',
        drag: false,
        "list-type": "picture-card",
        "auto-upload": false,
        accept: "image"
    },
    FileBox: {
        action: "/upload/file",
        limit: 5,
        tip: "上传文件限制不超过2M",
        "auto-upload": false
    },
    ZTogglePanel: {
        "default-open": false,
        olabel: "收起",
        clabel: "展开"
    },
    FormTmpl: {
        form_name: "formName",
        action: '/save', // form action (url)
        methods: 'POST',
        conf: {
            "label-width": '80px',
            size: 'medium', // medium|small|mini
            model: {
                username: '',
                // ...
            },
            rules: {
                username: [{required: true, message: '用户名必填', trigger: 'blur'}],
                // ...
            },
            // ...
        },
        columns: [
            // {
            //     component_name: 'TextBox',
            //     name: 'username',
            //     label: '用户名',
            //     conf: {
            //         clearable: true,
            //         placeholder: "请输入姓名..",
            //         // ...
            //     }
            // }
        ],
        btns: {
            submit: {
                label: '提交',
                conf: {
                    // ... support conf of el-button
                    type: "primary"
                }
            },
            cancel: {
                label: '取消',
                conf: {
                    // ... support conf of el-button
                }
            }
        }

    }
}

export default DEFAULT
