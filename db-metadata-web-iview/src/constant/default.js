import DICT from './dict'
// 最小业务组件的默认UI配置
const DEFAULT = {
    tableList: { // TableList的默认ui_config
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
    pagination: {
        pageNum: DICT.PAGE_NUM_AREA[0],
        totalNum: 1, // 总数, 被实际页数覆盖
        currentPage: 1 // 默认显示第一页
    },
    upload: {
        url: '/upload',
        drag: true,
    }
}

export default DEFAULT
