import {elementVersion, pageNumArea} from '../../config'

export const ConfDesc = `
|配置项|说明|类型|可选值|默认值|
|-----|---|----|----|-----|
|component_name|渲染组件名|string|组件列表|-|
|name|name属性|string|-|-|
|label|该实例标签名,表现为表单域的label|string|-|-|
|data_url|数据接口url|string|-|/table/list/{objectCode}|
|delete_url|删除数据接口|string|-|/table/delete/{objectCode}?{primaryKvExp}|
|multi_select|是否多选模式|boolean|true/false|true|
|editable|是否编辑模式(暂未支持)|boolean|true/false|false|
|operation-bar|操作条的配置项|object|-|{"show": true,"size": "mini","type": "primary"}|
|operation-column|操作列的配置项|object|-|{"show": true,"fixed": true,"width": "180"}|
|oper_logic|一些操作逻辑配置|object|chose_type可选值: default/toggle|{"chose_type": true}|
||chose-type|节点点选模式: 再次点选已选中节点时的行为。default表示无行为, toggle会取消已选节点|string|default/toggle|default|
|columns|表格列的元数据信息, 配置见原子控件配置。当表可编辑时会用到其中的控件配置。 **列元数据支持render配置选项,值为函数字符串, 参数为(h, value, row)**|array|-|[]|
|pagination|分页配置对象|object|-|{'page-size': ` + pageNumArea[0] + `, 'page-sizes': ` + pageNumArea + `, 'current-size': 1, 'layout': 'total, prev, pager, next'}|
|conf|ElementUI(` + elementVersion + `)中el-table的原生配置项|object|-|-|
`;


export const CHOSE_TYPE = {
    default: 'default',
    toggle: 'toggle'
}

export default {
    "component_name": "TableView",
    "name": "TableView",
    "label": "表格模板",
    "data_url": "/table/list/{objectCode}", // required
    "delete_url": '/table/delete/{objectCode}?{primaryKvExp}',
    "multi_select": true, //多选
    "editable": false,
    "conf": {
        "default-sort": {"prop": "id", "order": "descending"}, // descending, ascending
        "highlight-current-row": true,
        "size": "medium", // medium, small, mini
        // "max-height": 500,
    },
    "columns": [], // 字段元数据
    "pagination": { // element ui配置
        "show": true,
        "page-size": pageNumArea[0],
        "page-sizes": pageNumArea,
        "current-page": 1,
        "layout": "total, sizes, prev, pager, next, jumper"
    },
    "operation-bar": { // 针对操作栏中所有按钮的默认设置
        "show": true,
        "size": "mini",
        "type": "primary"
    },
    "operation-column": { // 针对操作列
        "show": true,
        "fixed": "right",
        "width": "180"
    },
    "buttons": {
        "show": true,
        "view": {
            "show": true,
            "label": "详情",
            "conf": {
                "icon": "el-icon-view",
                "size": "mini",
                "type": "success"
            }
        },
        "edit": {
            "show": true,
            "label": "编辑",
            "conf": {
                "icon": "el-icon-edit",
                "size": "mini",
                "type": "primary"
            }
        },
        "delete": {
            "show": true,
            "label": "删除",
            "conf": {
                "size": "mini",
                "icon": "el-icon-delete",
                "type": "danger"
            }
        }
    },
    "oper_logic": {
        "chose_type": CHOSE_TYPE.default, // |chose-type|节点点选模式: 再次点选已选中节点时的行为。default表示无行为, toggle会取消已选节点|string|default/toggle|default|
    },
}
