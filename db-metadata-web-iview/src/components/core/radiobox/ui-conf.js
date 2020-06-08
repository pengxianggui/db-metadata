import {elementVersion} from "@/config";

export const ConfDesc = `
    |配置项|说明|类型|可选值|默认值|
    |-----|---|----|----|-----|
    |component_name|渲染组件名|string|组件列表|-|
    |name|name属性|string|-|-|
    |label|该实例标签名,表现为表单域的label|string|-|-|
    |inline|声明为内敛元素,当存在于FormView中时有效|boolean|true/false|false|
    |data_url|选项列表的数据接口, 请配置url. 注意响应的数据格式应当是数组, 数组内容为{key:value}对象|string|-|/dict/yn|
    |options|配置选项数据, 应当为一个数组, 数组内容为{key: value}对象. 注意当data_url和options同时存在时, data_url拥有更高优先级|array|-|-|
    |conf|ElementUI(` + elementVersion + `)中el-radio的原生配置项|object|-|-|
`;

export default {
    "component_name": "RadioBox",
    "name": "RadioBox",
    "label": "单选框",
    "inline": true,
    "data_url": "/dict/yn",
    "options": [
        //     {"key":"value"}
    ],
    "conf": {}
}
