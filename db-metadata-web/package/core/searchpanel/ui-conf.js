import {elementVersion} from "../../config";

export const ConfDesc = `
    |配置项|说明|类型|可选值|默认值|
    |-----|---|----|----|-----|
    |component_name|渲染组件名|string|组件列表|-|
    |name|name属性|string|-|-|
    |label|该实例标签名,表现为表单域的label|string|-|-|
    |expand|是否默认展开搜索面板|boolean|true/false|false|
    |label-position|收缩开展开关按钮位置|string|top-left/top-center/top-right/bottom-left/bottom-center/bottom-right|top-center|
    |conf|ElementUI(` + elementVersion + `)中el-form的原生配置项|object|-|-|
`;

export default {
    "component_name": "SearchPanel",
    "name": "SearchPanel",
    "label": "搜索面板",
    "expand": false,
    "label-position": "top-center",
    "conf": {
        "label-width": '80px',
        "size": 'mini', // medium|small|mini
    },
    "columns": []
}
