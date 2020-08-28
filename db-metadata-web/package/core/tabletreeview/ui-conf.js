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
    |operation-bar||object|-|{"show": true,"size": "mini","type": "primary"}|
    |column|表格列的元数据信息, 配置见原子控件配置|array|-|[]|
    |conf|ElementUI(` + elementVersion + `)中el-table的原生配置项|object|-|-|
`;

export default {
    "component_name": "TableTreeView",
    "name": "TableTreeView",
    "label": "树型表",
    "data_url": "/table/tree/{objectCode}",
    "delete_url": "/table/tree/delete/{objectCode}?ids={ids}",
    // "children_data_url": "/table/list/{objectCode}?parentPrimary={parentPrimary}",
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
    }
}
