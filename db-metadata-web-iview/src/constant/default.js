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
        name: "",
        label: "",
        component_name: 'TableList',
        data_url: "",
        methods: "GET",
        // ...
        conf: {
            "default-sort": {prop: 'username', order: 'descending'}, // descending, ascending
            "size": 'medium',
            "max-height": 500,
        },
        columns: [
            // {
            //     component_name: 'TextBox',
            //     name: 'username',
            //     label: '用户名',
            //     conf: {
            //         sortable: true,
            //         // ...
            //     }
            // }
        ],
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
        name: '',
        label: '',
        conf: {
            placeholder: "请输入内容..",
            clearable: true
        }
    },
    PassBox: {
        name: '',
        label: '',
        conf: {
            placeholder: "请输入密码..",
            clearable: true
        }
    },
    TextAreaBox: {
        name: '',
        label: '',
        conf: {
            placeholder: "请输入文本内容.."
        }
    },
    DropDownBox: {
        name: '',
        label: '',
        "conf": {
            clearable: true
        }
    },
    TimeBox: {
        name: '',
        label: '',
        conf: {
            "value-format": "HH:mm:ss"
        }
    },
    DateBox: {
        name: '',
        label: '',
        conf: {
            "value-format": "yyyy-MM-dd"
        }
    },
    DateTimeBox: {
        name: '',
        label: '',
        conf: {
            "value-format": "yyyy-MM-dd HH:mm:ss"
        }
    },
    NumBox: {
        name: '',
        label: '',
        conf: {
            placeholder: '输入数字..',
        }
    },
    RadioBox: {
        name: '',
        label: '',
        "data_url": "", // todo filling
        conf: {
            // ...
        }
    },
    CheckBox: {
        name: '',
        label: '',
        "data_url": "", // todo filling
        conf: {
            // ...
        }
    },
    JsonBox: {
        name: '',
        label: '',
        mode: 'text',
        modes: ['code', 'tree', 'text', 'view', 'form'],
        conf: {
        }
    },
    ImgBox: {
        name: '',
        label: '',
        conf: {
            action: '/upload/img',
            drag: false,
            "list-type": "picture-card",
            "auto-upload": false,
            accept: "image"
        }
    },
    FileBox: {
        name: '',
        label: '',
        conf: {
            action: "/upload/file",
            limit: 5,
            tip: "上传文件限制不超过2M",
            "auto-upload": false
        }
    },
    ZTogglePanel: {
        name: '',
        label: '',
        "default-open": false,
        olabel: "收起",
        clabel: "展开",
    },
    FormTmpl: {
        name: "formName",
        label: '',
        action: '/save', // form action (url)
        methods: 'POST',
        conf: {
            "label-width": '100px',
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
};

export default DEFAULT
