import {elementVersion} from '../../config'

export const ConfDesc = `
|配置项|说明|类型|可选值|默认值|
|-----|---|----|----|-----|
|component_name|渲染组件名|string|组件列表|-|
|name|name属性|string|-|-|
|label|该实例标签名,表现为表单域的label|string|-|-|
|data_url|数据接口url|string|-|/table/list?objectCode={objectCode}|
|delete_url|删除数据接口|string|-|暂未支持|
|multi_select|是否多选模式|boolean|true/false|false|
|conf|ElementUI(` + elementVersion + `)中el-table的原生配置项|object|-|-|
|operation-bar||object|-||
|column|表格列的元数据信息, 配置见原子控件配置|array|-|[]|
|operation-column|操作列的配置项||
`;

export default {
    "component_name": "TableTreeView",
    "name": "TableTreeView",
    "label": "树型表",
    "data_url": "/table/tree?objectCode={objectCode}",
    "delete_url": "/table/tree/delete?objectCode={objectCode}&ids={ids}",
    // "children_data_url": "/table/list/{objectCode}?parentPrimary={parentPrimary}",
    "multi_select": false,
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
    "operation-bar": { // 针对操作栏中所有按钮的默认设置
        "show": true,
        "group": true,
        "style": {},
        "conf": {
            "size": "mini"
        },
        "add": {
            "show": true,
            "text": "新增",
            "conf": {
                "size": "mini",
                "type": "primary",
                "icon": "el-icon-document-add",
            }
        },
        "delete": {
            "show": true,
            "text": "删除",
            "conf": {
                "size": "mini",
                "type": "danger",
                "icon": "el-icon-delete-solid",
            }
        },
        "expand": {
            "show": true,
            "text": "展开",
            "conf": {
                "size": "mini",
                "type": "info",
                "icon": "el-icon-caret-bottom",
            }
        },
        "shrink": {
            "show": true,
            "text": "收缩",
            "conf": {
                "size": "mini",
                "type": "info",
                "icon": "el-icon-caret-right",
            }
        }
    },
    "columns": [],
    "operation-column": { // 针对操作列
        "show": true,
        "conf": {
            "fixed": "right",
            "width": "180"
        },
        "style": {},
        "buttons": {
            "show": true,
            "group": true,
            "style": {},
            "view": {
                "show": true, // Boolean/Function
                "text": "",
                "conf": {
                    "size": "mini",
                    "type": "success",
                    "icon": "el-icon-view"
                }
            },
            "edit": {
                "show": true, // Boolean/Function
                "text": "",
                "conf": {
                    "size": "mini",
                    "type": "primary",
                    "icon": "el-icon-edit"
                }
            },
            "delete": {
                "show": true, // Boolean/Function
                "text": "",
                "conf": {
                    "size": "mini",
                    "type": "danger",
                    "icon": "el-icon-delete"
                }
            }
        }
    }
}
