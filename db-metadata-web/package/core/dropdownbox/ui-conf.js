import {elementVersion} from "../../config";

export const ConfDesc = `
|配置项|说明|类型|可选值|默认值|
|-----|---|----|----|-----|
|component_name|渲染组件名|string|组件列表|-|
|name|name属性|string|-|-|
|label|该实例标签名,表现为表单域的label|string|-|-|
|*inline*|声明为内敛元素,当存在于FormView中时有效|boolean|true/false|false|
|group|是否为分组模式, 注意若为分组模式, 则options选项数据必须为element支持的分组结构|boolean|true/false|false|
|data_url|选项列表的数据接口, 请配置url. 注意响应的数据格式应当是数组, 数组内容为{key:value}对象, 当group为true时, 数据结构是不一样的|string|-|-|
|options|配置选项数据, 应当为一个数组, 数组内容为{key: value}对象(当group为true时,结构有所不同). 注意当data_url和options同时存在时, data_url拥有更高优先级|array|-|-|
|conf|ElementUI(` + elementVersion + `)中<a target="_blank" href="https://element.eleme.cn/2.12/#/zh-CN/component/select#select-attributes">el-select</a>的原生配置项|object|-|-|
`;

export default {
    "component_name": "DropDownBox",
    "name": "DropDownBox",
    "label": "下拉框",
    "inline": true,
    "group": false,
    "data_url": "/dict/yn",
    "options": [
        //     {"key":"value"}
    ],
    "conf": {
        "clearable": true,
        "multiple": false
    }
}
