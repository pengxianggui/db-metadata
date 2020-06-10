import {elementVersion, pageNumArea} from '../../config'

export const ConfDesc = `
    |配置项|说明|类型|可选值|默认值|
    |-----|---|----|----|-----|
    |component_name|渲染组件名|string|组件列表|-|
    |name|name属性|string|-|-|
    |label|该实例标签名,表现为表单域的label|string|-|-|
    |data_url|数据接口url|string|-|/table/list/{objectCode}|
    |delete_url|删除数据接口|string|-|暂未支持|
    |multi_select|是否多选模式|boolean|true/false|false|
    |editable|是否编辑模式(暂未支持)|boolean|true/false|false|
    |column|表格列的元数据信息, 配置见原子控件配置|array|-|[]|
    |pagination|分页配置对象|object|-|{'page-size': ` + pageNumArea[0] + `, 'page-sizes': ` + pageNumArea + `, 'current-size': 1, 'layout': 'total, prev, pager, next'}|
    |conf|ElementUI(` + elementVersion + `)中el-table的原生配置项|object|-|-|
`;

export default {
    "component_name": "TableTreeList",
    "name": "TableTreeList",
    "label": "树型表",
    "data_url": "/table/list/{objectCode}",
    "delete_url": "/table/delete?objectCode={objectCode}&ids={ids}",
    "children_data_url": "/table/list/{objectCode}?parentPrimary={parentPrimary}",
    "multi_select": false,
    "editable": false,
    "conf": {
        "row-key": "id",
        "tree-props": {
            "hasChildren": "hasChildren",
            "children": "children"
        },
        "default-expand-all": false,
        "expand-row-keys": [],  // 初始展开的行, 数组中的内容为row-key指定属性对应的值
        "indent": 16, // 树节点缩进
        "lazy": false,
        "default-sort": {"prop": "id", "order": "descending"}, // descending, ascending
        "highlight-current-row": true,
        "size": "medium", // medium, small, mini
    },
    "columns": [],
    "pagination": { // element ui配置
        "page-size": pageNumArea[0],
        "page-sizes": pageNumArea,
        "current-page": 1,
        "layout": "total, sizes, prev, pager, next, jumper"
    },
    "operation-bar": { // 针对操作栏中所有按钮的默认设置
        "size": "mini",
        "type": "primary"
    },
    "buttons": {
        "edit": {
            "label": "编辑",
            "conf": {
                "icon": "el-icon-edit",
                "size": "mini",
                "type": "primary"
            }
        },
        "delete": {
            "label": "删除",
            "conf": {
                "size": "mini",
                "icon": "el-icon-delete",
                "type": "danger"
            }
        }
    }
}
