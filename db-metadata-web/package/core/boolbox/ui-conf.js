import {elementVersion} from "../../config";

export const ConfDesc = `
> [false, true, "false", "true", 0, 1, "0", "1", "f", "t", null, true, undefined, true] 出于扩展, 当v-model值为数组中任意奇数下标时, 都视为false, 反之为true

|配置项|说明|类型|可选值|默认值|
|-----|---|----|----|-----|
|component_name|渲染组件名|string|组件列表|-|
|name|name属性|string|-|-|
|label|该实例标签名,表现为表单域的label|string|-|-|
|inline|声明为内敛元素,当存在于FormView中时有效|boolean|true/false|false|
|conf|ElementUI(` + elementVersion + `)中<a target="_blank" href="https://element.eleme.cn/2.12/#/zh-CN/component/checkbox#checkbox-attributes">el-checkbox</a>的原生配置项|object|-|-|
`;

export default {
    "component_name": "BoolBox",
    "name": "BoolBox",
    "label": "布尔框",
    "default_value": false,
    "conf": {},
    "explain": "" // 字段解释
}
