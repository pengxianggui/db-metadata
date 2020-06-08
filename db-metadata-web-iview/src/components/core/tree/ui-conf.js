import {elementVersion} from "@/config";

export const ConfDesc = `
    |配置项|说明|类型|可选值|默认值|
    |-----|---|----|----|-----|
    |component_name|渲染组件名|string|组件列表|-|
    |name|name属性|string|-|-|
    |label|该实例标签名,表现为表单域的label|string|-|-|
    |conf|ElementUI(` + elementVersion + `)中el-tree的原生配置项|object|-|-|
    |behavior|待完善|object|-|-|
`;

export default {
    "component_name": "Tree",
    "name": "Tree",
    "label": "树型列表",
    "data_url": "/tree/list/{objectCode}",
    "conf": {
        "node-key": "id",
        "highlight-current": true,
        "default-expand-all": false,
        "check-on-click-node": false,
        "expand-on-click-node": false,
        "show-checkbox": true,
        "default-expanded-keys": [],
        "default-checked-keys": [],
        "accordion": false,
        "indent": 16,
        "icon-class": "el-icon-caret-right",
        "draggable": false,
        "props": {
            "label": "label",
            "children": "children"
        }
    },
    "operation-bar": {
        "size": "mini",
        "type": "primary"
    },
    "behavior": {
        "allowDrag": function (node) {
            return true
        },
        "allowDrop": function (draddingNode, dropNode, type) {
            return true
        },
        "filterNodeMethod": function (value, data, node) {
            if (!value) return true;
            return data && data.label.indexOf(value) !== -1;
        },
        // "renderContent": function (h, {node, data, store}) {
        // }
    }
}
