export const ConfDesc = `
|配置项|说明|类型|可选值|默认值|
|-----|---|----|----|-----|
|component_name|渲染组件名|string|组件列表|-|
|name|name属性|string|-|-|
|label|该实例标签名,表现为表单域的label|string|-|-|
|data_url|数据接口url|string|-|/table/list?objectCode={objectCode}|
|delete_url|删除数据接口|string|-|/table/delete?{primaryKvExp}&objectCode={objectCode}|
|multi_select|是否多选模式|boolean|true/false|true|
|conf|ElementUI(` + elementVersion + `)中el-table的原生配置项|object|-|-|
|operation-bar|操作条的配置项|object|-||
|columns|表格列的元数据信息, 配置见原子控件配置。当表可编辑时会用到其中的控件配置。 **列元数据支持render配置选项,值为函数字符串, 参数为(h, value, row)**|array|-|[]|
|operation-column|操作列的配置项|object|-||
|pagination|分页配置对象|object|-|{'page-size': ` + pageNumArea[0] + `, 'page-sizes': ` + pageNumArea + `, 'current-size': 1, 'layout': 'total, prev, pager, next'}|
`;

import {elementVersion, pageNumArea} from '../../config'

export default {
    "component_name": "TableView",
    "name": "TableView",
    "label": "表格模板",
    "data_url": "/table/list?objectCode={objectCode}", // required
    "delete_url": '/table/delete?objectCode={objectCode}&{primaryKvExp}',
    "multi_select": true, //多选
    "show_index": false, // 在首列显示序号
    "conf": { // el-table原生配置
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
            "authorize": {
                "need_permit": false,
                "permit_by": "auth",
                "auths": [],
                "auth_match_mode": "any",
                "roles": [],
                "role_match_mode": "any"
            },
            "conf": {
                "size": "mini",
                "type": "primary",
                "icon": "el-icon-document-add",
            }
        },
        "delete": {
            "show": true,
            "text": "删除",
            "authorize": {
                "need_permit": false,
                "permit_by": "auth",
                "auths": [],
                "auth_match_mode": "any",
                "roles": [],
                "role_match_mode": "any"
            },
            "conf": {
                "size": "mini",
                "type": "danger",
                "icon": "el-icon-delete-solid",
            }
        }
    },
    "columns": [], // 字段元数据
    "operation-column": { // 针对操作列
        "show": true,
        "conf": {
            "fixed": "right",
            "width": "180",
            "header-align": "center"
        },
        "style": {},
        "buttons": {
            "show": true,
            "group": true,
            "style": {},
            "conf": {
                "size": "mini"
            },
            "view": {
                "show": true, // Boolean/Function
                "text": "",
                "authorize": {
                    "need_permit": false,
                    "permit_by": "auth",
                    "auths": [],
                    "auth_match_mode": "any",
                    "roles": [],
                    "role_match_mode": "any"
                },
                "conf": {
                    "size": "mini",
                    "type": "success",
                    "icon": "el-icon-view"
                }
            },
            "edit": {
                "show": true, // Boolean/Function
                "text": "",
                "authorize": {
                    "need_permit": false,
                    "permit_by": "auth",
                    "auths": [],
                    "auth_match_mode": "any",
                    "roles": [],
                    "role_match_mode": "any"
                },
                "conf": {
                    "size": "mini",
                    "type": "primary",
                    "icon": "el-icon-edit"
                }
            },
            "delete": {
                "show": true, // Boolean/Function
                "text": "",
                "authorize": {
                    "need_permit": false,
                    "permit_by": "auth",
                    "auths": [],
                    "auth_match_mode": "any",
                    "roles": [],
                    "role_match_mode": "any"
                },
                "conf": {
                    "size": "mini",
                    "type": "danger",
                    "icon": "el-icon-delete"
                }
            }
        },
    },
    "pagination": { // element分页组件  ui配置
        "show": true,
        "style": {

        },
        "conf": {
            "background": true,
            "page-size": pageNumArea[0],
            "page-sizes": pageNumArea,
            "current-page": 1,
            "layout": "total, sizes, prev, pager, next, jumper"
        }
    },
}
