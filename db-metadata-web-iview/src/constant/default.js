import DICT from './dict'
// 最小业务组件的默认UI配置
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
    ImgBox: {
        action: '/upload',
        drag: false,
        "list-type": "picture-card",
        "auto-upload": false,
        accept: "image"
    }
}

export default DEFAULT
