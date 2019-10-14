import DICT from './dict'
// 组件的默认UI配置
const DEFAULT = {
    TableList: { // TableList的默认ui_config
        table: {
            "default-sort": {prop: 'id', order: 'descending'}, // descending, ascending
            "size": 'medium',
            "max-height": 360
        },
        pagination: {
            "page-size": DICT.PAGE_NUM_AREA[0],
            "page-sizes": DICT.PAGE_NUM_AREA,
            "current-page": 1,
            "layout": "total, sizes, prev, pager, next, jumper"
        }
    },
    BoolBox: {
    },
    TextBox: {
        placeholder: "请输入内容..",
        clearable: true
    },
    PassBox: {
        placeholder: "请输入密码..",
        clearable: true
    },
    TextAreaBox: {
        placeholder: "请输入文本内容.."
    },
    DropDownBox: {

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

    },
    JsonBox: {
        mode: 'code',
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
        "label-width": "80px",
        // ...
    }
}

export default DEFAULT
