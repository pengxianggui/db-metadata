import {elementVersion} from "../../config";

export const ConfDesc = `
> v-model支持传入英文逗号分隔的字符串, 如: 1,2,3 但是需要注意能和选项值的value对应

|配置项|说明|类型|可选值|默认值|
|-----|---|----|----|-----|
|component_name|渲染组件名|string|组件列表|-|
|name|name属性|string|-|-|
|label|该实例标签名,表现为表单域的label|string|-|-|
|inline|声明为内敛元素,当存在于FormView中时有效|boolean|true/false|false|
|options|配置选项数据, 应当为一个数组, 数组内容为{key: value}对象. 注意当data_url和options同时存在时, data_url拥有更高优先级|array|-|-|
|data_url|选项列表的数据接口, 请配置url. 注意响应的数据格式应当是数组, 数组内容为{key:value}对象|string|-|/dict/yn|
|conf|ElementUI(` + elementVersion + `)中<a target="_blank" href="https://element.eleme.cn/2.12/#/zh-CN/component/checkbox#checkbox-attributes">el-checkbox</a>的原生配置项|object|-|-|
`;

export default {
    "component_name": "CheckBox",
    "name": "CheckBox",
    "label": "多选框",
    "data_url": "/dict?name=",
    "options": [],
    "conf": {},
    "explain": ""
}
