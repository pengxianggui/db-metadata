import {elementVersion} from "../../config";

export const ConfDesc = `
|配置项|说明|类型|可选值|默认值|
|---|---|---|---|---|
|component_name|渲染组件名|string|可选的组件列表|-|
|name|name属性|string|-|-|
|label|该实例标签名,表现为表单域的label|string|-|-|
|*inline*|声明为内敛元素,当存在于FormView中时有效|boolean|true/false|false|
|conf|ElementUI(` + elementVersion + `)中<a target="_blank" href="https://element.eleme.cn/2.12/#/zh-CN/component/input#input-attributes">el-input</a>的原生配置项|object|-|-|
`;

export default {
    "component_name": "TextBox",
    "name": "TextBox",
    "label": "文本框",
    "inline": false,
    "conf": {
        "placeholder": "请输入内容..",
        "clearable": true
    }
}
