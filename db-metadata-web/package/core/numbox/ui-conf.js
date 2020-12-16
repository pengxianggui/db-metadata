import {elementVersion} from "../../config";

export const ConfDesc = `
|配置项|说明|类型|可选值|默认值|
|-----|---|----|----|-----|
|component_name|渲染组件名|string|组件列表|-|
|name|name属性|string|-|-|
|label|该实例标签名,表现为表单域的label|string|-|-|
|inline|声明为内敛元素,当存在于FormView中时有效|boolean|true/false|false|
|default_value|默认值|number|-|0|
|conf|ElementUI(` + elementVersion + `)中<a target="_blank" href="https://element.eleme.cn/2.12/#/zh-CN/component/input-number#attributes">el-input-number</a>的原生配置项|object|-|-|
`;

export default {
    "component_name": "NumBox",
    "name": "NumBox",
    "label": "数字框",
    "inline": true,
    "default_value": 0,
    "conf": {
        "controls": true,
        "placeholder": "请输入数值.."
    }
}
